package com.oqwn.backend.model.response;

import com.oqwn.backend.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String email;
    private String phone;
    private String avatarUrl;
    private String gender;
    private String bio;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.avatarUrl = user.getAvatarUrl();
        this.gender = user.getGender();
        this.bio = user.getBio();
    }
}
