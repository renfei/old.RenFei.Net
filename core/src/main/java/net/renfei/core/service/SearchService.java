package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.SearchDTO;
import net.renfei.dao.entity.FullTextIndexDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "SearchService")
public class SearchService extends BaseService {
    @Autowired
    private WordService wordService;

    public SearchDTO search(String word) {
        return search(word, "1");
    }

    public SearchDTO search(String word, String pages) {
        return search(word, pages, "10");
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public SearchDTO search(String word, String pages, String rows) {
        if (stringUtil.isEmpty(word)) {
            return null;
        }
        int intPage = convertPage(pages), intRows = convertRows(rows);
        //需要先分词
        List<String> words = wordService.getWords(word);
        StringBuffer sb = new StringBuffer();
        for (String str : words
        ) {
            sb.append(str + " ");
        }
        String wd = sb.toString();
        if (sb.length() > 0) {
            wd = wd.substring(0, wd.length() - 1);
        }
        Page page = PageHelper.startPage(intPage, intRows);
        List<FullTextIndexDO> fullTextIndexDOS = fullTextIndexMapper.selectByWord(wd);
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTotal(page.getTotal());
        searchDTO.setFullTextIndexDOS(fullTextIndexDOS);
        return searchDTO;
    }
}
