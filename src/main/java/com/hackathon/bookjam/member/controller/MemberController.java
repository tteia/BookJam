package com.hackathon.bookjam.member.controller;

import com.hackathon.bookjam.common.auth.JwtTokenProvider;
import com.hackathon.bookjam.login.MemberInfoDetails;
import com.hackathon.bookjam.member.domain.Member;
import com.hackathon.bookjam.member.dto.MemberInfoRsDto;
import com.hackathon.bookjam.member.dto.MemberLoginDto;
import com.hackathon.bookjam.member.dto.MemberSaveRqDto;
import com.hackathon.bookjam.member.service.MemberService;
import com.hackathon.bookjam.common.dto.CommonResDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@RequestBody MemberSaveRqDto saveDto){
        memberService.memberRegister(saveDto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "회원가입 완료!", saveDto);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto loginDto){
        Member member = memberService.login(loginDto);
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRole().toString());
        redisTemplate.opsForValue().set(member.getEmail(), refreshToken, 240, TimeUnit.HOURS);

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", member.getId());
        loginInfo.put("token", jwtToken);
        loginInfo.put("refreshToken", refreshToken);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "로그인 완료!", loginInfo);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> myPage(@AuthenticationPrincipal MemberInfoDetails memberInfoDetails){
        MemberInfoRsDto infoDto = memberService.mypage(memberInfoDetails);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,"내 정보 조회 완료 !", infoDto);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

}
