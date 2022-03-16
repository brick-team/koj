package com.github.brick.parse.xml;

import com.github.brick.entity.ActionEntity;
import com.github.brick.entity.ActionsEntity;
import com.github.brick.entity.FormatEntity;
import com.github.brick.parse.Parse;
import org.dom4j.Element;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActionsParse implements Parse<ActionsEntity> {
    ActionParse actionParse = new ActionParse();
    ActionParamParse actionParamParse = new ActionParamParse();
    FormatParse formatParse = new FormatParse();

    @Override
    public ActionsEntity parse(Element element) throws Exception {
        ActionsEntity actionsEntity = new ActionsEntity();
        ArrayList<ActionEntity> list = new ArrayList<>();

        List<Element> action = element.elements("action");
        for (Element element1 : action) {
            ActionEntity parse = actionParse.parse(element1);
            list.add(parse);
        }

        actionsEntity.setList(list);
        return actionsEntity;
    }


    public class ActionParse implements Parse<ActionEntity> {
        @Override
        public ActionEntity parse(Element element) throws Exception {
            ActionEntity actionEntity = new ActionEntity();

            String id = element.attributeValue("id");
            String clazzStr = element.attributeValue("class");
            String methodStr = element.attributeValue("method");

            actionEntity.setId(id);
            actionEntity.setClazzStr(clazzStr);
            actionEntity.setClazz(Class.forName(clazzStr));
            actionEntity.setMethodStr(methodStr);



            List<Element> param = element.elements("param");
            ArrayList<ActionEntity.Param> params = new ArrayList<>();

            if (!param.isEmpty()) {

                for (Element element1 : param) {
                    ActionEntity.Param parse = actionParamParse.parse(element1);
                    params.add(parse);
                }
                actionEntity.setParams(params);

                params.sort(Comparator.comparing(ActionEntity.Param::getIndex));
                List<Class<?>> classes = new ArrayList<>();

                for (ActionEntity.Param param1 : params) {
                    classes.add(param1.getTypeClass());
                }


                Method method = findMethod(actionEntity.getClazz(), actionEntity.getMethodStr(),
                        classes.toArray(new Class<?>[] {}));
                actionEntity.setMethod(method);
            }


            return actionEntity;
        }

        private Method findMethod(Class<?> clazz, String methodName, Class<?>... args)
                throws NoSuchMethodException {
            return clazz.getMethod(methodName, args);
        }
    }



    public class ActionParamParse implements Parse<ActionEntity.Param> {
        @Override
        public ActionEntity.Param parse(Element element) throws Exception {
            String index = element.attributeValue("index");
            String arg_name = element.attributeValue("arg_name");
            String param_group = element.attributeValue("param_group");
            String ex = element.attributeValue("ex");
            String value = element.attributeValue("value");
            String type = element.attributeValue("type");
            ActionEntity.Param param = new ActionEntity.Param();
            param.setIndex(Integer.parseInt(index));
            param.setArgName(arg_name);
            param.setValue(value);
            param.setParamGroup(param_group);
            param.setEx(ex);
            param.setType(type);
            param.setTypeClass(Class.forName(type));

            Element format = element.element("format");
            if (format != null) {
                FormatEntity parse = formatParse.parse(format);
                param.setFormatEntity(parse);
            }

            return param;
        }
    }



}
