/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.brick.action.flow.sample.web;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
@Api(value = "user", tags = {"User"}, description = "用户相关")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    Map<String, UserInfo> userInfoMap = new HashMap<>();

    @PostMapping("/login")
    public Map<String, String> login(
            @ApiParam(value = "username") String username,
            @ApiParam(value = "password") String password
    ) {
        if (logger.isInfoEnabled()) {
            logger.info("login,username = {}, password = {}", JSON.toJSONString(username), JSON.toJSONString(password));
        }


        Map<String, String> map = new HashMap<>(8);
        String value = UUID.randomUUID().toString();
        map.put("token", value);
        UserInfo value1 = new UserInfo();
        value1.setUsername(username);
        value1.setToken(value);

        userInfoMap.put(value, value1);
        return map;
    }

    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(value = "请求头token", name = "token", paramType = "header", required = true)

            }
    )
    @GetMapping("/user_info")
    public UserInfo userInfo(
            @RequestHeader("token") String token
    ) {

        if (logger.isInfoEnabled()) {
            logger.info("userInfo,token = {}", JSON.toJSONString(token));
        }

        return userInfoMap.get(token);
    }

    @Data
    public static class UserInfo {
        private String username;
        private String token;

    }
}
