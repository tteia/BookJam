package com.hackathon.bookjam.login;

import com.hackathon.bookjam.member.domain.Member;
import com.hackathon.bookjam.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MemberInfoDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberInfoDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(()->new EntityNotFoundException("가입되지 않은 이메일입니다."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
        return new MemberInfoDetails(member.getEmail(), member.getPassword(), authorities);
    }
}
