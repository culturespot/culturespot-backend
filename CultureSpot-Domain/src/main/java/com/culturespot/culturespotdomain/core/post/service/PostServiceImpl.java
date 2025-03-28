package com.culturespot.culturespotdomain.core.post.service;

import com.culturespot.culturespotdomain.core.global.exception.DomainException;
import com.culturespot.culturespotdomain.core.global.exception.DomainExceptionCode;
import com.culturespot.culturespotdomain.core.image.entity.Image;
import com.culturespot.culturespotdomain.core.image.service.ImageService;
import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.post.entity.PostModifyCommand;
import com.culturespot.culturespotdomain.core.post.repository.PostRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return findPostOrThrow(postId);
    }

    @Override
    @Transactional
    public void createPost(User user, Post post) {
//        User findUser = userService.findUserOrThrow(user.getEmail());

//        PostDetails postDetails = post;
//        Post post = createEmptyPost(findUser, postDetails);
//
//        List<Image> images = imageService.createImage(post, request.images());
//        post.getImages().addAll(images);
//
//        postRepository.save(post);
    }

    @Override
    @Transactional
    public void modifyPost(User user, Long postId, PostModifyCommand command) {
//        Post currentPost = findPostOrThrow(postId);
//
//        PostValidator.validateEditPermission(user.getId(), currentPost.getId());
//
//        Post updatePost = modifyPostDetails(currentPost, request);
//
//        postRepository.save(updatePost);
    }

    @Override
    @Transactional
    public void deletePost(User user, Long postId) {
        Post post = findPostOrThrow(postId);

        PostValidator.validateDeletePermission(user.getId(), postId);

        postRepository.delete(post);
        imageService.deleteImages(postId);
    }

//    public Post createEmptyPost(User user, PostDetails postDetails) {
//        return Post.builder()
//                .user(user)
//                .title(postDetails.title())
//                .content(postDetails.content())
//                .images(new ArrayList<>())
//                .build();
//    }

    public Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.POST_NOT_FOUND));
    }

//    public Post modifyPostDetails(Post post, PostModifyRequest request) {
//        PostDetails postDetails = request.postDetails();
//        List<ImageDetailRequest> deleteImages = request.deleteImage();
//        List<Long> deleteImagesId = deleteImages.stream()
//                .map(ImageDetailRequest::imageId)
//                .toList();
//
//        if (postDetails.title() != null) {
//            post.setTitle(postDetails.title());
//        }
//
//        if (postDetails.content() != null) {
//            post.setContent(postDetails.content());
//        }
//
//        if (request.images() != null && !request.images().isEmpty()) {
//            post.setImages(imageService.createImage(post, request.images()));
//        }
//
//        if (!deleteImages.isEmpty()) {
//            imageService.deleteImages(deleteImagesId);
//        }
//
//        return post;
//    }
}