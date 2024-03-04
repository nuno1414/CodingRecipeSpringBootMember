package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.repository.MemberRepository;
import com.codingrecipe.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/save")
    public String saveForm() {

        return "save"; // => templates 폴더의 save.html을 찾아감.
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){

        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);

        memberService.save(memberDTO);

        return "login";
    }

    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session
    ) {

        MemberDTO loginResult =  memberService.login(memberDTO);

        if (loginResult != null) {
            // 로그인 성공
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        } else {
            // 로그인 실패
            return "login";
        }
    }

    @GetMapping("/")
    public String finAll(Model model){

        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);

        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {

        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);

        return "detail";
    }

    @GetMapping("/update")
    public String updateForm(Model model, HttpSession session){
        String myEmail = (String)session.getAttribute("loginEmail");

        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {

        memberService.update(memberDTO);

        return "redirect:/member/"+memberDTO.getId();
    }
}
