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

import com.github.brick.action.flow.method.entity.ResultEntity;
import com.github.brick.action.flow.method.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ResultParse implements Parse<ResultEntity> {

    ResultKeyParse resultKeyParse = new ResultKeyParse();

    @Override
    public ResultEntity parse(Element element) {
        ResultEntity resultEntity = new ResultEntity();

        ArrayList<ResultEntity.Key> keys = new ArrayList<>();

        List<Element> key = element.elements("key");
        for (Element keyElement : key) {
            ResultEntity.Key parse = resultKeyParse.parse(keyElement);
            keys.add(parse);
        }


        resultEntity.setKeys(keys);

        return resultEntity;
    }


    public class ResultKeyParse implements Parse<ResultEntity.Key> {
        @Override
        public ResultEntity.Key parse(Element element) {
            String name = element.attributeValue("name");
            String exid = element.attributeValue("exid");
            ResultEntity.Key key = new ResultEntity.Key();
            key.setName(name);
            key.setExId(exid);
            return key;
        }
    }

}
