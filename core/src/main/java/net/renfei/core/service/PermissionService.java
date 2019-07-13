package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PermissionDTO;
import net.renfei.core.entity.RoleDTO;
import net.renfei.dao.entity.PermissionDOExample;
import net.renfei.dao.entity.PermissionRoleDO;
import net.renfei.dao.entity.PermissionRoleDOExample;
import net.renfei.dao.persistences.PermissionDOMapper;
import net.renfei.dao.persistences.PermissionRoleDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService extends BaseService {
    @Autowired
    private PermissionDOMapper permissionDOMapper;
    @Autowired
    private PermissionRoleDOMapper permissionRoleDOMapper;
    @Autowired
    private RoleService roleService;

    public List<PermissionDTO> findByAccountId(Long accountId) {
        List<RoleDTO> roleDTOS = roleService.findRoleByAccountID(accountId);
        if (roleDTOS != null && roleDTOS.size() > 0) {
            //拿到角色列表，去查权限列表
            List<Long> ids = new ArrayList<>();
            for (RoleDTO roleDTO : roleDTOS
            ) {
                if (!ids.contains(roleDTO.getId())) {
                    ids.add(roleDTO.getId());
                }
            }
            PermissionRoleDOExample permissionRoleDOExample = new PermissionRoleDOExample();
            permissionRoleDOExample.createCriteria()
                    .andRoleIdIn(ids);
            List<PermissionRoleDO> permissionRoleDOS = permissionRoleDOMapper.selectByExample(permissionRoleDOExample);
            if (permissionRoleDOS != null && permissionRoleDOS.size() > 0) {
                //拿到权限列表，查询权限的详细信息
                ids = new ArrayList<>();
                for (PermissionRoleDO permissionRoleDO : permissionRoleDOS
                ) {
                    if (!ids.contains(permissionRoleDO.getPermissionId())) {
                        ids.add(permissionRoleDO.getPermissionId());
                    }
                }
                PermissionDOExample permissionDOExample = new PermissionDOExample();
                permissionDOExample.createCriteria()
                        .andIdIn(ids);
                return ejbGenerator.convert(permissionDOMapper.selectByExampleWithBLOBs(permissionDOExample), PermissionDTO.class);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<PermissionDTO> findAll() {
        PermissionDOExample permissionDOExample = new PermissionDOExample();
        permissionDOExample.createCriteria();
        return ejbGenerator.convert(permissionDOMapper.selectByExampleWithBLOBs(permissionDOExample), PermissionDTO.class);
    }
}
