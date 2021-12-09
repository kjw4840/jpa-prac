package jpa.prac;


import jpa.prac.entity.*;
import jpa.prac.entity.casecade.Child;
import jpa.prac.entity.casecade.Parent;
import jpa.prac.entity.items.Book;
import jpa.prac.entity.items.Item;
import jpa.prac.entity.items.Movie;
import jpa.prac.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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

        Book book1 = new Book();
        book1.setName("mBook");
        em.persist(book1);

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(book1);
        em.persist(orderItem);

        Order order = new Order();
        order.addOrderItem(orderItem);
        em.persist(order);
    }

    @Test
    @Rollback(value = false)
    public void test3() {

        Team team1 = new Team();
        team1.setName("Ateam");
        em.persist(team1);
        Team team2 = new Team();
        team2.setName("Bteam");
        em.persist(team2);
        Team team3 = new Team();
        team3.setName("Cteam");
        em.persist(team3);




        Member member1 = new Member();
        member1.setUsername("tester1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("tester2");
        member2.setTeam(team2);
        em.persist(member2);

        Member member3 = new Member();
        member3.setTeam(team3);
        member3.setUsername("tester3");
        em.persist(member3);

        em.flush();
        em.clear();

        List<Member> resultList = em.createQuery("select m From Member m join fetch m.team", Member.class).getResultList();

        for (Member member : resultList) {
            System.out.println("tester:::" + member.getUsername() + "team::" + member.getTeam().getName() );

        }


    }

    @Test
    @Rollback(value = false)
    public void test4() {
        Movie movie = new Movie();
        movie.setName("movie1");
        movie.setDirector("A");
        movie.setActor("B");
        movie.setPrice(10000);
        em.persist(movie);

        Book book = new Book();
        book.setName("book1");
        book.setAuthor("AAA");
        book.setIsbn("10a-2");
        em.persist(book);
    }

    @Test
//    @Rollback(value = false)
    public void test5() {
        Team team = new Team();
        team.setName("aTeam");
        em.persist(team);

        Member member = new Member();
        member.setCreatedBy("kim");
        member.setCreatedDate(LocalDateTime.now());
        member.setUsername("user1");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();
//
        Member findMember = em.find(Member.class, member.getId());
//        System.out.println("findMember.getClass() = " + findMember.getClass());
        System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
        member.getTeam().getName();
        System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
    }

    @Test
    @Rollback(value = false)
    public void casecadeTest() {
        Parent parent = new Parent();

        Child child1 = new Child();
        Child child2 = new Child();
        child1.setName("aaa");
        child2.setName("bbb");

        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(child1);
        em.persist(child2);
        em.persist(parent);

//        Parent findParent = em.find(Parent.class, parent.getId());
//        findParent.getChildList().remove(0);
//        findParent.setName("gasg");
//        System.out.println("test:::" + findParent.getChildList());
//        findParent.getChildList().forEach(c -> {
//            c.setName("cccccc");
//            System.out.println("ccc" + c.getName());
//        });
    }

    @Test
    @Rollback(value = false)
    public void testEmbedded() {
        Member member = new Member();
        member.setUsername("kjw");
        member.setAddress(new Address("ggg","sfaf","23-33"));
        member.getFavoriteFoods().add("치킨");
        member.getFavoriteFoods().add("치킨2");
        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        findMember.getAddressHistory().add(new AddressEntity("a","b","c"));
        findMember.getAddressHistory().add(new AddressEntity("aa","bb","cc"));

        AddressEntity addressEntity = em.find(AddressEntity.class, 1L);
        em.flush();
        em.clear();
    }
}
