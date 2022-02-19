package com.example.websocket;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Override
    public List<Post> findAll();
}
