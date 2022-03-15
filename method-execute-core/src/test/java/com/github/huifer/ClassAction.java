package com.github.huifer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassAction {
    private static final Logger logger = LoggerFactory.getLogger(ClassAction.class);

    public void hh(Mc mc) {

        if (logger.isInfoEnabled()) {
            logger.info("hh,mc = {}", JSON.toJSONString(mc));
        }


    }

    @Data
    public static class Mc {
        private String name;
        private String pwd;
    }
}
