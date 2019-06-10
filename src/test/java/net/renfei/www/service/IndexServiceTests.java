package net.renfei.www.service;

import net.renfei.www.ApplicationTests;
import net.renfei.www.entity.dto.AllInfoDTOList;
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
public class IndexServiceTests extends ApplicationTests {
    @Autowired
    private IndexService indexService;

    @Rollback
    @Test
    public void getAllInfoTest() {
        System.out.println("\t测试indexService.getAllInfo-------------");
        AllInfoDTOList postsListDTO =  indexService.getAllInfo("","");
        Assert.assertNotNull(postsListDTO.getVAllInfoWithBLOBsList());
        postsListDTO =  indexService.getAllInfo(null,null);
        Assert.assertNotNull(postsListDTO.getVAllInfoWithBLOBsList());
        postsListDTO =  indexService.getAllInfo("-1","-1");
        Assert.assertNotNull(postsListDTO.getVAllInfoWithBLOBsList());
        postsListDTO =  indexService.getAllInfo("0","0");
        Assert.assertNotNull(postsListDTO.getVAllInfoWithBLOBsList());
        System.out.println("\t\t测试indexService.getAllInfo通过-----------");
    }
}
