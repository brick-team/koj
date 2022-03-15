package com.github.huifer.parse;

import com.github.huifer.entity.WatcherTag;
import com.github.huifer.entity.WatchersTag;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class WatchersParse implements Parse<WatchersTag> {
    WatcherParse watcherParse = new WatcherParse();
    WatcherThenParse watcherThenParse = new WatcherThenParse();
    WatcherCatchParse watcherCatchParse = new WatcherCatchParse();

    @Override
    public WatchersTag parse(Element element) {
        WatchersTag watchersTag = new WatchersTag();
        ArrayList<WatcherTag> list = new ArrayList<>();

        List<Element> watcher = element.elements("watcher");
        for (Element element1 : watcher) {
            WatcherTag parse = watcherParse.parse(element1);
            list.add(parse);
        }

        watchersTag.setList(list);
        return watchersTag;
    }


    public class WatcherParse implements Parse<WatcherTag> {
        @Override
        public WatcherTag parse(Element element) {
            String id = element.attributeValue("id");
            String exid = element.attributeValue("exid");
            String condition = element.attributeValue("condition");

            WatcherTag watcherTag = new WatcherTag();
            watcherTag.setId(id);
            watcherTag.setExId(exid);
            watcherTag.setCondition(condition);

            List<Element> then = element.elements("then");
            List<WatcherTag.Then> thens = new ArrayList<>();


            if (!then.isEmpty()) {
                for (Element element1 : then) {
                    WatcherTag.Then parse = watcherThenParse.parse(element1);
                    thens.add(parse);
                }
            }
            List<Element> catchs = element.elements("catch");
            List<WatcherTag.Catch> caches = new ArrayList<>();
            if (!catchs.isEmpty()) {
                for (Element aCatch : catchs) {
                    WatcherTag.Catch parse = watcherCatchParse.parse(aCatch);
                    caches.add(parse);
                }
            }
            watcherTag.setCatchs(caches);
            watcherTag.setThens(thens);

            return watcherTag;
        }
    }


    public class WatcherThenParse implements Parse<WatcherTag.Then> {
        @Override
        public WatcherTag.Then parse(Element element) {
            String actionId = element.attributeValue("actionId");
            WatcherTag.Then then = new WatcherTag.Then();
            then.setActionId(actionId);
            return then;
        }
    }


    public class WatcherCatchParse implements Parse<WatcherTag.Catch> {
        @Override
        public WatcherTag.Catch parse(Element element) {
            String actionId = element.attributeValue("actionId");
            WatcherTag.Catch aCatch = new WatcherTag.Catch();
            aCatch.setActionId(actionId);

            return aCatch;
        }
    }
}
