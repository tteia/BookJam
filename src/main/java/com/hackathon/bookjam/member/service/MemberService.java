package com.hackathon.bookjam.member.service;

import com.hackathon.bookjam.login.MemberInfoDetails;
import com.hackathon.bookjam.member.domain.Member;
import com.hackathon.bookjam.member.dto.MemberInfoRsDto;
import com.hackathon.bookjam.member.dto.MemberLoginDto;
import com.hackathon.bookjam.member.dto.MemberSaveRqDto;
import com.hackathon.bookjam.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member memberRegister(MemberSaveRqDto saveDto) {
        if(memberRepository.findByEmail(saveDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if(memberRepository.findByNickname(saveDto.getNickname()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        if(saveDto.getPassword().length() < 8){
            throw  new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = saveDto.toEntity(passwordEncoder.encode(saveDto.getPassword()));
        return memberRepository.save(member);
    }

    public Member login(MemberLoginDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 이메일입니다."));
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    public MemberInfoRsDto mypage(MemberInfoDetails memberInfoDetails) {
        Member member = memberRepository.findByEmail(memberInfoDetails.getUsername()).orElseThrow(()->new EntityNotFoundException("회원을 찾을 수 없습니다."));
        return member.fromEntity();
    }
}
