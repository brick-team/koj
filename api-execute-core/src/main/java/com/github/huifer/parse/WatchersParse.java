package com.github.huifer.parse;

import com.github.huifer.entity.WatcherTag;
import com.github.huifer.entity.WatchersTag;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class WatchersParse implements Parse<WatchersTag> {
    WatcherParse watcherParse = new WatcherParse();
    WatcherThenParse watcherThenParse = new WatcherThenParse();
    WatcherCacheParse watcherCacheParse = new WatcherCacheParse();

    @Override
    public WatchersTag parse(Element element) {
        WatchersTag watchersTag = new WatchersTag();
        ArrayList<WatcherTag> list = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                WatcherTag parse = watcherParse.parse((Element) item);
                list.add(parse);
            }
        }

        watchersTag.setList(list);
        return watchersTag;
    }


    public class WatcherParse implements Parse<WatcherTag> {
        @Override
        public WatcherTag parse(Element element) {
            String id = element.getAttribute("id");
            String exid = element.getAttribute("exid");
            String condition = element.getAttribute("condition");

            WatcherTag watcherTag = new WatcherTag();
            watcherTag.setId(id);
            watcherTag.setExId(exid);
            watcherTag.setCondition(condition);

            NodeList childNodes = element.getChildNodes();
            List<WatcherTag.Then> thens = new ArrayList<>();
            List<WatcherTag.Cache> caches = new ArrayList<>();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item instanceof Element) {
                    if (item.getNodeName().equals("then")) {
                        WatcherTag.Then parse = watcherThenParse.parse((Element) item);
                        thens.add(parse);
                    }
                    if (item.getNodeName().equals("cache")) {
                        WatcherTag.Cache parse = watcherCacheParse.parse((Element) item);
                        caches.add(parse);
                    }
                }
            }
            watcherTag.setThens(thens);
            watcherTag.setCaches(caches);



            return watcherTag;
        }
    }


    public class WatcherThenParse implements Parse<WatcherTag.Then> {
        @Override
        public WatcherTag.Then parse(Element element) {
            String actionId = element.getAttribute("actionId");
            WatcherTag.Then then = new WatcherTag.Then();
            then.setActionId(actionId);
            return then;
        }
    }


    public class WatcherCacheParse implements Parse<WatcherTag.Cache> {
        @Override
        public WatcherTag.Cache parse(Element element) {
            String actionId = element.getAttribute("actionId");
            WatcherTag.Cache cache = new WatcherTag.Cache();
            cache.setActionId(actionId);

            return cache;
        }
    }
}
