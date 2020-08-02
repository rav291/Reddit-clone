package com.devilepsy.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devilepsy.redditclone.model.Subreddit;

@Repository
public interface SubredditRepo extends JpaRepository<Subreddit, Long>{

}
