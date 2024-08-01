package com.hackathon.bookjam.member.dto;

import com.hackathon.bookjam.common.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoRsDto {
    private String name;
    private String nickname;
    private Address address;
}
