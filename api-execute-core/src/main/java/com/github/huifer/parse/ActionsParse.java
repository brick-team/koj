package com.github.huifer.parse;

import com.github.huifer.entity.ActionTag;
import com.github.huifer.entity.ActionsTag;
import com.github.huifer.entity.FormatTag;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class ActionsParse implements Parse<ActionsTag> {
    ActionParse actionParse = new ActionParse();
    ActionParamParse actionParamParse = new ActionParamParse();
    FormatParse formatParse = new FormatParse();

    @Override
    public ActionsTag parse(Element element) {
        ActionsTag actionsTag = new ActionsTag();
        ArrayList<ActionTag> list = new ArrayList<>();

        NodeList param = element.getChildNodes();
        for (int i = 0; i < param.getLength(); i++) {
            Node item = param.item(i);
            if (item instanceof Element) {
                ActionTag actionTag = actionParse.parse((Element) item);
                list.add(actionTag);
            }
        }

        actionsTag.setList(list);
        return actionsTag;
    }


    public class ActionParse implements Parse<ActionTag> {
        @Override
        public ActionTag parse(Element element) {
            ActionTag actionTag = new ActionTag();
            String id = element.getAttribute("id");
            actionTag.setId(id);
            String classStr = element.getAttribute("class");
            actionTag.setClazzStr(classStr);
            String methodStr = element.getAttribute("method");
            actionTag.setMethodStr(methodStr);

            NodeList childNodes = element.getChildNodes();
            ArrayList<ActionTag.Param> params = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item instanceof Element) {
                    if (item.getNodeName().equals("param")) {
                        ActionTag.Param parse = actionParamParse.parse((Element) item);
                        params.add(parse);
                    }
                }
                actionTag.setParams(params);
            }
            return actionTag;
        }
    }


    public class ActionParamParse implements Parse<ActionTag.Param> {
        @Override
        public ActionTag.Param parse(Element element) {
            String index = element.getAttribute("index");
            String arg_name = element.getAttribute("arg_name");
            String param_group = element.getAttribute("param_group");
            String ex = element.getAttribute("ex");
            String value = element.getAttribute("value");
            ActionTag.Param param = new ActionTag.Param();
            param.setIndex(Integer.parseInt(index));
            param.setArgName(arg_name);
            param.setValue(value);
            param.setParamGroup(param_group);
            param.setEx(ex);

            NodeList childNodes = element.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item instanceof Element) {
                    if (item.getNodeName().equals("format")) {

                        FormatTag formatTag = formatParse.parse((Element) item);
                        param.setFormatTag(formatTag);
                    }
                }
            }

            return param;
        }
    }


}
