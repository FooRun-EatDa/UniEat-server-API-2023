package foorun.unieat.common.auth;

import foorun.unieat.common.rules.AgeGroupType;
import foorun.unieat.common.rules.GenderType;

import java.util.Date;

public interface UniEatDefaultOAuth2UserInfo {
    String getProvider();
    String getUsername();
    String getPassword();
    GenderType getGender();
    AgeGroupType getAgeRange();
    Date getBirthDay();
}