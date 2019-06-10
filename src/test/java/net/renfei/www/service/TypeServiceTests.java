package net.renfei.www.service;

import net.renfei.www.ApplicationTests;
import net.renfei.www.entity.dto.TypeDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TypeServiceTests extends ApplicationTests {
    @Autowired
    private TypeService typeService;

    @Test
    @Rollback
    public void getAllType() {
        System.out.println("\t测试typeService.getAllType-------------");
        List<TypeDTO> typeDTOS = typeService.getAllType();
        Assert.assertNotNull(typeDTOS);
        System.out.println("\t\t测试typeService.getAllType通过-----------");
    }

    @Test
    @Rollback
    public void getTypeByID(){
        System.out.println("\t测试typeService.getTypeByID-------------");
        TypeDTO typeDTO = typeService.getTypeByID(1L);
        Assert.assertNotNull(typeDTO);
        System.out.println("\t\t测试typeService.getTypeByID通过-----------");
    }
}
