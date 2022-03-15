package com.github.huifer.parse;

import com.alibaba.fastjson.JSON;
import com.github.huifer.entity.DocTag;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocParseTest {
    DocParse docParse = new DocParse();

    @Test
    public void parse() throws Exception {
        DocTag parse = docParse.parse("fl.xml");
        System.out.println(parse);
    }
}