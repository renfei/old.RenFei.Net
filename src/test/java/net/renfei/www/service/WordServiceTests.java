package net.renfei.www.service;

import net.renfei.www.ApplicationTests;
import org.apdplat.word.segmentation.Word;
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
public class WordServiceTests extends ApplicationTests {
    @Autowired
    private WordService wordService;

    @Test
    @Rollback
    public void getWords() {
        System.out.println("\t测试Word分词服务-------------");
        List<String> words = wordService.getWords("测试一个分词服务器的例句");
        for (String word : words) {
            System.out.print(word + " / ");
        }
        System.out.println();
        System.out.println("\t\t测试Word分词服务通过-----------");
    }

}
