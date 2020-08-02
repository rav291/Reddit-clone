package com.devilepsy.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.devilepsy.redditclone.model.Comment;

@EnableJpaRepositories
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>{

}
