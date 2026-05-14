package com.narcis.gamelogged.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("games")
public class Game {
    @Id
    private Long id;
    private Integer rawgId;
}
