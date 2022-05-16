package com.github.brick.action.flow.model.req;

import com.github.brick.action.flow.model.execute.FlowExecuteEntity;

import java.io.Serializable;
import java.util.Map;

public class FlowExecuteReq extends FlowExecuteEntity {
    private String id;

    private Map map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public Serializable getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
