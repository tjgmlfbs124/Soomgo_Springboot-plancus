package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.Member;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member save(Member member);

    @Query("select m from Member m where member_id = :memberId")
    List<Member> findMemberByLogId(@Param("memberId") String memberId);

    @Query("select m from Member m where member_id = :memberId and member_pw = :memberPw")
    List<Member> findMemberByLogIdWithPw(@Param("memberId") String memberId, @Param("memberPw") String memberPw);

    @Query(value="SELECT * FROM MEMBER LEFT JOIN PF_DETAIL ON MEMBER.MEM_ID = PF_DETAIL.PFD_MEM_ID WHERE PF_DETAIL.PFH_ID=:headId", nativeQuery=true)
    List<Member> findMemberByDetail(@Param("headId") Long headId);
}
