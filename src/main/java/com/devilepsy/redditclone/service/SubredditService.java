package com.devilepsy.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devilepsy.redditclone.DTO.SubredditDto;
import com.devilepsy.redditclone.model.Subreddit;
import com.devilepsy.redditclone.repository.SubredditRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	
	private final SubredditRepo subredditRepository;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto)
	{
		Subreddit save = subredditRepository.save(mapSubredditDto(subredditDto));
		subredditDto.setId(save.getId());
		
		return subredditDto;
	}

	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
		
		return Subreddit.builder().name(subredditDto.getName())
							.description(subredditDto.getDescription())
							.build();
		
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {

		return subredditRepository.findAll()
				.stream()
				.map(this::mapToDto) // mapping from subreddit entity to subredditDto
				.collect(Collectors.toList());
	}
	
	private SubredditDto mapToDto(Subreddit subreddit)
	{
		return SubredditDto.builder().name(subreddit.getName())
						.id(subreddit.getId())
						.numberOfPosts(subreddit.getPosts().size())
						.build();
		
	}

}
