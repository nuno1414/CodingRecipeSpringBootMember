package com.codingrecipe.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {
    // 회원가입 페이지 출력 요청
    @GetMapping("/save")
    public String saveForm() {

        return "save"; // => templates 폴더의 save.html을 찾아감.
    }

    @PostMapping("/save")
    public String save(@RequestParam("memberEmail") String memberEmail,
                       @RequestParam("memberPassword") String memberPassword,
                       @RequestParam("memberName") String memberName
    ){

        System.out.println("MemberController.save");
        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
        return "index";
    }
}
