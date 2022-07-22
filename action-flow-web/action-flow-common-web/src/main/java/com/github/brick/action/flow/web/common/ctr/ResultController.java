package com.github.brick.action.flow.web.common.ctr;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.model.execute.ResultExecuteEntity;
import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.ResultExecuteEntityStorage;
import com.github.brick.action.flow.web.common.config.ActionFlowConfiguration;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
@RestController
public class ResultController {
    private final ActionFlowConfiguration actionFlowConfiguration;

    private final ResultExecuteEntityStorage storage;

    public ResultController(ActionFlowConfiguration actionFlowConfiguration) {
        this.actionFlowConfiguration = actionFlowConfiguration;
        this.storage = StorageFactory.factory(
                actionFlowConfiguration.getStorage().getType(), ResultExecuteEntityStorage.class);

    }

    @PostMapping("/result/save")
    public ResponseEntity save(
            @RequestBody ResultExecuteEntity resultExecuteEntity
    ) throws Exception {

        storage.save(null, Lists.newArrayList(resultExecuteEntity));
        return ResponseEntity.ok(true);
    }

    @GetMapping("/result/list")
    public ResponseEntity list(int size,
                               int page) {
        Page page1 = this.storage.page(size, page);
        return ResponseEntity.ok(page1);
    }
}
