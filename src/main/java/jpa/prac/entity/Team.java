package jpa.prac.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // 읽기전용 , 연관관계 주인이 아님
    private List<Member> members = new ArrayList<>(); // ArraytList로 초기화 , nullException 처리, 관례

}
