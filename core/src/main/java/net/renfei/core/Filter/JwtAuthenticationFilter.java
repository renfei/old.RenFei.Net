package net.renfei.core.Filter;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.config.RenFeiConfig;
import net.renfei.core.entity.TokenSubject;
import net.renfei.core.service.AccountService;
import net.renfei.core.service.CustomUserDetailsService;
import net.renfei.core.service.JwtService;
import net.renfei.core.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWTAuthenticationFilter以从请求中获取JWT令牌，验证它，加载与令牌关联的用户，并将其传递给Spring Security
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private RequestUtil requestUtil;
    @Autowired
    private AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
                TokenSubject tokenSubject = jwtService.readToken(jwt, requestUtil.getClientDigest(request));
                if (tokenSubject == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                //检查Token的有效期
                if (!accountService.checkTokenExtension(jwt, requestUtil.getClientDigest(request))) {
                    filterChain.doFilter(request, response);
                    return;
                }
                //延长Token的有效期
                accountService.extensionToken(jwt, requestUtil.getClientDigest(request));
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenSubject.getAccount());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
