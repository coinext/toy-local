package com.exam.toylocal.domain.history;

import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hahms
 * @since 15/11/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Test
    public void testSave() {
        User user = userRepository.findByEmail("user_a@mail.com").get();

        History history = new History();
        history.setUser(user);
        history.setKeyword("하우스");
        historyRepository.save(history);

        History reHistory = historyRepository.findById(1L).get();

        Assert.assertEquals(history.getKeyword(), reHistory.getKeyword());
        Assert.assertEquals(history.getUser().getId(), reHistory.getUser().getId());
    }

}