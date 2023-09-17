package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConnectedMember {

    @Id @GeneratedValue
    private Long id;
    private Long connectedId;

    public ConnectedMember(Long connectedId) {
        this.connectedId = connectedId;
    }
}
