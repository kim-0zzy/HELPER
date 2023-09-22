package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community {

    @Id @GeneratedValue
    @Column(name = "community_id")
    private Long id;
    private String ot_Username;
    private String ot_Password;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public Community(String ot_Username, String ot_Password, String title, String content, LocalDateTime createDate) {
        this.ot_Username = ot_Username;
        this.ot_Password = ot_Password;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
}
