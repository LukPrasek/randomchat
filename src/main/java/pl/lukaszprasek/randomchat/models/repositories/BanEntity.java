package pl.lukaszprasek.randomchat.models.repositories;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bans")
public class BanEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "IP")
    private String ip;
    @Column(name="data")
    private LocalDateTime dateTime;
}
