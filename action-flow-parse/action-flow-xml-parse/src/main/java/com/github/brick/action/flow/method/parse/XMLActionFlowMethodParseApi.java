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

package com.github.brick.action.flow.method.parse;

import com.github.brick.action.flow.method.entity.*;
import com.github.brick.action.flow.method.parse.xml.*;
import com.github.brick.action.flow.parse.api.ActionFlowMethodParseApi;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLActionFlowMethodParseApi implements ActionFlowMethodParseApi {
    private final ResultParse resultParse = new ResultParse();
    private final ParamsParse paramsParse = new ParamsParse();
    private final ActionsParse actionsParse = new ActionsParse();
    private final WatchersParse watchersParse = new WatchersParse();
    private final ExtractsParse extractsParse = new ExtractsParse();
    private final FlowsParse flowsParse = new FlowsParse();

    @Override
    public AllEntity parse(String file) throws Exception {
        AllEntity allEntity = new AllEntity();

        String fileName = this.getClass().getClassLoader().getResource(file).getPath();//获取文件路径
        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);
        Element rootElement = document.getRootElement();

        Element params = rootElement.element("params");
        if (params != null) {
            ParamsEntity paramsEntity = paramsParse.parse(params);
            allEntity.setParams(paramsEntity);
        }

        Element result = rootElement.element("result");
        if (result != null) {
            ResultEntity resultEntity = resultParse.parse(result);
            allEntity.setResult(resultEntity);
        }

        Element actions = rootElement.element("actions");
        if (actions != null) {
            ActionsEntity actionsEntity = actionsParse.parse(actions);
            allEntity.setActions(actionsEntity);
        }

        Element watchers = rootElement.element("watchers");
        if (watchers != null) {
            WatchersEntity watchersEntity = watchersParse.parse(watchers);
            allEntity.setWatchers(watchersEntity);
        }

        Element extracts = rootElement.element("extracts");
        if (extracts != null) {
            ExtractsEntity extractsEntity = extractsParse.parse(extracts);
            allEntity.setExtracts(extractsEntity);
        }

        Element flow = rootElement.element("flows");
        if (flow != null) {
            FlowsEntity parse = flowsParse.parse(flow);
            allEntity.setFlows(parse);
        }


        return allEntity;


    }
}
