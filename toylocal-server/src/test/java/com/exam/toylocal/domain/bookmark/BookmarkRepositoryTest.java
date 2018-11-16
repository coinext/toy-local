package com.exam.toylocal.domain.bookmark;

import com.exam.toylocal.domain.book.BookVendor;
import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.history.History;
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
 * @since 16/11/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookmarkRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Test
    public void testSave() {
        User user = userRepository.findByEmail("user_a@mail.com").get();

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setVendor(BookVendor.kakao);
        bookmark.setIsbn("8996991341 9788996991342");

        bookmarkRepository.save(bookmark);

        Page<Bookmark> bookmarkPage = bookmarkRepository.findByUser(user, null);

        Assert.assertEquals(1, bookmarkPage.getContent().size());
        Assert.assertEquals(bookmark.getIsbn(), bookmarkPage.getContent().get(0).getIsbn());
    }

    @Test
    public void testGetsBookmarkASC() {
        User user = userRepository.findByEmail("user_a@mail.com").get();
        orderDateSetup(user);

        FieldSort fieldSort = new FieldSort("isbn", Sort.Direction.ASC);
        PageRequest pageRequest = PageRequest.of(0, 2, fieldSort.getDirection(), fieldSort.getField());
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user, pageRequest).getContent();

        Assert.assertEquals("0838877028 9780838877029", bookmarks.get(0).getIsbn());
        Assert.assertEquals(2, bookmarks.size());
    }

    private void orderDateSetup(User user) {
        Arrays.asList("8996991341 9788996991342", "8956051798 9788956051796", "0838877028 9780838877029").forEach(isbn -> {
            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setVendor(BookVendor.kakao);
            bookmark.setIsbn(isbn);
            bookmarkRepository.save(bookmark);
        });
    }
}