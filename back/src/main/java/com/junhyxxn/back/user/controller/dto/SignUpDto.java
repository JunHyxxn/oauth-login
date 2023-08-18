package com.junhyxxn.back.user.controller.dto;

import com.junhyxxn.back.domain.type.Gender;
import com.junhyxxn.back.domain.type.Provider;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
public class SignUpDto {

    @NotNull
    @Email
    @Size(max = 50)
    private String email;
    @NotNull
    private Provider provider;
    @NotNull
    private String password;
    @Size(max = 50)
    private String nickname;
    private Gender gender;
    private Integer age;

    public void encodingPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

}
