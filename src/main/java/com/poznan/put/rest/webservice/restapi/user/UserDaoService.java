package com.poznan.put.rest.webservice.restapi.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {
    private static final List<User> users = new ArrayList<>();
    private static int usersCount = 0;
    static {
        users.add(new User(++usersCount,"Adam", LocalDate.now().minusYears(23)));
        users.add(new User(++usersCount,"Eve", LocalDate.now().minusYears(26)));
        users.add(new User(++usersCount,"Jim", LocalDate.now().minusYears(18)));
    }

    public List<User> findAll(){
        return users;
    }
    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }
    public User findOne(int id){
        //Loop approach
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void deleteById(int id) {
        users.removeIf(user -> user.getId() == id);
    }

    public User updateUser(int id, String name, LocalDate birthDate) {
        User user = findOne(id);
        user.setName(name);
        user.setBirthDate(birthDate);
        return user;
    }
}
