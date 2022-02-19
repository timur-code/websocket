package com.example.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post addPost(Post post) {
        postRepository.save(post);
        return post;
    }

    public List<Post> getAll() {
        return new ArrayList<Post>(postRepository.findAll());
    }
}
