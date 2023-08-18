package com.junhyxxn.back.domain.entity;

import com.junhyxxn.back.domain.type.BaseTimeEntity;
import com.junhyxxn.back.domain.type.Gender;
import com.junhyxxn.back.domain.type.Provider;
import com.junhyxxn.back.domain.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint (
                columnNames = {"email", "provider"}
        )},
        indexes = {
                @Index(name = "email_provider_idx", columnList = "email, provider")
        }
)
@DynamicInsert // table default value 적용되도록 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class User extends BaseTimeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @Enumerated(EnumType.STRING)
        @ColumnDefault("'ROLE_USER'")
        @Builder.Default
        private Role role = Role.ROLE_USER;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        @NotNull
        @Email
        @Column(length = 50)
        private String email;

        @NotNull
        private Provider provider;

        @NotNull
        private String password;

        @Column(length = 50)
        private String nickname;

        @Column
        private Integer age;

}
