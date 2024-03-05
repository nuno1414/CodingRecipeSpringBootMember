package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // repository의 save 메서드 호출(조건 : entity 객체를 넘겨줘야 함)
        // 1. dto -> entity 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        // 2. repository의 save 메서드 호출
        memberRepository.save(memberEntity); // save : jpa가 제공하는 method

    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회 함
        // 2. DB에서 조회 한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단

        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if ( byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            // Optional로 감싸진 객체의 안에 있는 Entity 객체를 가지고 나옴
            MemberEntity memberEntity = byMemberEmail.get();
            if ( memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword()) ) {
                // 비밀번호가 일치
                // Entity -> DTO 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호가 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원 정보가 없다)
            return null;
        }

    }

    public List<MemberDTO> findAll() {

        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for(MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }

        return memberDTOList;
    }

    public MemberDTO findById(Long id) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        // save : id가 없으면 insert, 있으면 update  수행
        MemberEntity memberEntity = MemberEntity.toUpdateMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public void deleteById(Long id) {

        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);

        if (optionalMemberEntity.isPresent()) {
            // 조회결과가 있다 -> 사용 할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용 할 수 있다.
            return "ok";
        }
    }
}
