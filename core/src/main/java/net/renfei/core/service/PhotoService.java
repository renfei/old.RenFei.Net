package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PhotoDTO;
import net.renfei.core.entity.PhotoImgDTO;
import net.renfei.core.entity.PhotoListDTO;
import net.renfei.dao.entity.PhotoDOExample;
import net.renfei.dao.entity.PhotoDOWithBLOBs;
import net.renfei.dao.entity.PhotoImgDO;
import net.renfei.dao.entity.PhotoImgDOExample;
import net.renfei.util.ListUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "PhotoService")
public class PhotoService extends BaseService {
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1", condition = "#p0!=null&&#p1!=null")
    public PhotoListDTO getAllPhotos(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PhotoDOExample photoDOExample = new PhotoDOExample();
        photoDOExample.setOrderByClause("release_time DESC");
        photoDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(intPage, intRows);
        List<PhotoDOWithBLOBs> photoDOWithBLOBs = photoDOMapper.selectByExampleWithBLOBs(photoDOExample);

        return convert(photoDOWithBLOBs, page.getTotal());
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public PhotoListDTO getAllPhotosCatID(Long catID, String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PhotoDOExample photoDOExample = new PhotoDOExample();
        photoDOExample.setOrderByClause("release_time DESC");
        photoDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date())
                .andCategoryIdEqualTo(catID);
        Page page = PageHelper.startPage(intPage, intRows);
        List<PhotoDOWithBLOBs> photoDOWithBLOBs = photoDOMapper.selectByExampleWithBLOBs(photoDOExample);

        return convert(photoDOWithBLOBs, page.getTotal());
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public PhotoDTO getPhotoById(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                PhotoDOExample photoDOExample = new PhotoDOExample();
                photoDOExample.createCriteria()
                        .andIdEqualTo(ID)
                        .andReleaseTimeLessThanOrEqualTo(new Date())
                        .andIsDeleteEqualTo(false);
                List<PhotoDOWithBLOBs> photoDOWithBLOBs = photoDOMapper.selectByExampleWithBLOBs(photoDOExample);
                if (photoDOWithBLOBs != null && photoDOWithBLOBs.size() > 0) {
                    PhotoDOWithBLOBs photoDOWithBLOBs1 = photoDOWithBLOBs.get(0);
                    return ejbGenerator.convert(photoDOWithBLOBs1, PhotoDTO.class);
                } else {
                    return null;
                }
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public PhotoImgDTO getPhotoImgByPhotoId(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                PhotoDOExample photoDOExample = new PhotoDOExample();
                photoDOExample.createCriteria()
                        .andIdEqualTo(ID)
                        .andReleaseTimeLessThanOrEqualTo(new Date())
                        .andIsDeleteEqualTo(false);
                List<PhotoDOWithBLOBs> photoDOWithBLOBs = photoDOMapper.selectByExampleWithBLOBs(photoDOExample);
                if (photoDOWithBLOBs != null && photoDOWithBLOBs.size() > 0) {
                    PhotoImgDOExample photoImgDOExample = new PhotoImgDOExample();
                    photoImgDOExample.createCriteria()
                            .andPhotoIdEqualTo(ID);
                    List<PhotoImgDO> photoImgDOS = photoImgDOMapper.selectByExampleWithBLOBs(photoImgDOExample);
                    PhotoImgDTO photoImgDTO = new PhotoImgDTO();
                    photoImgDTO.setPhotoImgs(photoImgDOS);
                    return photoImgDTO;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public PhotoImgDO getPhotoImgById(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
            } catch (Exception e) {
                return null;
            }
            PhotoImgDOExample photoImgDOExample = new PhotoImgDOExample();
            photoImgDOExample.createCriteria()
                    .andIdEqualTo(ID);
            return ListUtil.getOne(photoImgDOMapper.selectByExampleWithBLOBs(photoImgDOExample));
        } else {
            return null;
        }
    }

    public Long getCountByCategoryId(Long catID) {
        PhotoDOExample photoDOExample = new PhotoDOExample();
        photoDOExample.createCriteria()
                .andCategoryIdEqualTo(catID)
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(1, 1);
        photoDOMapper.selectByExampleWithBLOBs(photoDOExample);
        return page.getTotal();
    }

    private PhotoListDTO convert(List<PhotoDOWithBLOBs> photoDOWithBLOBs, Long count) {
        PhotoListDTO photoListDTO = new PhotoListDTO();
        photoListDTO.setPhotoDOWithBLOBs(photoDOWithBLOBs);
        photoListDTO.setCount(count);
        return photoListDTO;
    }
}
