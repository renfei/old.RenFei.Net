package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.entity.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService extends BaseService {

    public List<TagDOExtend> getAllTagDOExtend() {
        return tagDOMapper.getAllTag();
    }

    /**
     * 获取全部标签
     *
     * @return
     */
    public List<TagDO> getAllTag() {
        TagDOExample tagDOExample = new TagDOExample();
        tagDOExample.createCriteria();
        return tagDOMapper.selectByExampleWithBLOBs(tagDOExample);
    }

    /**
     * 根据目标获取全部标签
     *
     * @return
     */
    public List<TagDO> getTagByTargetId(long targetId) {
        List<TagRelationDO> tagRelationDOS = getTagRelationByTargetId(targetId);
        List<Long> longs = new ArrayList<>();
        for (TagRelationDO tagre : tagRelationDOS
        ) {
            longs.add(tagre.getTagId());
        }
        TagDOExample tagDOExample = new TagDOExample();
        tagDOExample.createCriteria()
                .andIdIn(longs);
        return tagDOMapper.selectByExampleWithBLOBs(tagDOExample);
    }

    /**
     * 添加一个标签
     *
     * @param enName
     * @param zhName
     * @param describe
     * @return
     */
    public int addTag(String enName, String zhName, String describe) {
        TagDO tagDO = new TagDO();
        tagDO.setEnName(enName);
        tagDO.setZhName(zhName);
        tagDO.setDescribe(describe);
        return tagDOMapper.insertSelective(tagDO);
    }
    public List<TagRelationDO> getTagRelation(long typeId, long targetId) {
        TagRelationDOExample tagRelationDOExample = new TagRelationDOExample();
        tagRelationDOExample.createCriteria()
                .andTypeIdEqualTo(typeId)
                .andTargetIdEqualTo(targetId);
        return tagRelationDOMapper.selectByExample(tagRelationDOExample);
    }

    public List<TagRelationDO> getTagRelationByEnName(String enName, Long typeId) {
        TagDOExample tagDOExample = new TagDOExample();
        tagDOExample.createCriteria().andEnNameEqualTo(enName);
        List<TagDO> tagDOS = tagDOMapper.selectByExampleWithBLOBs(tagDOExample);
        if (tagDOS != null && tagDOS.size() > 0) {
            TagRelationDOExample tagRelationDOExample = new TagRelationDOExample();
            tagRelationDOExample.createCriteria()
                    .andTagIdEqualTo(tagDOS.get(0).getId())
                    .andTypeIdEqualTo(typeId);
            return tagRelationDOMapper.selectByExample(tagRelationDOExample);
        } else {
            return null;
        }
    }

    public List<TagRelationDO> getTagRelationByTargetId(Long targetId) {
        TagRelationDOExample tagRelationDOExample = new TagRelationDOExample();
        tagRelationDOExample.createCriteria().andTargetIdEqualTo(targetId);
        return tagRelationDOMapper.selectByExample(tagRelationDOExample);
    }

    public List<TagRelationDO> getTagRelationByTagId(Long tagId) {
        TagRelationDOExample tagRelationDOExample = new TagRelationDOExample();
        tagRelationDOExample.createCriteria().andTagIdEqualTo(tagId);
        return tagRelationDOMapper.selectByExample(tagRelationDOExample);
    }

    public List<TagRelationDO> getTagRelationByTagIds(List<Long> tagIds) {
        if (tagIds != null && tagIds.size() > 0) {
            TagRelationDOExample tagRelationDOExample = new TagRelationDOExample();
            tagRelationDOExample.createCriteria().andTagIdIn(tagIds);
            return tagRelationDOMapper.selectByExample(tagRelationDOExample);
        } else {
            return null;
        }
    }

    public List<TagDO> getTagByEnName(String enName) {
        TagDOExample tagDOExample = new TagDOExample();
        tagDOExample.createCriteria().andEnNameEqualTo(enName);
        return tagDOMapper.selectByExampleWithBLOBs(tagDOExample);
    }

    /**
     * 新增一个标签和对象的关系
     *
     * @param tagId
     * @param typeId
     * @param targetId
     * @return
     */
    public int addTagRelation(long tagId, long typeId, long targetId) {
        TagRelationDO tagRelationDO = new TagRelationDO();
        tagRelationDO.setTagId(tagId);
        tagRelationDO.setTargetId(targetId);
        tagRelationDO.setTypeId(typeId);
        return tagRelationDOMapper.insertSelective(tagRelationDO);
    }

    public int deleteTagRelationByTargetId(long targetId) {
        TagRelationDOExample tagRelationDOExample = new TagRelationDOExample();
        tagRelationDOExample.createCriteria().andTargetIdEqualTo(targetId);
        return tagRelationDOMapper.deleteByExample(tagRelationDOExample);
    }
}
