package net.renfei.www.service;

import net.renfei.www.ApplicationTests;
import net.renfei.www.entity.dto.PostsDTO;
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
public class PostsServiceTests extends ApplicationTests {
    @Autowired
    private PostsService postsService;

    @Test
    @Rollback
    public void getAllPostsTest(){
        System.out.println("\t测试getAllPosts-------------");
        PostsListDTO postsListDTO =  postsService.getAllPosts("","");
        Assert.assertNotNull(postsListDTO);
        postsListDTO =  postsService.getAllPosts(null,null);
        Assert.assertNotNull(postsListDTO);
        postsListDTO =  postsService.getAllPosts("-1","-1");
        Assert.assertNotNull(postsListDTO);
        postsListDTO =  postsService.getAllPosts("0","0");
        Assert.assertNotNull(postsListDTO);
        System.out.println("\t\t测试getAllPosts通过-----------");
    }

    @Test
    @Rollback
    public void getPostsByIDTest(){
        System.out.println("\t测试getPostsByID-------------");
        PostsDTO postsDTO = postsService.getPostsByID("");
        Assert.assertNull(postsDTO);
        postsDTO = postsService.getPostsByID("asd");
        Assert.assertNull(postsDTO);
        postsDTO = postsService.getPostsByID("-1");
        Assert.assertNull(postsDTO);
        postsDTO = postsService.getPostsByID(null);
        Assert.assertNull(postsDTO);
        postsDTO = postsService.getPostsByID("1");
        Assert.assertNotNull(postsDTO);
        System.out.println("\t\t测试getPostsByID通过-----------");
    }
}
