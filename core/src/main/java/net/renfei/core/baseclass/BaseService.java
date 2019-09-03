package net.renfei.core.baseclass;

import net.renfei.core.config.RenFeiConfig;
import net.renfei.core.service.GlobalService;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import net.renfei.dao.persistences.*;
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
