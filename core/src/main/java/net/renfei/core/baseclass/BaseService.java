package net.renfei.core.baseclass;

import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import net.renfei.dao.persistences.PostsDOMapper;
import net.renfei.dao.persistences.VAllInfoMapper;
import net.renfei.dao.persistences.VideoDOMapper;
import net.renfei.dao.persistences.VideoSourceDOMapper;
import net.renfei.dao.persistences.VideoTrackDOMapper;
import net.renfei.dao.persistences.PhotoDOMapper;
import net.renfei.dao.persistences.PhotoImgDOMapper;
import net.renfei.dao.persistences.AccountDOMapper;
import net.renfei.dao.persistences.TokenDOMapper;
import net.renfei.dao.persistences.CategoryDOMapper;
import net.renfei.dao.persistences.CommentDOMapper;
import net.renfei.dao.persistences.IPV4DOMapper;
import net.renfei.dao.persistences.IPV6DOMapper;
import net.renfei.dao.persistences.PageDOMapper;
import net.renfei.dao.persistences.PermissionDOMapper;
import net.renfei.dao.persistences.PermissionRoleDOMapper;
import net.renfei.dao.persistences.RoleDOMapper;
import net.renfei.dao.persistences.RoleAccountDOMapper;
import net.renfei.dao.persistences.FullTextIndexMapper;
import net.renfei.dao.persistences.SecretKeyDOMapper;
import net.renfei.dao.persistences.SettingDOMapper;
import net.renfei.dao.persistences.TypeDOMapper;
import net.renfei.dao.persistences.LinkDOMapper;
import net.renfei.dao.persistences.SysMenuDOMapper;
import net.renfei.util.FileUtil;
import net.renfei.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 服务基类
 */
@Service
public class BaseService extends BaseClass {
    protected static int DEFAULT_PAGE = 1;
    protected static int DEFAULT_ROWS = 10;

    @Autowired
    protected MD5Util md5Util;
    @Autowired
    protected FileUtil fileUtil;
    @Autowired
    protected VAllInfoMapper vAllInfoMapper;
    @Autowired
    protected PostsDOMapper postsDOMapper;
    @Autowired
    protected VideoDOMapper videoDOMapper;
    @Autowired
    protected VideoSourceDOMapper videoSourceDOMapper;
    @Autowired
    protected VideoTrackDOMapper videoTrackDOMapper;
    @Autowired
    protected PhotoDOMapper photoDOMapper;
    @Autowired
    protected PhotoImgDOMapper photoImgDOMapper;
    @Autowired
    protected AccountDOMapper accountDOMapper;
    @Autowired
    protected TokenDOMapper tokenDOMapper;
    @Autowired
    protected CategoryDOMapper categoryDOMapper;
    @Autowired
    protected CommentDOMapper commentDOMapper;
    @Autowired
    protected IPV4DOMapper ipv4DOMapper;
    @Autowired
    protected IPV6DOMapper ipv6DOMapper;
    @Autowired
    protected PageDOMapper pageDOMapper;
    @Autowired
    protected PermissionDOMapper permissionDOMapper;
    @Autowired
    protected PermissionRoleDOMapper permissionRoleDOMapper;
    @Autowired
    protected RoleDOMapper roleDOMapper;
    @Autowired
    protected RoleAccountDOMapper roleAccountDOMapper;
    @Autowired
    protected FullTextIndexMapper fullTextIndexMapper;
    @Autowired
    protected SecretKeyDOMapper secretKeyDOMapper;
    @Autowired
    protected SettingDOMapper settingDOMapper;
    @Autowired
    protected TypeDOMapper typeDOMapper;
    @Autowired
    protected LinkDOMapper linkDOMapper;
    @Autowired
    protected SysMenuDOMapper sysMenuDOMapper;

    protected int convertPage(String page) {
        return stringUtil.convertInt(page, DEFAULT_PAGE);
    }

    protected int convertRows(String rows) {
        return stringUtil.convertInt(rows, DEFAULT_ROWS);
    }

    /**
     * 文章浏览量自增
     *
     * @param postsDOWithBLOBs
     */
    @Async
    protected void updateView(PostsDOWithBLOBs postsDOWithBLOBs) {
        postsDOWithBLOBs.setViews(postsDOWithBLOBs.getViews() + 1);
        postsDOMapper.updateByPrimaryKeySelective(postsDOWithBLOBs);

    }

    /**
     * 视频浏览量自增
     *
     * @param videoDOWithBLOBs
     */
    @Async
    protected void updateView(VideoDOWithBLOBs videoDOWithBLOBs) {
        videoDOWithBLOBs.setViews(videoDOWithBLOBs.getViews() + 1);
        videoDOMapper.updateByPrimaryKeySelective(videoDOWithBLOBs);
    }
}
