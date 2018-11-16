package com.exam.toylocal.domain.history;

import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author hahms
 * @since 15/11/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryRepositoryTest {
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

    @Test
    public void testGetsHistoryASC() {
        User user = userRepository.findByEmail("user_a@mail.com").get();
        orderDateSetup(user);

        FieldSort fieldSort = new FieldSort("keyword", Sort.Direction.ASC);
        PageRequest pageRequest = PageRequest.of(0, 2, fieldSort.getDirection(), fieldSort.getField());
        List<History> histories = historyRepository.findByUser(user, pageRequest).getContent();

        Assert.assertEquals("A", histories.get(0).getKeyword());
        Assert.assertEquals(2, histories.size());
    }

    @Test
    public void testGetsHistoryDESC() {
        User user = userRepository.findByEmail("user_a@mail.com").get();
        orderDateSetup(user);

        FieldSort fieldSort = new FieldSort("keyword", Sort.Direction.DESC);
        PageRequest pageRequest = PageRequest.of(0, 2, fieldSort.getDirection(), fieldSort.getField());
        List<History> histories = historyRepository.findByUser(user, pageRequest).getContent();

        Assert.assertEquals("C", histories.get(0).getKeyword());
        Assert.assertEquals(2, histories.size());
    }

    @Test
    public void testGetsHistoryPage1() {
        User user = userRepository.findByEmail("user_a@mail.com").get();
        orderDateSetup(user);

        FieldSort fieldSort = new FieldSort("keyword", Sort.Direction.ASC);
        PageRequest pageRequest = PageRequest.of(0, 2, fieldSort.getDirection(), fieldSort.getField());
        Page<History> historyPage = historyRepository.findByUser(user, pageRequest);

        long totalCount = historyPage.getTotalElements();
        long pageCount = historyPage.getNumberOfElements();
        boolean isNext = historyPage.getTotalPages() -1 > historyPage.getNumber();

        Assert.assertEquals(3, totalCount);
        Assert.assertEquals(2, pageCount);
        Assert.assertTrue(isNext);
    }

    @Test
    public void testGetsHistoryPage2() {
        User user = userRepository.findByEmail("user_a@mail.com").get();
        orderDateSetup(user);

        FieldSort fieldSort = new FieldSort("keyword", Sort.Direction.ASC);
        PageRequest pageRequest = PageRequest.of(1, 2, fieldSort.getDirection(), fieldSort.getField());
        Page<History> historyPage = historyRepository.findByUser(user, pageRequest);

        long totalCount = historyPage.getTotalElements();
        long pageCount = historyPage.getNumberOfElements();
        boolean isNext = historyPage.getTotalPages() - 1 > historyPage.getNumber();

        Assert.assertEquals(3, totalCount);
        Assert.assertEquals(1, pageCount);
        Assert.assertFalse(isNext);
    }

    private void orderDateSetup(User user) {
        Arrays.asList("B", "A", "C").forEach(keyword -> {
            History history = new History();
            history.setUser(user);
            history.setKeyword(keyword);
            historyRepository.save(history);
        });
    }
}