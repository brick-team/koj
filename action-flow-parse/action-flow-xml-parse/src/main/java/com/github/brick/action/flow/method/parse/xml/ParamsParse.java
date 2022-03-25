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

import com.github.brick.action.flow.method.entity.ParamEntity;
import com.github.brick.action.flow.method.entity.ParamsEntity;
import com.github.brick.action.flow.method.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ParamsParse implements Parse<ParamsEntity> {
    ParamParse paramParse = new ParamParse();

    @Override
    public ParamsEntity parse(Element element) throws Exception {
        ParamsEntity paramsEntity = new ParamsEntity();
        ArrayList<ParamEntity> list = new ArrayList<>();


        List<Element> param = element.elements("param");
        for (Element element1 : param) {
            list.add(paramParse.parse(element1));
        }
        paramsEntity.setList(list);

        return paramsEntity;
    }


    public class ParamParse implements Parse<ParamEntity> {
        @Override
        public ParamEntity parse(Element element) throws Exception {
            String group = element.attributeValue("group");

            String key = element.elementText("key");
            String value = element.elementText("value");
            ParamEntity paramEntity = new ParamEntity();
            paramEntity.setGroup(group);
            paramEntity.setKey(key);
            paramEntity.setValue(value);


            return paramEntity;
        }
    }

}
