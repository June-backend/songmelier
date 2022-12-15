package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MellonChart extends TimeStamped {

    @Id
    @Column(name = "mellon_chart_id")
    private Long id;

    private MellonChart(Long id) {
        this.id = id;
    }

    public static MellonChart createMellonChart(Long id) {
        return new MellonChart(id);
    }
}
