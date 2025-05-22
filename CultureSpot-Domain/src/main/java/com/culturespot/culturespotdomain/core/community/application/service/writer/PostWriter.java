package com.culturespot.culturespotdomain.core.community.application.service.writer;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.domain.entity.PostImage;
import com.culturespot.culturespotdomain.core.community.infrastructure.persistence.PostRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostWriter {

    private final PostRepository postRepository;

    public void register(Post post) {
        postRepository.save(post);
    }

    public Post registerPost(User user, String title, String content, List<MultipartFile> images) {
        List<PostImage> postImages = addImages(images);
        Post createPost = PostWriter.createPost(user, title, content, postImages);
        return postRepository.save(createPost);
    }

    public static Post createPost(User user, String title, String content, List<PostImage> images) {
        Post post = Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .postImages(new ArrayList<>())
                .build();

        post.addImages(images);

        return post;
    }

    public List<PostImage> addImages(List<MultipartFile> images) {
        List<PostImage> postImages = new ArrayList<>();

        if (images != null) {
            for (MultipartFile image : images) {
                PostImage postImage = PostImageWriter.of(image);
                postImages.add(postImage);
            }
        }

        return postImages;
    }
}
