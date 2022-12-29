package june.songmelier.controller;


import june.songmelier.dto.CommentDto;
import june.songmelier.dto.EvaluationDto;
import june.songmelier.dto.MemberDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 코멘트 추가
     */
    @PostMapping("/api/song/{songId}/comment")
    public void createComment(@PathVariable("songId") Long songId,
                              @AuthenticationPrincipal PrincipalDetails principal,
                              @RequestBody CommentDto.CreateReq commentReq) {
        commentService.createComment(songId, principal.getMemberId(), commentReq.getText());

    }

    /**
     * 코멘트 제거
     */
    @PostMapping("/api/song/{songId}/comment/{commentId}/delete")
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              @PathVariable("songId") Long songId,
                              @AuthenticationPrincipal PrincipalDetails principal) {

        commentService.deleteComment(commentId, songId, principal.getMemberId());
    }


    /**
     * 코멘트 좋아요 추가
     */
    @PostMapping("/api/comment/{commentId}/favor")
    public void createCommentFavor(@PathVariable("commentId") Long commentId,
                                   @AuthenticationPrincipal PrincipalDetails principal,
                                   @RequestBody Map<String, Integer> isLike) {

        boolean like = isLike.get("isLike").equals(1) ? true : false;
        commentService.createCommentFavor(commentId, principal.getMemberId(), like);
    }


    /**
     * 코멘트 좋아요 삭제
     */
    @PostMapping("/api/comment/{commentId}/favor/delete")
    public void deleteCommentFavor(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal PrincipalDetails principal) {
        commentService.deleteCommentFavor(commentId, principal.getMemberId());
    }

    /**
     * 곡의 코멘트 보기
     */
    //page 전
//    @GetMapping("/api/song/{songId}/comment")
//    public List<CommentDto.CommentRes> getSongComments(@PathVariable("songId") Long songId, @AuthenticationPrincipal PrincipalDetails principal){
//        return commentService.getSongComments(songId,principal.getMemberId());
//    }


    @GetMapping("/api/song/{songId}/comment")
    public Slice<CommentDto.CommentRes> getSongComments(@PathVariable("songId") Long songId,
                                                        @AuthenticationPrincipal PrincipalDetails principal,
                                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable ){
        return commentService.getSongComments(songId,principal.getMemberId(),pageable);
    }

    @GetMapping("/api/song/member/comment")
    public Slice<CommentDto.MyCommentRes> getSongComments(@AuthenticationPrincipal PrincipalDetails principal,
                                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable ){
        return commentService.getMyComment(principal.getMemberId(),pageable);
    }

}
