package com.epam.learn.security;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "user")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;
    private String authority;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Authority(String authority, User user) {
        this.authority = authority;
        this.user = user;
    }
}
