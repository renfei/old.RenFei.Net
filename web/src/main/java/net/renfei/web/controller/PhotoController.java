package net.renfei.web.controller;

import net.renfei.core.entity.PhotoDTO;
import net.renfei.core.entity.PhotoImgDTO;
import net.renfei.core.entity.PhotoListDTO;
import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController {

    @RequestMapping("")
    public ModelAndView getAllPhotoList(@RequestParam(value = "page", required = false) String page,
                                        ModelAndView mv) {
        setHead(mv, "Photo", "The RenFei Web Albums");
        PhotoListDTO photoListDTO = photoService.getAllPhotos(page, "10");
        mv.addObject("photoList", photoListDTO.getPhotoDOWithBLOBs());
        setPagination(mv, page, photoListDTO.getCount(), "/photo?page=");
        setSidebarByPhoto(mv, null);
        mv.setViewName("photo/list");
        return mv;
    }

    @RequestMapping("{id}")
    public ModelAndView getPhotoById(@PathVariable("id") String id, ModelAndView mv) throws NoHandlerFoundException {
        PhotoDTO photoDTO = photoService.getPhotoById(id);
        PhotoImgDTO photoImgDTO = photoService.getPhotoImgByPhotoId(id);
        if (photoImgDTO != null) {
            mv.addObject("photo", photoDTO);
            mv.addObject("photoImg", photoImgDTO);
            setHead(mv, photoDTO.getTitle() + " - Photo", "The RenFei Web Albums");
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("photo/show");
        return mv;
    }
}
