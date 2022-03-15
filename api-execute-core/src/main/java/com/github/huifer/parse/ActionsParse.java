package com.github.huifer.parse;

import com.github.huifer.entity.ActionTag;
import com.github.huifer.entity.ActionsTag;
import com.github.huifer.entity.FormatTag;
import org.dom4j.Element;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActionsParse implements Parse<ActionsTag> {
    ActionParse actionParse = new ActionParse();
    ActionParamParse actionParamParse = new ActionParamParse();
    FormatParse formatParse = new FormatParse();

    @Override
    public ActionsTag parse(Element element) throws Exception {
        ActionsTag actionsTag = new ActionsTag();
        ArrayList<ActionTag> list = new ArrayList<>();

        List<Element> action = element.elements("action");
        for (Element element1 : action) {
            ActionTag parse = actionParse.parse(element1);
            list.add(parse);
        }

        actionsTag.setList(list);
        return actionsTag;
    }


    public class ActionParse implements Parse<ActionTag> {
        @Override
        public ActionTag parse(Element element) throws Exception {
            ActionTag actionTag = new ActionTag();

            String id = element.attributeValue("id");
            String clazzStr = element.attributeValue("class");
            String methodStr = element.attributeValue("method");

            actionTag.setId(id);
            actionTag.setClazzStr(clazzStr);
            actionTag.setClazz(Class.forName(clazzStr));
            actionTag.setMethodStr(methodStr);



            List<Element> param = element.elements("param");
            ArrayList<ActionTag.Param> params = new ArrayList<>();

            if (!param.isEmpty()) {

                for (Element element1 : param) {
                    ActionTag.Param parse = actionParamParse.parse(element1);
                    params.add(parse);
                }
                actionTag.setParams(params);

                params.sort(Comparator.comparing(ActionTag.Param::getIndex));
                List<Class<?>> classes = new ArrayList<>();

                for (ActionTag.Param param1 : params) {
                    classes.add(param1.getTypeClass());
                }


                Method method = findMethod(actionTag.getClazz(), actionTag.getMethodStr(),
                        classes.toArray(new Class<?>[] {}));
                actionTag.setMethod(method);
            }


            return actionTag;
        }

        private Method findMethod(Class<?> clazz, String methodName, Class<?>... args)
                throws NoSuchMethodException {
            return clazz.getMethod(methodName, args);
        }
    }



    public class ActionParamParse implements Parse<ActionTag.Param> {
        @Override
        public ActionTag.Param parse(Element element) throws Exception {
            String index = element.attributeValue("index");
            String arg_name = element.attributeValue("arg_name");
            String param_group = element.attributeValue("param_group");
            String ex = element.attributeValue("ex");
            String value = element.attributeValue("value");
            String type = element.attributeValue("type");
            ActionTag.Param param = new ActionTag.Param();
            param.setIndex(Integer.parseInt(index));
            param.setArgName(arg_name);
            param.setValue(value);
            param.setParamGroup(param_group);
            param.setEx(ex);
            param.setType(type);
            param.setTypeClass(Class.forName(type));

            Element format = element.element("format");
            if (format != null) {
                FormatTag parse = formatParse.parse(format);
                param.setFormatTag(parse);
            }

            return param;
        }
    }



}
