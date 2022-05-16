package com.github.brick.action.flow.web.common.ctr;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
@RestController
public class ActionController {
}
