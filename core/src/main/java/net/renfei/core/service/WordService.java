package net.renfei.core.service;

// 分词器

import net.renfei.core.baseclass.BaseService;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordService extends BaseService {
    public List<String> getWords(String word) {
        if (!stringUtil.isEmpty(word)) {
            List<Word> words = WordSegmenter.seg(word);
            List<String> wordList = new ArrayList<>();
            if (words != null && words.size() > 0) {
                for (Word wordItem : words) {
                    wordList.add(wordItem.getText());
                }
            }
            return wordList;
        } else {
            return null;
        }
    }
}
