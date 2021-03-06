package com.exam.toylocal.domain.history;

import com.exam.toylocal.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Data
@Entity
@Table
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @ManyToOne
    User user;

    String keyword;

    @CreationTimestamp
    @Getter
    private ZonedDateTime created; // 생성일자
}
