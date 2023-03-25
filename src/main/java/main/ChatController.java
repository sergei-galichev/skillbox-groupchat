package main;

import main.dto.DtoMessage;
import main.dto.MessageMapper;
import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/init")
    public Map<String, Boolean> init() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> userOptional = userRepository.findBySessionId(sessionId);
        return Map.of("result", userOptional.isPresent());
    }

    @PostMapping("/auth")
    public Map<String, Boolean> auth(@RequestParam String name) {
        if (Strings.isEmpty(name)) {
            return Map.of("result", false);
        }
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = User.builder()
                .name(name)
                .sessionId(sessionId)
                .build();
        userRepository.saveAndFlush(user);
        return Map.of("result", true);
    }

    @PostMapping("/messages")
    public Map<String, Boolean> sendMessage(@RequestParam String message) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> userOptional = userRepository.findBySessionId(sessionId);
        if (!userOptional.isPresent() || Strings.isEmpty(message)) {
            return Map.of("result", false);
        }
        Message newMessage = Message.builder()
                .message(message)
                .dateTime(LocalDateTime.now())
                .user(userOptional.get())
                .build();
        messageRepository.saveAndFlush(newMessage);
        return Map.of("result", true);
    }

    @GetMapping("/messages")
    public List<DtoMessage> getMessagesList() {
        return messageRepository
                .findAll(Sort.by(Sort.Direction.ASC, "dateTime"))
                .stream()
                .map(MessageMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    public HashMap<Integer, String> getUsersList() {
        return null;
    }
}
