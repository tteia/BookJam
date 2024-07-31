package com.hackathon.bookjam.member.controller;

import com.hackathon.bookjam.member.dto.MemberSaveRqDto;
import com.hackathon.bookjam.member.service.MemberService;
import com.hackathon.bookjam.common.dto.CommonResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@RequestBody MemberSaveRqDto saveDto){
        memberService.memberRegister(saveDto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "SUCCESS", saveDto);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

}
