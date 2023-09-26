package foorun.unieat.api.controller;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatServerErrorException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.service.bookmark.BookMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/bookmark")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookmarkService;

    private final JwtProvider jwtProvider;
    private final UniEatMemberRepository uniEatMemberRepository;

    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity isBookMark(HttpServletRequest request, @PathVariable Long restaurantId) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        UniEatMemberEntity member = jwtProvider.resolveToken(token, uniEatMemberRepository);

        boolean isMarked = false;
        try {
            isMarked = bookmarkService.isBookMark(member, restaurantId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UniEatServerErrorException();
        }

        return UniEatCommonResponse.success(isMarked);
    }

    @GetMapping(value = "/list")
    public ResponseEntity getBookMarks(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        UniEatMemberEntity member = jwtProvider.resolveToken(token, uniEatMemberRepository);

        List<Long> restaurantIds;
        try {
            restaurantIds = bookmarkService.getBookMarks(member);
            if (restaurantIds == null) restaurantIds = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UniEatServerErrorException();
        }

        return UniEatCommonResponse.success(restaurantIds);
    }

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity insertBookMark(HttpServletRequest request, @PathVariable Long restaurantId) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        UniEatMemberEntity member = jwtProvider.resolveToken(token, uniEatMemberRepository);

        try {
            bookmarkService.addBookMark(member, restaurantId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UniEatServerErrorException();
        }

        return UniEatCommonResponse.success();
    }

    @DeleteMapping(value = "/{restaurantId}")
    public ResponseEntity removeBookMark(HttpServletRequest request, @PathVariable Long restaurantId) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        UniEatMemberEntity member = jwtProvider.resolveToken(token, uniEatMemberRepository);

        try {
            bookmarkService.removeBookMark(member, restaurantId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UniEatServerErrorException();
        }

        return UniEatCommonResponse.success();
    }
}