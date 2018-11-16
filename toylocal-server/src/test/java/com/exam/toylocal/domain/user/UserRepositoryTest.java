package com.exam.toylocal.domain.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hahms
 * @since 16/11/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGetById() {
        User user = userRepository.findById(1L).get();

        Assert.assertEquals("user_a@mail.com", user.getEmail());
        Assert.assertEquals(1L, (long) user.getId());
    }

    @Test
    public void testGetByEmail() {
        User user = userRepository.findByEmail("user_b@mail.com").get();

        Assert.assertEquals("user_b@mail.com", user.getEmail());
        Assert.assertEquals(2L, (long) user.getId());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("tester");
        user.setPassword("test");
        userRepository.save(user);

        User retrieveUser = userRepository.findByEmail("test@test.com").get();

        Assert.assertEquals(user.getEmail(), retrieveUser.getEmail());
        Assert.assertEquals(3L, (long) retrieveUser.getId());
    }

}