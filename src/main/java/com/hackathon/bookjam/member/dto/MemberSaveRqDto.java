package com.hackathon.bookjam.member.dto;

import com.hackathon.bookjam.member.domain.Member;
import com.hackathon.bookjam.common.domain.Address;
import com.hackathon.bookjam.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveRqDto {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private Address address;

    public Member toEntity(String encodedPw) {
        Member member = Member.builder()
                .name(this.name)
                .nickname(this.nickname)
                .email(this.email)
                .password(encodedPw)
                .role(Role.USER)
                .address(this.address)
                .build();
        return member;
    }
}
