package com.blogforum.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Getter @Setter
public class SignupRequest {
    @NotBlank @Size(min = 3, max = 20)private String username;
    @NotBlank @Size(max = 50) @Email private String email;
    @NotBlank @Size(min = 6, max = 40) private String password;
}
