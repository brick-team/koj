package com.github.brick.action.flow.web.common.ctr;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.ActionType;
import com.github.brick.action.flow.model.req.ActionExecuteReq;
import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.web.common.config.ActionFlowConfiguration;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
@RestController
public class JavaActionController {
    private final ActionFlowConfiguration actionFlowConfiguration;

    private final ActionExecuteEntityStorage storage;

    public JavaActionController(ActionFlowConfiguration actionFlowConfiguration) {
        this.actionFlowConfiguration = actionFlowConfiguration;
        this.storage = StorageFactory.factory(
                actionFlowConfiguration.getStorage().getType(), ActionExecuteEntityStorage.class);

    }

    @PostMapping("/java/action/save")
    public ResponseEntity<Boolean> save(
            @RequestBody ActionExecuteReq actionExecuteEntity
    ) throws Exception {
        actionExecuteEntity.setType(ActionType.JAVA_METHOD);
        storage.save(null, Lists.newArrayList(actionExecuteEntity));
        return ResponseEntity.ok(true);
    }

    @GetMapping("/java/action/list")
    public Page list(
            int size,
            int page
    ) {
        return this.storage.page(ActionType.JAVA_METHOD, page, size);
    }
}
