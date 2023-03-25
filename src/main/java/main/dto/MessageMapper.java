package main.dto;

import main.model.Message;

import java.time.format.DateTimeFormatter;

public class MessageMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    public static DtoMessage map(Message message){
        return DtoMessage.builder()
                .username(message.getUser().getName())
                .datetime(message.getDateTime().format(FORMATTER))
                .text(message.getMessage())
                .build();
    }
}
