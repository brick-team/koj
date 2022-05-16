package com.github.brick.action.flow.model.req;

import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class ActionExecuteReq extends ActionExecuteEntity {


    @Override
    public Serializable getId() {
        return null;
    }
}
