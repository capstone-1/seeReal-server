package com.seereal.algi.config.auth;

// 세션에 저장하기 위해서는 직렬화 구현이 필요하다.
// Entity 클래스 직렬화보다는 별도 클래스로 만들어 직렬화하는게 좋다.

import com.seereal.algi.model.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}