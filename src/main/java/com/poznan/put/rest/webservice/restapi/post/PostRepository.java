package com.poznan.put.rest.webservice.restapi.post;

import com.poznan.put.rest.webservice.restapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<User, Integer> {

}
