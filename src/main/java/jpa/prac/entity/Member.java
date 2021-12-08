package jpa.prac.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
public class Member {

    @Id @GeneratedValue(generator = "member_seq_generator")
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String username;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


    //연관관계 편의 메서드 : 양방향 연관관계에서 한쪽이 등록될때 다른 한쪽도 같이 등록
//    public void setTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
}
