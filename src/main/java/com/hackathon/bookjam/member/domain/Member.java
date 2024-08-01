package com.hackathon.bookjam.member.domain;

import com.hackathon.bookjam.common.domain.Address;
import com.hackathon.bookjam.member.dto.MemberInfoRsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 20, nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;

    public MemberInfoRsDto fromEntity() {
        return MemberInfoRsDto.builder()
                .name(this.name)
                .nickname(this.nickname)
                .address(this.address)
                .build();
    }
}
