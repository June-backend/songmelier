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
public class TjChart extends TimeStamped {

    @Id
    @Column(name = "tj_chart_id")
    private Long id;

    private TjChart(Long id) {
        this.id = id;
    }

    public static TjChart createTjChart(Long id) {
        return new TjChart(id);
    }
}
