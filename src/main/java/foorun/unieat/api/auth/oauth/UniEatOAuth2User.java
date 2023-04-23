package foorun.unieat.api.auth.oauth;

import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.common.rules.AgeGroupType;
import foorun.unieat.common.rules.GenderType;
import foorun.unieat.common.rules.SocialLoginType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UniEatOAuth2User implements OAuth2User {
    private final UniEatMemberEntity USER;
    private final Map<String, Object> ATTRIBUTES;
    private UniEatOAuth2User(UniEatMemberEntity user, Map<String, Object> attributes) {
        this.USER = user;
        this.ATTRIBUTES = attributes;
    }

    public static UniEatOAuth2User create(UniEatMemberEntity user) {
        return create(user, new HashMap<>());
    }
    public static UniEatOAuth2User create(UniEatMemberEntity user, Map<String, Object> attributes) {
        return new UniEatOAuth2User(user, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return ATTRIBUTES;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return USER.getAuthorities();
    }

    @Override
    public String getName() {
        return USER.getUsername();
    }
}