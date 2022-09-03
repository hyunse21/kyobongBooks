package com.kyobong.book;

import com.kyobong.book.service.book.BookSaveDto;
import com.kyobong.book.service.book.BookService;
import com.kyobong.book.service.category.CategorySaveDto;
import com.kyobong.book.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final BookService bookService;
    private final CategoryService categoryService;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {

        categoryService.add(new CategorySaveDto("문학", ""));
        categoryService.add(new CategorySaveDto("경제경영", ""));
        categoryService.add(new CategorySaveDto("인문학", ""));
        categoryService.add(new CategorySaveDto("IT", ""));
        categoryService.add(new CategorySaveDto("과학", ""));


        bookService.add(new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", Arrays.asList("문학")));
        bookService.add(new BookSaveDto("단순하게 배부르게", "현영서", "Y", "정상", Arrays.asList("문학")));
        bookService.add(new BookSaveDto("게으른 사랑", "권태영", "Y", "정상", Arrays.asList("문학")));
        bookService.add(new BookSaveDto("트랜드 코리아 2322", "권태영", "Y", "정상", Arrays.asList("경제경영")));
        bookService.add(new BookSaveDto("초격자 투자", "장동혁", "Y", "정상", Arrays.asList("경제경영")));
        bookService.add(new BookSaveDto("파이어족 강환국의 하면 되지 않는다! 퀀트 투자", "홍길동", "Y", "정상", Arrays.asList("경제경영")));
        bookService.add(new BookSaveDto("진심보다 밥", "이서연", "Y", "정상", Arrays.asList("인문학")));
        bookService.add(new BookSaveDto("실패에 대하여 생각하지 마라", "위성원", "Y", "정상", Arrays.asList("인문학")));
        bookService.add(new BookSaveDto("실리콘밸리 리더십 쉽다", "지승열", "Y", "정상", Arrays.asList("IT")));
        bookService.add(new BookSaveDto("데이터분석을 위한 A 프로그래밍", "지승열", "Y", "정상", Arrays.asList("IT")));
        bookService.add(new BookSaveDto("인공지능1-12", "장동혁", "Y", "정상", Arrays.asList("IT")));
        bookService.add(new BookSaveDto("-1년차 게임 개발", "위성원", "Y", "정상", Arrays.asList("IT")));
        bookService.add(new BookSaveDto("Skye가 알려주는 피부 채색의 비결", "권태영", "Y", "정상", Arrays.asList("IT")));
        bookService.add(new BookSaveDto("자연의 발전", "장지명", "Y", "정상", Arrays.asList("과학")));
        bookService.add(new BookSaveDto("코스모스 필 무렵", "이승열", "Y", "정상", Arrays.asList("과학")));

    }
}
