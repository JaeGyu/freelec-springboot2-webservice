package me.jaegyu.book.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import me.jaegyu.book.springboot.domain.posts.PostsRepository;
import me.jaegyu.book.springboot.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
