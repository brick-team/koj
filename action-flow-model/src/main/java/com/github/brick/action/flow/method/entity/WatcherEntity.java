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

package com.github.brick.action.flow.method.entity;

import lombok.Data;

import java.util.List;

@Data
public class WatcherEntity {
    private String id;
    private String exId;
    private String condition;
    private List<Then> thens;
    private List<Catch> catchs;
    private List<Error> errors;


    @Data
    public static class Then {
        private String actionId;
        private String apiId;
    }


    @Data
    public static class Catch {
        private String actionId;
        private String apiId;
    }


    @Data
    public static class Error {
        private String actionId;
        private String apiId;
    }
}
