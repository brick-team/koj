package com.github.brick.action.flow.method.enums;

/**
 * work节点类型
 */
public enum WorkTypeModel implements Type {

    /**
     * 动作
     */
    ACTION(),
    /**
     * 监控
     */
    WATCHER(),
    ;

    @Override
    public String type() {
        return name();
    }
}
