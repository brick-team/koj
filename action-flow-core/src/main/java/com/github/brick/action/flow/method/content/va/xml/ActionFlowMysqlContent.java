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

package com.github.brick.action.flow.method.content.va.xml;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.model.xml.ActionFlowXML;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.ResultExecuteEntityStorage;

import java.util.Map;

/**
 * @author Zen Huifer
 */
public class ActionFlowMysqlContent extends ActionFlowXMLContent {
	protected static StorageType storageType;

	static {
		storageType = StorageType.MYSQL;
	}

	public ActionFlowMysqlContent(String[] actionFlowFileNames) {
		super(actionFlowFileNames);
	}

	@Override protected void storage(Map<String, ActionFlowXML> loads) {
		ActionExecuteEntityStorage actionExecuteEntityStorage = StorageFactory.factory(
				storageType, ActionExecuteEntityStorage.class);
		FlowExecuteEntityStorage flowExecuteEntityStorage = StorageFactory.factory(
				storageType, FlowExecuteEntityStorage.class);
		ResultExecuteEntityStorage resultExecuteEntityStorage = StorageFactory.factory(
				storageType, ResultExecuteEntityStorage.class);

		loads.forEach((k, v) -> {
			actionExecuteEntityStorage.save(k, v.getActions());
			flowExecuteEntityStorage.save(k, v.getFlows());
			resultExecuteEntityStorage.save(k, v.getResults());
		});
	}
}
