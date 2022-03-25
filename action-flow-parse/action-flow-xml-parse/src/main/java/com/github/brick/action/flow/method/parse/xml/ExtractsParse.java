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

import com.github.brick.action.flow.method.entity.ExtractEntity;
import com.github.brick.action.flow.method.entity.ExtractsEntity;
import com.github.brick.action.flow.method.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtractsParse implements Parse<ExtractsEntity> {
    ExtractParse extractParse = new ExtractParse();

    @Override
    public ExtractsEntity parse(Element element) {
        ExtractsEntity extractsEntity = new ExtractsEntity();
        ArrayList<ExtractEntity> extractEntities = new ArrayList<>();

        List<Element> extract = element.elements("extract");
        for (Element element1 : extract) {
            ExtractEntity parse = extractParse.parse(element1);
            extractEntities.add(parse);
        }
        extractsEntity.setExtractEntities(extractEntities);
        return extractsEntity;
    }


    public class ExtractParse implements Parse<ExtractEntity> {
        @Override
        public ExtractEntity parse(Element element) {
            ExtractEntity extractEntity = new ExtractEntity();
            extractEntity.setId(element.attributeValue("id"));
            extractEntity.setFromAction(element.attributeValue("fromAction"));
            extractEntity.setEl(element.attributeValue("el"));
            return extractEntity;
        }
    }

}
