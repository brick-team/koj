package com.github.brick.action.flow.web.common.ctr;

import com.github.brick.action.flow.method.content.va.ActionFlowContent;
import com.github.brick.action.flow.method.content.va.memory.ActionFlowMemoryContent;
import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.req.FlowExecuteReq;
import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;
import com.github.brick.action.flow.web.common.config.ActionFlowConfiguration;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
@RestController
public class FlowController {

    private final ActionFlowConfiguration actionFlowConfiguration;

    private final FlowExecuteEntityStorage storage;
    Gson gson = new Gson();
    @Autowired
    private ActionFlowContent content;

    public FlowController(ActionFlowConfiguration actionFlowConfiguration) {
        this.actionFlowConfiguration = actionFlowConfiguration;
        this.storage = StorageFactory.factory(
                actionFlowConfiguration.getStorage().getType(), FlowExecuteEntityStorage.class);

    }

    @PostMapping("/flow/save")
    public ResponseEntity save(
            @RequestBody FlowExecuteReq req) {
        storage.save(null, Lists.newArrayList(req));
        return ResponseEntity.ok(true);
    }

    @GetMapping("/flow/list")
    public ResponseEntity list(int size,
                               int page) {
        Page page1 = this.storage.page(size, page);
        return ResponseEntity.ok(page1);
    }

    @PostMapping("/flow/execute")
    public ResponseEntity execute(
            @RequestBody FlowExecuteReq req
    ) {
        String execute = content.execute(req.getId(), gson.toJson(req.getMap()));
        return ResponseEntity.ok(execute);
    }
}
