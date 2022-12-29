package june.songmelier.service;

import june.songmelier.dto.CommentDto;
import june.songmelier.dto.EvaluationDto;
import june.songmelier.dto.MemberDto;
import june.songmelier.dto.SongDto;
import june.songmelier.entity.*;
import june.songmelier.repository.CommentRepository;
import june.songmelier.repository.CommentStatusRepository;
import june.songmelier.repository.FavorRepository;
import june.songmelier.repository.SongRepository;
import june.songmelier.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final SongRepository songRepository;
    private final CommentRepository commentRepository;
    private final CommentStatusRepository commentStatusRepository;


    @Transactional
    public void createComment(Long songId, Long memberId, String text) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 노래가 존재하지 않습니다."));

        Comment comment = Comment.createComment(text, Member.createIdMember(memberId), song);

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long songId, Long memberId) {
        commentRepository.findById(commentId)
                .filter((co) -> co.getMember().getId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코멘트 입니다."));

        commentRepository.deleteById(commentId);

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 노래가 존재하지 않습니다."));
        song.commentCountDown();

    }

    @Transactional
    public void createCommentFavor(Long commentId, Long memberId, boolean isLike) {
        //comment favor 눌렀는지 혹은 다른것을 눌렀는지 확인
        Optional<CommentStatus> commentStatus = commentStatusRepository.findByCommentId(commentId)
                .filter((status) -> status.getMember().getId().equals(memberId));

        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코멘트입니다.."));

        commentStatus.filter(status -> status.isLike() == isLike).ifPresent(
                (x) -> {throw new IllegalArgumentException("이미 클릭한 status");}
        );

        commentStatus.ifPresent(
                (status) -> {
                    if (status.isLike()) comment.likeCountDown();
                    else comment.hateCountDown();

                    commentStatusRepository.delete(status);
                }
        );

        CommentStatus newCommentStatus =
                CommentStatus.createCommentStatus(isLike, Member.createIdMember(memberId), comment);
        commentStatusRepository.save(newCommentStatus);

    }

    @Transactional
    public void deleteCommentFavor(Long commentId, Long memberId) {
        CommentStatus commentStatus = commentStatusRepository.findByCommentId(commentId)
                .filter((status) -> status.getMember().getId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("없거나 자신의 것이 아닙니다."));

        Comment comment = commentStatus.getComment();
        if (commentStatus.isLike()) comment.likeCountDown();
        else comment.hateCountDown();

        commentStatusRepository.delete(commentStatus);
    }


    //모든 코맨트 가져오기 ,page전
//    @Transactional
//    public List<CommentDto.CommentRes> getSongComments(Long songId, Long memberId) {
//
//        List<Comment> comments = commentRepository.findBySongIdAndMember(songId);
//        List<CommentDto.CommentRes> result = new ArrayList<CommentDto.CommentRes>();
//
//        for(Comment comment : comments){
//            Optional<CommentStatus> commentStatus = commentStatusRepository.findByCommentId(comment.getId());
//            MemberDto.MemberRes member = new MemberDto.MemberRes(comment.getMember().getId(),comment.getMember().getUsername(),comment.getMember().getImageUrl());
//            result.add(new CommentDto.CommentRes(comment.getId(), comment.getText(),comment.getLikeCount(),comment.getCreatedAt(),commentStatus.isPresent(),member));
//        }
//        return result;
//    }

    @Transactional
    public Slice<CommentDto.CommentRes> getSongComments(Long songId, Long memberId,Pageable pageable) {

        Slice<Comment> comments = commentRepository.findBySongIdAndMember(songId,pageable);
        Slice<CommentDto.CommentRes> result = comments.map(c -> new CommentDto.CommentRes(c.getId(),c.getText(),c.getLikeCount(),c.getCreatedAt(),
                commentStatusRepository.findByCommentId(c.getId()).isPresent(),
                new MemberDto.MemberRes(c.getMember().getId(),c.getMember().getUsername(),c.getMember().getImageUrl())));

        return result;
    }

    @Transactional
    public Slice<CommentDto.MyCommentRes> getMyComment(Long memberId,Pageable pageable) {

        Slice<Comment> comments = commentRepository.findBySongIdAndMember(memberId,pageable);

        Slice<CommentDto.MyCommentRes> result = comments.map( c -> new CommentDto.MyCommentRes(
                new SongDto.MyCommentSongRes(c.getSong().getId(), c.getSong().getTitle(), c.getSong().getSinger(), c.getSong().getImageUrl(), c.getSong().getRapDifficult(),c.getSong().getHighDifficult(),c.getSong().getLowDifficult(),c.getSong().getMood()),
                new CommentDto.MyCommentDto(c.getId(),c.getText())
        ));
        return result;
    }




}
