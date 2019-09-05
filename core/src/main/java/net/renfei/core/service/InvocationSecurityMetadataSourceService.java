package net.renfei.core.service;

import net.renfei.core.entity.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class InvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {
    @Autowired
    private PermissionService permissionService;
    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<PermissionDTO> permissions = permissionService.findAll();
        for (PermissionDTO permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl()-getMethod() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl() + "-" + permission.getMethod(), array);
        }

    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl, method;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            String itern = iter.next();
            String[] arr = itern.split("-");
            resUrl = arr[0];
            method = arr[1];
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request) && request.getMethod().toUpperCase().equals(method.toUpperCase())) {
                //请求的地址和请求的方法都符合，返回权限名称集合
                return map.get(itern);
            }
        }
        //返回一个默认的，这里不能返回 null
        Collection<ConfigAttribute> array = new LinkedList<>();
        ConfigAttribute configAttribute = new SecurityConfig("ROLE_NO_USER");
        array.add(configAttribute);
        return array;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
