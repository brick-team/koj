package com.github.brick.enums;

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
