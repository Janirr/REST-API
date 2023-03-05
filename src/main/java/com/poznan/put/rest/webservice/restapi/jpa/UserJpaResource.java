package com.poznan.put.rest.webservice.restapi.jpa;

import com.poznan.put.rest.webservice.restapi.post.Post;
import com.poznan.put.rest.webservice.restapi.post.PostRepository;
import com.poznan.put.rest.webservice.restapi.user.User;
import com.poznan.put.rest.webservice.restapi.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {
    // private fields
    private final UserRepository repository;
    private final PostRepository postRepository;

    // public Constructor
    public UserJpaResource(UserRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    // display all Users details
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return repository.findAll();
    }

    // display User details
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id:"+id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    // create new User
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user){
        // add user into the ArrayList in Service
        User savedUser = repository.save(user);
        // get new Location for the user to be created in
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        /* return the ResponseEntity when trying to
           create user in location and build it   */
        return ResponseEntity.created(location).build();
    }

    // delete User
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUserById(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List <Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id:"+id);
        }
        return user.get().getPosts();
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List <Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id:"+id);
        }
        return user.get().getPosts();
    }
    // update User
//    @PatchMapping("/jpa/users/{id}")
//    public User updateUserById(@PathVariable int id, @RequestBody String name, @RequestBody LocalDate birthDate){
//        repository.deleteById(id);
//        repository.save(id,name,birthDate);
//    }

}