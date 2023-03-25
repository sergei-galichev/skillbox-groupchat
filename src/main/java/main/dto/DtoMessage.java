package main.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoMessage {

    @Getter
    @Setter
    private String datetime;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String text;
}
