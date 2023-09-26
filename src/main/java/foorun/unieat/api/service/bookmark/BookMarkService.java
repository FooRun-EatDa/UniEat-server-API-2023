package foorun.unieat.api.service.bookmark;

import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;

import java.util.List;

public interface BookMarkService {
    boolean isBookMark(UniEatMemberEntity user, Long restaurantId);
    List<Long> getBookMarks(UniEatMemberEntity user);
    void addBookMark(UniEatMemberEntity user, Long restaurantId);
    void removeBookMark(UniEatMemberEntity user, Long restaurantId);
}