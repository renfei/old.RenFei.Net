package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.RoleDTO;
import net.renfei.dao.entity.RoleAccountDO;
import net.renfei.dao.entity.RoleAccountDOExample;
import net.renfei.dao.entity.RoleDOExample;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends BaseService {

    public List<RoleDTO> findRoleByAccountID(Long accountID) {
        RoleDOExample roleDOExample = new RoleDOExample();
        RoleAccountDOExample roleAccountDOExample = new RoleAccountDOExample();
        roleAccountDOExample.createCriteria()
                .andAccountIdEqualTo(accountID);
        List<RoleAccountDO> roleAccountDOS = roleAccountDOMapper.selectByExample(roleAccountDOExample);
        if (roleAccountDOS != null && roleAccountDOS.size() > 0) {
            List<Long> ids = new ArrayList<>();
            for (RoleAccountDO roleAccountDO : roleAccountDOS
            ) {
                if (!ids.contains(roleAccountDO.getRoleId())) {
                    ids.add(roleAccountDO.getRoleId());
                }
            }
            roleDOExample.createCriteria()
                    .andIdIn(ids);
            return ejbGenerator.convert(roleDOMapper.selectByExample(roleDOExample), RoleDTO.class);
        } else {
            return null;
        }
    }
}
