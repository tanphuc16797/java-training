package com.amit.springsercurity.security;

import com.amit.springsercurity.domain.TokenStore;
import com.amit.springsercurity.redis.entity.TokenDTO;
import com.amit.springsercurity.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager , TokenStore tokenStore) {
        super(authenticationManager);
        this.tokenStore = tokenStore;
    }
    private TokenStore tokenStore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
       String token = request.getHeader(Constant.HEADER.HEADER_TOKEN);

       if (StringUtils.isBlank(token)){
           chain.doFilter(request, response);
           return;
       }

        token = token.replaceFirst("(?i)" + Constant.HEADER.TOKEN_PREFIX, "");

        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Optional<TokenDTO> optionalTokenDTO = tokenStore.getSessionFromToken(token);

            if (optionalTokenDTO.isEmpty()){
                chain.doFilter(request, response);
                return;
            }

            TokenDTO tokenDTO = optionalTokenDTO.get();

            AmitUserDetail userDetail = new AmitUserDetail(tokenDTO.getUserId() , tokenDTO.getUserName());

            AmitUserAuthentication amitUserAuthentication = new AmitUserAuthentication(userDetail , null , new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(amitUserAuthentication);
        } catch (Exception e){

        }finally {
            chain.doFilter(request, response);
            return;
        }
    }

}
