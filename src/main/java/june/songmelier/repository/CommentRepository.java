package june.songmelier.repository;

import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Comment;
import june.songmelier.entity.CommentStatus;
import june.songmelier.entity.Favor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySongId(Long songId);

//    @Query(value="select c from Comment c join fetch c.member order by c.likeCount desc")
//    List<Comment> findBySongIdAndMember(Long songId);

    @Query(value="select c from Comment c join fetch c.member order by c.likeCount desc")
    Slice<Comment> findBySongIdAndMember(Long songId, Pageable pageable);

    //내가 작성한 코맨트 갖고오기
    Slice<Comment> findAllByMemberId(Long memberId,Pageable pageable);

}
