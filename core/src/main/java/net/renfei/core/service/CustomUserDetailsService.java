package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PermissionDTO;
import net.renfei.core.entity.UserDTO;
import net.renfei.core.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 它根据用户名加载用户的数据
 */
@Service
public class CustomUserDetailsService extends BaseService implements UserDetailsService {

    @Autowired
    AccountService accountService;
    @Autowired
    PermissionService permissionService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String accountName)
            throws UsernameNotFoundException {
        UserDTO user = accountService.findByAccountname(accountName);
        if (user != null) {
            List<PermissionDTO> permissions = permissionService.findByAccountId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (PermissionDTO permission : permissions) {
                if (permission != null) {
                    //加载用户携带的权限名称
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //[1]：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("accountName: " + accountName + " do not exist!");
        }
    }
}
