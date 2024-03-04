package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> { //<사용 할 Entity Class, pk의 datatype>

    // 이메일로 회원 정보 조회(select * from member_talbe where member_email=?)
    // Optional : null 방지하는 기능
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
