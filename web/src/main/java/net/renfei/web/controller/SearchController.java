package net.renfei.web.controller;

import net.renfei.core.entity.SearchDTO;
import net.renfei.dao.entity.FullTextIndexDO;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.SearchVO;
import net.renfei.web.entity.SidebarVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {


    @RequestMapping("")
    public ModelAndView doSearch(ModelAndView mv,
                                 @RequestParam(value = "q", required = false) String query,
                                 @RequestParam(value = "p", required = false) String page) {
        if (!stringUtil.isEmpty(query)) {
            setHead(mv, "Search:" + query);
            SidebarVO sidebarVO = (SidebarVO) getObjFromMV(mv, SIDEBAR_KEY);
            mv.addObject(SIDEBAR_KEY, sidebarVO);
            Long startTime = System.nanoTime();
            SearchDTO searchDTO;
            if (stringUtil.isEmpty(page)) {
                searchDTO = searchService.search(query);
            } else {
                searchDTO = searchService.search(query, page);
            }
            Long endTime = System.nanoTime();
            if (searchDTO != null) {
                SearchVO searchVO = new SearchVO();
                searchVO.setSearchWord(query);
                searchVO.setResults(searchDTO.getTotal());
                searchVO.setSeconds((endTime - startTime) / 1000000000D);
                List<SearchVO.Search> searchs = new ArrayList<>();
                for (FullTextIndexDO fullTextIndexDO : searchDTO.getFullTextIndexDOS()
                ) {
                    SearchVO.Search search = new SearchVO.Search();
                    search.setTitle(fullTextIndexDO.getTitle());
                    search.setDescribe(stringUtil.getTextFromHtml(fullTextIndexDO.getContent()));
                    search.setLink(domain + fullTextIndexDO.getUriPath() + "/" + fullTextIndexDO.getId());
                    search.setDate(fullTextIndexDO.getReleaseTime());
                    searchs.add(search);
                }
                searchVO.setSearchList(searchs);
                setPagination(mv, page, searchDTO.getTotal(), "/search?q=" + query + "&p=");
                mv.addObject("search", searchVO);
            }
        }
        mv.setViewName("search/search");
        return mv;
    }
}
