package net.renfei.www.service;

import net.renfei.www.ApplicationTests;
import net.renfei.www.entity.dto.PostsListDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostsSetviceTests extends ApplicationTests {
    @Autowired
    private PostsService postsService;

    @Test
    @Rollback
    public void getAllPostsTest(){
        System.out.println("\t测试getAllPosts-------------");
        PostsListDTO postsListDTO =  postsService.getAllPosts("","");
        Assert.assertNotNull(postsListDTO);
        System.out.println("\t\t测试getAllPosts通过-----------");
    }
}
