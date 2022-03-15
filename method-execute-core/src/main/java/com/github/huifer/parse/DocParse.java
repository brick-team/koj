package com.github.huifer.parse;

import com.github.huifer.entity.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DocParse {
    private final ResultParse resultParse = new ResultParse();
    private final ParamsParse paramsParse = new ParamsParse();
    private final ActionsParse actionsParse = new ActionsParse();
    private final WatchersParse watchersParse = new WatchersParse();
    private final ExtractsParse extractsParse = new ExtractsParse();
    private final FlowsParse flowsParse = new FlowsParse();

    public DocTag parse(String file) throws Exception {
        String fileName = this.getClass().getClassLoader().getResource(file).getPath();//获取文件路径

        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);

        Element rootElement = document.getRootElement();

        Element params = rootElement.element("params");
        ParamsTag paramsTag = paramsParse.parse(params);

        Element result = rootElement.element("result");
        ResultTag resultTag = resultParse.parse(result);

        Element actions = rootElement.element("actions");
        ActionsTag actionsTag = actionsParse.parse(actions);

        Element watchers = rootElement.element("watchers");
        WatchersTag watchersTag = watchersParse.parse(watchers);

        Element extracts = rootElement.element("extracts");
        ExtractsTag extractsTag = extractsParse.parse(extracts);

        Element flow = rootElement.element("flows");
        FlowsTag parse = flowsParse.parse(flow);

        DocTag docTag = new DocTag();
        docTag.setParams(paramsTag);
        docTag.setActions(actionsTag);
        docTag.setWatchers(watchersTag);
        docTag.setResult(resultTag);
        docTag.setExtracts(extractsTag);
        docTag.setFlows(parse);

        return docTag;

    }
}
