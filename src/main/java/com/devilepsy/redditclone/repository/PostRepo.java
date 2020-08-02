package com.devilepsy.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devilepsy.redditclone.model.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long>{

}
