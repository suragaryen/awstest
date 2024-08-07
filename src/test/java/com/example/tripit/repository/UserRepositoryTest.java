package com.example.tripit.repository;

import com.example.tripit.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByEmail(){
       String user = String.valueOf(userRepository.findByEmail("johndoe@example.com"));
        System.out.println(user);
    }


}