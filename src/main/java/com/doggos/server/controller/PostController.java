package com.doggos.server.controller;

import com.doggos.server.model.Comment;
import com.doggos.server.model.Post;
import com.doggos.server.model.User;
import com.doggos.server.payload.request.PostRequest;
import com.doggos.server.repository.CommentRepository;
import com.doggos.server.repository.PostRepository;
import com.doggos.server.repository.UserRepository;
import com.doggos.server.service.StorageException;
import com.doggos.server.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);


    private final StorageService storageService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    public PostController(StorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('VOLUNTEER')")
    public ResponseEntity<Post> create(@RequestPart(name = "post") PostRequest postRequest, @RequestPart(name = "image", required = false) MultipartFile file) {
        try {
            User user = userRepository.findByUsername(postRequest.getUsername()).get();
            String imgPath = null;
            if (file != null) {
                imgPath = storageService.store(file, "DOGGOS", "posts");
            }
            Post newPost = new Post(user, postRequest.getCaption(), postRequest.getTags(), imgPath, new Date(postRequest.getCreatedDate()));

            Post _post = postRepository.save(newPost);
            return new ResponseEntity<Post>(_post, HttpStatus.CREATED);
        } catch (Exception | StorageException e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/image/{id}/{name}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImage(@PathVariable(name = "id") String id, @PathVariable(name = "name") String name) throws IOException {
        return storageService.get(id, name);
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAll() {
        try {
            List<Post> posts = new ArrayList<>();
            postRepository.findAll().forEach(posts::add);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") String id) {
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
