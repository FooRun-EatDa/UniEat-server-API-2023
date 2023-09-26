package foorun.unieat.api.service.bookmark.impl;

import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.UnieatMemberBookMarkEntity;
import foorun.unieat.api.model.database.member.entity.primary_key.UniEatBookMarker;
import foorun.unieat.api.model.database.member.repository.UniEatMemberBookMarkRepository;
import foorun.unieat.api.service.bookmark.BookMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {
    private final UniEatMemberBookMarkRepository bookMarkRepository;

    @Override
    public boolean isBookMark(UniEatMemberEntity user, Long restaurantId) {
        UnieatMemberBookMarkEntity entity = bookMarkRepository.findById(UniEatBookMarker.of(user.getProvider(), user.getPrimaryId(), restaurantId)).orElse(null);

        return entity != null;
    }

    @Override
    public List<Long> getBookMarks(UniEatMemberEntity user) {
        List<UnieatMemberBookMarkEntity> entities = bookMarkRepository.findAll();

        return entities.stream().map(entity -> entity.getRestaurantId()).collect(Collectors.toList());
    }

    @Override
    public void addBookMark(UniEatMemberEntity user, Long restaurantId) {
        UnieatMemberBookMarkEntity entity = UnieatMemberBookMarkEntity.builder()
                .provider(user.getProvider())
                .primaryId(user.getPrimaryId())
                .restaurantId(restaurantId)
                .build();

        bookMarkRepository.save(entity);
    }

    @Override
    public void removeBookMark(UniEatMemberEntity user, Long restaurantId) {
        UnieatMemberBookMarkEntity entity = UnieatMemberBookMarkEntity.builder()
                .provider(user.getProvider())
                .primaryId(user.getPrimaryId())
                .restaurantId(restaurantId)
                .build();

        bookMarkRepository.delete(entity);
    }
}