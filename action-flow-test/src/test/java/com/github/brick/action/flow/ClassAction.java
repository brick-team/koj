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

package com.github.brick.action.flow;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassAction {
    private static final Logger logger = LoggerFactory.getLogger(ClassAction.class);

    public void hh(Mc mc) {

        if (logger.isInfoEnabled()) {
            logger.info("hh,mc = {}", JSON.toJSONString(mc));
        }


    }

    @Data
    public static class Mc {
        private String name;
        private String pwd;
    }
}
