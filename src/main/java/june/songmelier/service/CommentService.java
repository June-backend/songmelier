package june.songmelier.service;

import june.songmelier.entity.Comment;
import june.songmelier.entity.CommentStatus;
import june.songmelier.entity.Member;
import june.songmelier.entity.Song;
import june.songmelier.repository.CommentRepository;
import june.songmelier.repository.CommentStatusRepository;
import june.songmelier.repository.MemberRepository;
import june.songmelier.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
