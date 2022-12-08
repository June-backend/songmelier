package june.songmelier.service;

import june.songmelier.dto.MemberDto;
import june.songmelier.entity.Member;
import june.songmelier.repository.MemberRepository;
import june.songmelier.utils.S3UploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadUtils s3UploadUtils;


    public MemberDto.profileRes getMyProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버 존재 X"));

        return new MemberDto.profileRes(member);
    }


    @Transactional
    public void editMyProfile(String username, String introduce, MultipartFile file, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버 존재 X"));

        String imageUrl = Optional.ofNullable(file).isEmpty() ?
                member.getImageUrl() : s3UploadUtils.upload(file, "profile");
        member.putMember(username, imageUrl, introduce);
        return;
    }

    @Transactional
    public void secession(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 멤버 존재 X"));

        //해당 ID에 해당하는 멤버가 없다면 EmptyResultDataAccessException 이 일어난다.
        memberRepository.deleteById(memberId);
    }
}
