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

package com.github.brick.action.flow.method.parse.xml;

import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.method.entity.WatchersEntity;
import com.github.brick.action.flow.method.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class WatchersParse implements Parse<WatchersEntity> {
    WatcherParse watcherParse = new WatcherParse();
    WatcherThenParse watcherThenParse = new WatcherThenParse();
    WatcherCatchParse watcherCatchParse = new WatcherCatchParse();

    @Override
    public WatchersEntity parse(Element element) {
        WatchersEntity watchersEntity = new WatchersEntity();
        ArrayList<WatcherEntity> list = new ArrayList<>();

        List<Element> watcher = element.elements("watcher");
        for (Element element1 : watcher) {
            WatcherEntity parse = watcherParse.parse(element1);
            list.add(parse);
        }

        watchersEntity.setList(list);
        return watchersEntity;
    }


    public class WatcherParse implements Parse<WatcherEntity> {
        @Override
        public WatcherEntity parse(Element element) {
            String id = element.attributeValue("id");
            String exid = element.attributeValue("exid");
            String condition = element.attributeValue("condition");

            WatcherEntity watcherEntity = new WatcherEntity();
            watcherEntity.setId(id);
            watcherEntity.setExId(exid);
            watcherEntity.setCondition(condition);

            List<Element> then = element.elements("then");
            List<WatcherEntity.Then> thens = new ArrayList<>();


            if (!then.isEmpty()) {
                for (Element element1 : then) {
                    WatcherEntity.Then parse = watcherThenParse.parse(element1);
                    thens.add(parse);
                }
            }
            List<Element> catchs = element.elements("catch");
            List<WatcherEntity.Catch> caches = new ArrayList<>();
            if (!catchs.isEmpty()) {
                for (Element aCatch : catchs) {
                    WatcherEntity.Catch parse = watcherCatchParse.parse(aCatch);
                    caches.add(parse);
                }
            }
            watcherEntity.setCatchs(caches);
            watcherEntity.setThens(thens);

            return watcherEntity;
        }
    }


    public class WatcherThenParse implements Parse<WatcherEntity.Then> {
        @Override
        public WatcherEntity.Then parse(Element element) {
            String actionId = element.attributeValue("actionId");
            WatcherEntity.Then then = new WatcherEntity.Then();
            then.setActionId(actionId);
            return then;
        }
    }


    public class WatcherCatchParse implements Parse<WatcherEntity.Catch> {
        @Override
        public WatcherEntity.Catch parse(Element element) {
            String actionId = element.attributeValue("actionId");
            WatcherEntity.Catch aCatch = new WatcherEntity.Catch();
            aCatch.setActionId(actionId);

            return aCatch;
        }
    }
}
