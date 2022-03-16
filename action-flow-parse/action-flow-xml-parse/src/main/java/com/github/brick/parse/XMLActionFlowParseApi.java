package com.github.brick.parse;

import com.github.brick.entity.*;
import com.github.brick.parse.api.ActionFlowParseApi;
import com.github.brick.parse.xml.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLActionFlowParseApi implements ActionFlowParseApi {
    private final ResultParse resultParse = new ResultParse();
    private final ParamsParse paramsParse = new ParamsParse();
    private final ActionsParse actionsParse = new ActionsParse();
    private final WatchersParse watchersParse = new WatchersParse();
    private final ExtractsParse extractsParse = new ExtractsParse();
    private final FlowsParse flowsParse = new FlowsParse();

    @Override
    public AllEntity parse(String file) throws Exception {
        String fileName = this.getClass().getClassLoader().getResource(file).getPath();//获取文件路径

        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);

        Element rootElement = document.getRootElement();

        Element params = rootElement.element("params");
        ParamsEntity paramsEntity = paramsParse.parse(params);

        Element result = rootElement.element("result");
        ResultEntity resultEntity = resultParse.parse(result);

        Element actions = rootElement.element("actions");
        ActionsEntity actionsEntity = actionsParse.parse(actions);

        Element watchers = rootElement.element("watchers");
        WatchersEntity watchersEntity = watchersParse.parse(watchers);

        Element extracts = rootElement.element("extracts");
        ExtractsEntity extractsEntity = extractsParse.parse(extracts);

        Element flow = rootElement.element("flows");
        FlowsEntity parse = flowsParse.parse(flow);

        AllEntity allEntity = new AllEntity();
        allEntity.setParams(paramsEntity);
        allEntity.setActions(actionsEntity);
        allEntity.setWatchers(watchersEntity);
        allEntity.setResult(resultEntity);
        allEntity.setExtracts(extractsEntity);
        allEntity.setFlows(parse);

        return allEntity;

    }
}
