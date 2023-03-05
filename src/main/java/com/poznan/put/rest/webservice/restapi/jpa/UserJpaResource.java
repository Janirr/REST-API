package com.poznan.put.rest.webservice.restapi.jpa;

import com.poznan.put.rest.webservice.restapi.user.User;
import com.poznan.put.rest.webservice.restapi.user.UserDaoService;
import com.poznan.put.rest.webservice.restapi.user.UserNotFoundException;
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
public class UserJpaResource {
    // private fields
    private final UserDaoService service;
    private final UserRepository repository;
    // public Constructor

    public UserJpaResource(UserDaoService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }
    @GetMapping("/jpa/users/{id}")
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
    @PostMapping("/jpa/users")
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
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUserById(@PathVariable int id){
        service.deleteById(id);
    }
    @PatchMapping("/jpa/users/{id}")
    public User updateUserById(@PathVariable int id, @RequestBody String name, @RequestBody LocalDate birthDate){
        User user = service.updateUser(id,name,birthDate);
        if (user==null){
            throw new UserNotFoundException("id: "+id);
        }
        return user;
    }
}
