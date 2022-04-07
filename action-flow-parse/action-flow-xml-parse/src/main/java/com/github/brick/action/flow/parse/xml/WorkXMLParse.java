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


import com.github.brick.action.flow.model.xml.WatcherXML;
import com.github.brick.action.flow.model.xml.WorkXML;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class WorkXMLParse implements ParseXML<WorkXML> {
    private static final String STEP_ATTR = "step";
    private static final String REF_ID_ATTR = "ref_id";
    private static final String WATCHER_ATTR = "watcher";
    private static final String THEN_ATTR = "then";
    private static final String CAT_ATTR = "cat";
    private static final String WORK_ATTR = "work";
    WatcherXMLParse watcherXMLParse = new WatcherXMLParse();

    @Override
    public WorkXML parse(Element element) throws Exception {
        WorkXML workXML = new WorkXML();
        workXML.setStep(element.attributeValue(STEP_ATTR));
        workXML.setRefId(element.attributeValue(REF_ID_ATTR));
        ArrayList<WatcherXML> watchers = new ArrayList<>();
        List<Element> watcher = element.elements(WATCHER_ATTR);

        for (Element element1 : watcher) {
            WatcherXML parse = watcherXMLParse.parse(element1);
            ArrayList<WorkXML> then1 = new ArrayList<>();
            Element then = element1.element(THEN_ATTR);
            if (then != null) {
                List<Element> work = then.elements(WORK_ATTR);
                for (Element element2 : work) {
                    WorkXML parse1 = parse(element2);
                    then1.add(parse1);
                }
            }
            parse.setThen(then1);


            ArrayList<WorkXML> cat1 = new ArrayList<>();
            Element cat = element1.element(CAT_ATTR);
            if (cat != null) {
                List<Element> work = cat.elements(WORK_ATTR);
                for (Element element2 : work) {
                    WorkXML parse1 = parse(element2);
                    cat1.add(parse1);
                }
            }
            parse.setCat(cat1);
            watchers.add(parse);
        }
        workXML.setWatchers(watchers);

        return workXML;
    }
}
