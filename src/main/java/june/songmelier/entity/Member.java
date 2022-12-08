package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamped {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String username;
    private String imageUrl;
    private String introduce;

    @OneToMany(mappedBy = "member")
    private List<Favor> favorList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Evaluation> evaluationList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommentStatus> commentStatusList = new ArrayList<>();


    //--------------------------------------------------- 생성자 ----------------------------------------------------------//
    private Member(String email, String password, String username, String imageUrl, String introduce) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
    }

    private Member(Long memberId) {
        this.id = memberId;
    }

    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//


    public static Member createMember(String email, String password, String username, String imageUrl, String introduce) {
        Member member = new Member(email, password, username, imageUrl, introduce);
        return member;
    }

    public static Member createIdMember(Long memberId) {
        return new Member(memberId);
    }

    //--------------------------------------------------- 비즈니스 로직 ----------------------------------------------------------//

    public void putMember(String username, String imageUrl, String introduce) {
        this.username = username;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
    }
}
