package com.github.brick.action.flow.web.common.ctr;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.ActionType;
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.model.req.ActionExecuteReq;
import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.web.common.config.ActionFlowConfiguration;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.PrimitiveIterator;
import java.util.UUID;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
@RestController
public class RestActionController {
    private final ActionFlowConfiguration actionFlowConfiguration;

    private final ActionExecuteEntityStorage storage;

    public RestActionController(ActionFlowConfiguration actionFlowConfiguration) {
        this.actionFlowConfiguration = actionFlowConfiguration;
        this.storage = StorageFactory.factory(
                actionFlowConfiguration.getStorage().getType(), ActionExecuteEntityStorage.class);

    }

    @PostMapping("/rest/action/save")
    public ResponseEntity<Boolean> save(
            @RequestBody ActionExecuteReq actionExecuteEntity
    ) throws Exception {
        actionExecuteEntity.setType(ActionType.REST_API);
        actionExecuteEntity.setId(UUID.randomUUID().toString());
        storage.save(null, Lists.newArrayList(actionExecuteEntity));
        return ResponseEntity.ok(true);
    }

    @GetMapping("/list")
    public ResponseEntity<Page> list(
            int size,
            int page
    ) {

        Page page1 = this.storage.page(ActionType.REST_API, page, size);
        return ResponseEntity.ok(page1);
    }

}
