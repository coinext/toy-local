package com.exam.toylocal.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 12/11/2018
 */
@Data
@Entity
@Table
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String email;       // 이메일 (로그인 값)

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;    // 패스워드

    @Column(nullable = false)
    private String name;        // 유저 이름

    @Column(columnDefinition = "bit(1) DEFAULT 1")
    private Boolean enabled = true;

    @CreationTimestamp
    @Getter
    private ZonedDateTime created; // 생성일자

    public User(String email, String password, String name) {
        this.email = email;
        this.name = name;
    }
}
