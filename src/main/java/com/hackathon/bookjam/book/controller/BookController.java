package com.hackathon.bookjam.book.controller;


import com.hackathon.bookjam.book.dto.BookRegisterRqDto;
import com.hackathon.bookjam.book.service.BookService;
import com.hackathon.bookjam.common.dto.CommonResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;


    @PostMapping("/register")
    public ResponseEntity<?> BookRegister(@RequestBody BookRegisterRqDto bookRegisterRqDto){
        bookService.bookRegister(bookRegisterRqDto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "책 등록 완료 !", bookRegisterRqDto);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }
}
