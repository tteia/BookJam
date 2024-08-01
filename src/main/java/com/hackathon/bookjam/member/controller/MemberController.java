package com.hackathon.bookjam.member.controller;

import com.hackathon.bookjam.common.auth.JwtTokenProvider;
import com.hackathon.bookjam.member.domain.Member;
import com.hackathon.bookjam.member.dto.MemberLoginDto;
import com.hackathon.bookjam.member.dto.MemberSaveRqDto;
import com.hackathon.bookjam.member.service.MemberService;
import com.hackathon.bookjam.common.dto.CommonResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/member")
@RestController
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;


    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider, RedisTemplate<String, Object> redisTemplate) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@RequestBody MemberSaveRqDto saveDto){
        memberService.memberRegister(saveDto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "SUCCESS", saveDto);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto loginDto){
        Member member = memberService.login(loginDto);
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRole().toString());
        redisTemplate.opsForValue().set(member.getEmail(), refreshToken, 240, TimeUnit.HOURS);

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", member.getId());
        loginInfo.put("token", jwtToken);
        loginInfo.put("refreshToken", refreshToken);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "Success", loginInfo);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

}
