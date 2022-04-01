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

package com.github.brick.action.flow.web.common.ctr;

import org.springframework.web.multipart.MultipartFile;

/**
 * swagger 文件导入
 */
public interface SwaggerCtr {
    /**
     * 处理swagger数据
     *
     * @param swaggerData swagger文件内容
     * @return true 成功 ，false 失败
     */
    boolean handlerWithData(String swaggerData);

    /**
     * 处理上传的swagger文件
     *
     * @param file swagger文件
     * @return true 成功 ，false 失败
     */
    boolean handlerWithFile(MultipartFile file);

    /**
     * 处理swagger地址
     *
     * @param url swagger访问地址
     * @return true 成功 ，false 失败
     */
    boolean handlerWithUrl(String url);

}
