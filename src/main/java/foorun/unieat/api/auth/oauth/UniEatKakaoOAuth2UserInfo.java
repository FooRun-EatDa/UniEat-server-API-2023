package foorun.unieat.api.auth.oauth;

import foorun.unieat.common.auth.UniEatDefaultOAuth2UserInfo;
import foorun.unieat.common.rules.AgeGroupType;
import foorun.unieat.common.rules.GenderType;

import java.util.Date;
import java.util.Map;

public class UniEatKakaoOAuth2UserInfo implements UniEatDefaultOAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final Map<String, Object> attributesProperties;
    private final Map<String, Object> attributesAccount;
    private final Map<String, Object> attributesProfile;


    public UniEatKakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesProperties = (Map<String, Object>) attributes.get("properties");
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributes.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao_";
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("id");
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public GenderType getGender() {
        return GenderType.valueOf((String) attributes.get("gender"));
    }

    @Override
    public AgeGroupType getAgeRange() {
        return null;
    }

    @Override
    public Date getBirthDay() {
        return null;
    }
}
