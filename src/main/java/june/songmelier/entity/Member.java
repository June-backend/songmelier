package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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


//--------------------------------------------------- 생성자 ----------------------------------------------------------//
    private Member(String email, String password, String username, String imageUrl, String introduce) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
    }
    
    public static Member createMember(String email, String password, String username, String imageUrl, String introduce) {
        Member member = new Member(email, password, username, imageUrl, introduce);
        return member;
    }
}
