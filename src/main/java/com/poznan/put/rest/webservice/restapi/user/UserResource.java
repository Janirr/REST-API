package com.poznan.put.rest.webservice.restapi.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    // private fields
    private final UserDaoService service;
    // public Constructor
    public UserResource(UserDaoService userDaoService) {
        this.service = userDaoService;
    }
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id){
        User user = service.findOne(id);
        if (user==null){
            throw new UserNotFoundException("id"+id);
        }
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user){
        // add user into the ArrayList in Service
        User savedUser = service.save(user);
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
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id){
        service.deleteById(id);
    }
    @PatchMapping("/users/{id}")
    public User updateUserById(@PathVariable int id, @RequestBody String name, @RequestBody LocalDate birthDate){
        User user = service.updateUser(id,name,birthDate);
        if (user==null){
            throw new UserNotFoundException("id: "+id);
        }
        return user;
    }
}
