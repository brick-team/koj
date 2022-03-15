package com.github.huifer.el;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elts {
    @Test
    public void tt() {
        D d = new D();
        HashMap<String, String> a = new HashMap<>();
        a.put("b", "aa");
        d.a = a;

        ArrayList<String> d1 = new ArrayList<>();
        d1.add("1");
        d1.add("21");
        d.d = d1;

        String json = JSON.toJSONString(d);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        Object b = JsonPath.read(document, "$.d");
        System.out.println(b);
    }

    @Data
    public static class D {
        public Map<String, String> a;
        public List<String> d;
    }
}
