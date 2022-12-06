package june.songmelier.controller;

import june.songmelier.dto.MemberDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 프로필 조회
     */
    @GetMapping("/api/member")
    public MemberDto.profileRes getMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return memberService.getMyProfile(principalDetails.getMemberId());
    }

    /**
     * 프로필 수정
     * image 파일이 null이 들어올 수 있음 - 이럴땐 수정 X
     */
    @PostMapping("/api/member/edit")
    public void memberEdit(@NotBlank @RequestPart("username") String username,
                           @RequestPart("introduce") String introduce,
                           @RequestPart(value = "image", required = false) MultipartFile file,
                           @AuthenticationPrincipal PrincipalDetails principalDetails) {

        memberService.editMyProfile(username, introduce, file, principalDetails.getMemberId());
    }

    @PostMapping("/api/member/secession")
    public void memberSecession(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        memberService.secession(principalDetails.getMemberId());
    }
}
