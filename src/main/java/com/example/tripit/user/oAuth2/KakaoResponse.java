package com.example.tripit.user.oAuth2;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {

        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {

        return "kakao";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {

        return attribute.get("account_email").toString();
    }

    @Override
    public String getName() {

        return attribute.get("profile_nickname").toString();
    }


    @Override
    public String getGender(){
        return attribute.get("gender").toString();
    }

    @Override
    public String getBirthYear() {
        return "";
    }


    @Override
    public String getBirthday(){
        return attribute.get("birthday").toString();
    }

}
