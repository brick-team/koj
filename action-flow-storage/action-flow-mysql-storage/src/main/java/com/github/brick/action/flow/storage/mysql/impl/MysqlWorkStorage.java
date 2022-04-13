/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.brick.action.flow.storage.mysql.impl;

import com.github.brick.action.flow.method.enums.WorkNodeType;
import com.github.brick.action.flow.method.req.WorkNode;
import com.github.brick.action.flow.storage.api.WorkStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfWorkCz;
import com.github.brick.action.flow.storage.mysql.repository.AfWorkCzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MysqlWorkStorage implements WorkStorage {
    @Autowired(required = false)
    private AfWorkCzRepository workCzRepository;

    @Override
    public void saveWorkNodes(List<WorkNode> workNodes, Long flowId) {
        for (WorkNode workNode : workNodes) {
            saveWorkNode(workNode, flowId);
        }
    }

    @Override
    public void saveWorkNode(WorkNode workNode, Long flowId) {
        AfWorkCz entity1 = new AfWorkCz();
        entity1.setFlowId(flowId);
        entity1.setWorkType(WorkNodeType.START.getId());

        entity1.setPid(null);
        entity1.setType(workNode.getType());
        entity1.setRefId(workNode.getRefId());

        AfWorkCz save = workCzRepository.save(entity1);


        // TODO: 2022/3/30 then cat 数据存储
        List<WorkNode> cat = workNode.getCat();
        handlerCat(cat, save.getId(), flowId);
        List<WorkNode> then = workNode.getThen();
        handlerThe(then, save.getId(), flowId);

    }

    private void handlerCat(List<WorkNode> data, Long pid, long workFlowId) {
        for (WorkNode WorkNode : data) {
            AfWorkCz entity1 = new AfWorkCz();
            entity1.setWorkType(WorkNodeType.CAT.getId());
            entity1.setFlowId(workFlowId);

            entity1.setPid(pid);
            entity1.setType(WorkNode.getType());
            entity1.setRefId(WorkNode.getRefId());

            AfWorkCz save = workCzRepository.save(entity1);
            List<WorkNode> then = WorkNode.getThen();
            handlerThe(then, save.getId(), workFlowId);
            List<WorkNode> cat = WorkNode.getCat();
            handlerCat(cat, save.getId(), workFlowId);

        }
    }

    private void handlerThe(List<WorkNode> data, Long pid, long workFlowId) {
        for (WorkNode WorkNode : data) {
            AfWorkCz entity1 = new AfWorkCz();
            entity1.setWorkType(WorkNodeType.THEN.getId());
            entity1.setFlowId(workFlowId);
            entity1.setPid(pid);
            entity1.setType(WorkNode.getType());
            entity1.setRefId(WorkNode.getRefId());

            AfWorkCz save = workCzRepository.save(entity1);
            List<WorkNode> then = WorkNode.getThen();
            handlerThe(then, save.getId(), workFlowId);
            List<WorkNode> cat = WorkNode.getCat();

            handlerCat(cat, save.getId(), workFlowId);

        }


    }


    @Override
    public List<WorkNode> findByFlowId(Long flowId) {
        List<AfWorkCz> list = workCzRepository.findByFlowId(flowId);
        List<WorkNode> res = new ArrayList<>();
        Map<Long, List<AfWorkCz>> pidMap = new HashMap<>();

        for (AfWorkCz afWorkCz : list) {
            Long pid = afWorkCz.getPid();
            if (pid != null) {
                List<AfWorkCz> afWorkCzs = pidMap.get(pid);
                if (afWorkCzs == null) {
                    afWorkCzs = new ArrayList<>();
                }
                afWorkCzs.add(afWorkCz);
                pidMap.put(pid, afWorkCzs);
            }
        }

        List<AfWorkCz> starts = list.stream().filter(s -> s.getWorkType().equals(WorkNodeType.START.getId())).collect(Collectors.toList());

        for (AfWorkCz start : starts) {
            WorkNode workNode = new WorkNode();
            workNode.setType(start.getType());
            workNode.setRefId(start.getRefId());
            workNode.setThen(workCzToThens(start, pidMap));
            workNode.setCat(workCzToCat(start, pidMap));
            workNode.setId(start.getId());
            res.add(workNode);
        }


        return res;
    }

    private List<WorkNode> workCzToThens(AfWorkCz start, Map<Long, List<AfWorkCz>> pidMap) {
        List<AfWorkCz> afWorkCzs = pidMap.get(start.getId());
        List<WorkNode> res = new ArrayList<>();
        if (afWorkCzs != null) {
            List<AfWorkCz> collect = afWorkCzs.stream().filter(s -> {
                return s.getWorkType().equals(WorkNodeType.THEN.getId());
            }).collect(Collectors.toList());

            for (AfWorkCz afWorkCz : collect) {
                WorkNode workNode = new WorkNode();
                workNode.setId(afWorkCz.getId());
                workNode.setType(afWorkCz.getType());
                workNode.setRefId(afWorkCz.getRefId());
                workNode.setThen(workCzToThens(afWorkCz, pidMap));
                workNode.setCat(workCzToCat(afWorkCz, pidMap));
                res.add(workNode);
            }
        }

        return res;
    }

    private List<WorkNode> workCzToCat(AfWorkCz start, Map<Long, List<AfWorkCz>> pidMap) {
        List<WorkNode> res = new ArrayList<>();
        List<AfWorkCz> afWorkCzs = pidMap.get(start.getId());
        if (afWorkCzs != null) {
            List<AfWorkCz> collect = afWorkCzs.stream().filter(s -> {
                return s.getWorkType().equals(WorkNodeType.CAT.getId());
            }).collect(Collectors.toList());

            for (AfWorkCz afWorkCz : collect) {
                WorkNode workNode = new WorkNode();
                workNode.setId(afWorkCz.getId());
                workNode.setType(afWorkCz.getType());
                workNode.setRefId(afWorkCz.getRefId());
                workNode.setThen(workCzToThens(afWorkCz, pidMap));
                workNode.setCat(workCzToCat(afWorkCz, pidMap));
                res.add(workNode);
            }
        }

        return res;
    }

}
