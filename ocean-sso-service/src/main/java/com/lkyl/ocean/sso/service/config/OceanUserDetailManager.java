package com.lkyl.ocean.sso.service.config;

import com.lkyl.oceanframework.common.utils.constant.CommonCode;
import com.lkyl.oceanframework.common.utils.constant.CommonResult;
import com.lkyl.oceanframework.security.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class OceanUserDetailManager implements UserDetailsManager {

    String USER_URL = "http://127.0.0.1:8001/user/login";

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        if(ObjectUtils.isEmpty(this.loadUserByUsername(username))){
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonResult result = restTemplate.getForEntity(USER_URL+"/" + username, CommonResult.class).getBody();
        if(ObjectUtils.isEmpty(result) || !CommonCode.SUCCESS.equals(result.getCode())){
            return null;
        }
        return (UserPrincipal)result.getData();
    }
}
