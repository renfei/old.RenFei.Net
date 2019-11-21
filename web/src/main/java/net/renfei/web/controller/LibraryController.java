package net.renfei.web.controller;

import com.alibaba.fastjson.JSON;
import net.renfei.dao.entity.LibraryDO;
import net.renfei.dao.entity.LibraryDetailsDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.APIResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/library")
public class LibraryController extends BaseController {

    @RequestMapping("")
    public ModelAndView getLibrary(ModelAndView mv) {
        setHead(mv, "资源文件收藏库 - Library", "资源文件收藏库，收藏了常用IT技术资料和文件下载链接。");
        mv.setViewName("library/index");
        return mv;
    }

    @RequestMapping("getLibDirJson")
    @ResponseBody
    public String getLibDirJson() {
        return JSON.toJSONString(libraryService.getLibraryDirectoryJson())
                .replace("menuText", "text")
                .replace("child", "nodes");
    }

    @RequestMapping("getLibraryDetails")
    @ResponseBody
    public APIResult getLibraryDetails(String library) {
        LibraryDO libraryDO = libraryService.getLibraryByName(library);
        if (libraryDO == null) {
            return APIResult.fillResult(false);
        }
        List<LibraryDetailsDOWithBLOBs> libraryDetailsDOWithBLOBs = libraryService.getLibraryDetails(libraryDO.getId());
        return APIResult.fillResult(true, "", libraryDetailsDOWithBLOBs);
    }
}
