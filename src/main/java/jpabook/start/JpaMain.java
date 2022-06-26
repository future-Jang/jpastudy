package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test"); //엔티티 매니저 팩토리 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 획득


        try {

            tx.begin(); //트랜잭션 시작
            logic(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        String id = "id2";
        Member member = new Member();
        member.setId(id);
        member.setUsername("id2네임");
        member.setAge(20);
        member.setNickName("닉네임");

        //등록
        em.persist(member);

        /*insert
        into
            MEMBER
            (age, nickName, NAME, ID)
        values
            (?, ?, ?, ?)*/

        //수정
        member.setAge(15);
        member.setNickName("신규닉네임");

        /*update
            MEMBER
        set
            age=?,
            nickName=?,
            NAME=?
        where
            ID=?*/

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        /* select
            member0_.ID as ID1_0_,
            member0_.age as age2_0_,
            member0_.nickName as nickName3_0_,
            member0_.NAME as NAME4_0_
        from
            MEMBER member0_ */

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        /* members.size=2 */

        //삭제
        em.remove(member);

        /* delete
        from
            MEMBER
        where
            ID=? */

    }
}
