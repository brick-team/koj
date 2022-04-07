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

import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLParseCommonTest {
    public static final String FILE_PATH = "flow.xml";
    private static final Logger logger = LoggerFactory.getLogger(XMLParseCommonTest.class);
    Element rootElement;
    Gson gson = new Gson();

    @Before
    public void init() throws Exception {
        String fileName = this.getClass().getClassLoader().getResource(FILE_PATH).getPath();//获取文件路径
        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);
        rootElement = document.getRootElement();
    }

    public void log(Object o) {
        logger.info("解析结果 = [{}]", gson.toJson(o));
    }
}
