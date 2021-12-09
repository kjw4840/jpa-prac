package jpa.prac.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
public class Member extends BaseEntity{

    @Id @GeneratedValue(generator = "member_seq_generator")
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String username;

    @Embedded
    private Address address;

    // 값 타입을 여러개 이상 지정할 때 사용, DB는 컬렉션을 저장할 수 없어서 일대다 관계로 풀어서 저장
    @ElementCollection
    @CollectionTable(name = "favorite_food", joinColumns =
    @JoinColumn(name = "member_id"))
    private Set<String> favoriteFoods = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private List<AddressEntity> addressHistory = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
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
