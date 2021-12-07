package jpa.prac;


import jpa.prac.entity.Member;
import jpa.prac.entity.Team;
import jpa.prac.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class test {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void test1() {


        Team team = new Team();
        team.setName("Ateam");
        em.persist(team);

        Member member = new Member();
        member.setUsername("test1");
        member.setTeam(team);
        em.persist(member);

//        em.flush();
//        em.clear();

        System.err.println("======================");
        Member findMember = em.find(Member.class, member.getId());
        List<Member> members = findMember.getTeam().getMembers();
//        List<Member> members = findTeam.getMembers();

        for (Member memberFind : members) {
            System.out.println("memberFind = " + memberFind.getUsername());
        }

//        System.out.println("findTeam.getName()) = " + findTeam.getName());

//        Member member2 = new Member();
//        member2.setUsername("test2");
//        memberRepository.save(member2);

    }

    @Test
    @Rollback(value = false)
    public void test2() {




    }
}
