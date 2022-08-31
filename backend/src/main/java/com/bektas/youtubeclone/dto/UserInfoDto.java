package com.bektas.youtubeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String id;
    private String sub;
    private String nickname;
    private String name;
    private String picture;
    @JsonProperty("updated_at")
    private String updatedAt;
    private String email;
    @JsonProperty("email_verified")
    private boolean emailVerified;
}
