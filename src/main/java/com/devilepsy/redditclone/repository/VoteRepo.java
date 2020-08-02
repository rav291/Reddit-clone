package com.devilepsy.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devilepsy.redditclone.model.Vote;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long>{

}
