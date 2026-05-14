package com.narcis.gamelogged.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("user_games")
public class UserGame {
    @Id
    private Long id;
    private Long userId;
    private Long gameId;
    private String status;
    private Integer rating;
    private String review;
    private LocalDateTime createdAt;
}
