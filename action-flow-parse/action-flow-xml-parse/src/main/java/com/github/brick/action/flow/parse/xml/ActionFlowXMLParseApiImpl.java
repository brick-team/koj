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

package com.github.brick.action.flow.parse.xml;

import com.github.brick.action.flow.model.xml.ActionFlowXML;
import com.github.brick.action.flow.parse.api.ActionFlowXMLParseApi;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ActionFlowXMLParseApiImpl implements ActionFlowXMLParseApi {
    ActionFlowXMLParse actionFlowXMLParse = new ActionFlowXMLParse();

    @Override
    public ActionFlowXML parse(String file) throws Exception {
        String fileName = this.getClass().getClassLoader().getResource(file).getPath();//获取文件路径
        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);
        Element rootElement = document.getRootElement();

        return actionFlowXMLParse.parse(rootElement);
    }
}
