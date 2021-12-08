package jpa.prac;


import jpa.prac.entity.*;
import jpa.prac.repository.MemberRepository;
import org.aspectj.weaver.ast.Or;
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

        em.flush();
        em.clear();

        System.err.println("======================");
        Member findMember = em.find(Member.class, member.getId());
        List<Member> members = findMember.getTeam().getMembers();
//        List<Member> members = findTeam.getMembers();

        for (Member memberFind : members) {
            System.out.println("memberFind = " + memberFind.getUsername());
        }
    }

    @Test
    @Rollback(value = false)
    public void test2() {
        Item item1 = new Item();
        item1.setName("book");
        em.persist(item1);

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item1);
        em.persist(orderItem);

        Order order = new Order();
        order.addOrderItem(orderItem);
        em.persist(order);
    }

    @Test
    @Rollback(value = false)
    public void test3() {
        Member member = new Member();
        member.setUsername("tester");
        em.persist(member);

        Team team = new Team();
        team.setName("Ateam");
        team.getMembers().add(member);
        em.persist(team);
    }
}
