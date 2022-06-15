package com.acme.blogging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.repository.TagRepository;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.exception.ResourceNotFoundException;
import com.acme.blogging.service.PostServiceImpl;

@ExtendWith(SpringExtension.class)
public class PostServiceImplTest {

    @MockBean
    private PostRepository postRepository;
    
    @MockBean
    private TagRepository tagRepository;
    
    @Autowired
    private PostService postService;


    @TestConfiguration
    static class PostServiceImplTestConfiguration{
        @Bean
        public PostService postService(){
            return new PostServiceImpl();
        }
    }

    @Test
    @DisplayName("when GetPostByTitle With Valid Title With Valid Title Then Returns Post")
    public void whenGetPostByTitleWithValidTitleWithValidTitleThenReturnPost(){
        //Arrange
        String title = "Great Post";
        Post post = new Post().setId(1L).setTitle(title);
        when(postRepository.findByTitle(title)).thenReturn(Optional.of(post));
        //Act
        Post foundPost = postService.getPostByTitle(title);

        //Assert
        assertThat(foundPost.getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("when GetPostByTitle With Invalid Title Then ResourceNotFoundException")
    public void whenGetPostByTitleWithInvalidTitleThenResourceNotFoundException(){
        //arrange
        String title = "Great Post";
        String template = "Resource %s not found for %s with value %s";
        when(postRepository.findByTitle(title)).thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "Post", "Title", title);
        //act
        Throwable exception = catchThrowable(() -> {
            Post foundPost = postService.getPostByTitle(title);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(expectedMessage);
    }
}
