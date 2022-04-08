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

package com.github.brick.action.flow.method.factory.storage;

import com.github.brick.action.flow.method.enums.StorageType;
import com.github.brick.action.flow.method.factory.ActionFlowFactory;
import com.github.brick.action.flow.storage.api.nv.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.nv.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.nv.ResultExecuteEntityStorage;
import com.github.brick.action.flow.storage.memory.nv.ActionExecuteEntityMemoryStorage;
import com.github.brick.action.flow.storage.memory.nv.FlowExecuteEntityMemoryStorage;
import com.github.brick.action.flow.storage.memory.nv.ResultExecuteEntityMemoryStorage;

import java.util.HashMap;
import java.util.Map;

public final class StorageFactory {
    private static final Map<Class<?>, ActionFlowStorageFactory<?>> map = new HashMap<>(8);

    static {
        map.put(ActionExecuteEntityStorage.class, new ActionExecuteEntityStorageFactory());
        map.put(FlowExecuteEntityStorage.class, new FlowExecuteEntityStorageFactory());
        map.put(ResultExecuteEntityStorage.class, new ResultExecuteEntityStorageFactory());
    }

    @SuppressWarnings(value = {"unchecked"})
    public static <T> T factory(StorageType storageType, Class<T> clazz) {

        if (ActionExecuteEntityStorage.class.isAssignableFrom(clazz)) {
            ActionFlowStorageFactory<?> factory = map.get(ActionExecuteEntityStorage.class);
            return (T) factory.factory(storageType);
        }
        else if (FlowExecuteEntityStorage.class.isAssignableFrom(clazz)) {
            ActionFlowStorageFactory<?> factory = map.get(FlowExecuteEntityStorage.class);
            return (T) factory.factory(storageType);
        }
        else if (ResultExecuteEntityStorage.class.isAssignableFrom(clazz)) {
            ActionFlowStorageFactory<?> factory = map.get(ResultExecuteEntityStorage.class);
            return (T) factory.factory(storageType);
        }

        return null;

    }

    private interface ActionFlowStorageFactory<T> extends ActionFlowFactory<StorageType, T> {


    }

    private final static class ActionExecuteEntityStorageFactory implements ActionFlowStorageFactory<ActionExecuteEntityStorage> {
        Map<StorageType, ActionExecuteEntityStorage> map = new HashMap<>();

        @Override
        public ActionExecuteEntityStorage factory(StorageType type) {
            switch (type) {
                case MYSQL:
                    break;
                case MEMORY:
                    ActionExecuteEntityStorage actionExecuteEntityStorage = map.get(type);
                    if (actionExecuteEntityStorage != null) {
                        return actionExecuteEntityStorage;
                    }
                    else {
                        ActionExecuteEntityMemoryStorage actionExecuteEntityMemoryStorage = new ActionExecuteEntityMemoryStorage();
                        map.put(type, actionExecuteEntityMemoryStorage);
                        return actionExecuteEntityMemoryStorage;
                    }

            }

            throw new IllegalArgumentException("参数异常，无法生成 ActionExecuteEntityStorage");

        }
    }

    private final static class FlowExecuteEntityStorageFactory implements ActionFlowStorageFactory<FlowExecuteEntityStorage> {
        Map<StorageType, FlowExecuteEntityStorage> map = new HashMap<>();

        @Override
        public FlowExecuteEntityStorage factory(StorageType type) {
            switch (type) {
                case MYSQL:
                    break;
                case MEMORY:
                    FlowExecuteEntityStorage flowExecuteEntityStorage = map.get(type);
                    if (flowExecuteEntityStorage != null) {
                        return flowExecuteEntityStorage;
                    }
                    else {
                        FlowExecuteEntityMemoryStorage flowExecuteEntityMemoryStorage = new FlowExecuteEntityMemoryStorage();
                        map.put(type, flowExecuteEntityMemoryStorage);
                        return flowExecuteEntityMemoryStorage;

                    }
            }

            throw new IllegalArgumentException("参数异常，无法生成 FlowExecuteEntityStorage");

        }
    }

    private final static class ResultExecuteEntityStorageFactory implements ActionFlowStorageFactory<ResultExecuteEntityStorage> {
        Map<StorageType, ResultExecuteEntityStorage> map = new HashMap<>();

        @Override
        public ResultExecuteEntityStorage factory(StorageType type) {
            switch (type) {
                case MYSQL:
                    break;
                case MEMORY:
                    ResultExecuteEntityStorage resultExecuteEntityStorage = map.get(type);
                    if (resultExecuteEntityStorage != null) {
                        return resultExecuteEntityStorage;
                    }
                    else {
                        ResultExecuteEntityMemoryStorage resultExecuteEntityMemoryStorage = new ResultExecuteEntityMemoryStorage();
                        map.put(type, resultExecuteEntityMemoryStorage);
                        return resultExecuteEntityMemoryStorage;
                    }
            }

            throw new IllegalArgumentException("参数异常，无法生成 ResultExecuteEntityStorage");

        }
    }

}
