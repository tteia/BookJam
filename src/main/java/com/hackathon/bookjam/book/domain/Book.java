package com.hackathon.bookjam.book.domain;

import com.hackathon.bookjam.author.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    // 한 명의 회원에 여러 개의 주문 OK
    // 주문에 ManyToOne 으로 member 걸어줌
    // 한 명의 저자에 여러 개의 책 OK
    // 책에 ManyToOne 으로 author 걸어줌!

}
