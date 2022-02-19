package com.example.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Controller
@Slf4j
public class AppController implements WebSocketHandler{
    @Autowired
    private PostService postService;

    @MessageMapping("/addPost")
    @SendTo("/topic/posts")
    public Post addPost(Post post) {
        return postService.addPost(post);
    }

    @MessageMapping("/getPosts")
    @SendTo("/topic/posts")
    public List<Post> getAllPosts() {
        return postService.getAll();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session.toString());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info(session.toString() + closeStatus.toString());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
