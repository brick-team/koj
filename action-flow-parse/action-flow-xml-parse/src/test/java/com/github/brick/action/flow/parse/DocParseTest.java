package com.github.brick.action.flow.parse;

import com.github.brick.action.flow.method.entity.AllEntity;
import com.github.brick.action.flow.method.parse.xml.DocParse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocParseTest {
    private static final Logger logger = LoggerFactory.getLogger(DocParseTest.class);
    DocParse docParse = new DocParse();

    @Test
    public void parse() throws Exception {
        AllEntity parse = docParse.parse("fl.xml");
        System.out.println();
    }
}