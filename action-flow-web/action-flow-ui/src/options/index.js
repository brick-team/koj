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

const DEFAULT_DATA = {
  nodes: [
    {
      id: 'node1',
      x: 700,
      y: 100,
      // size: [300, 60],
      label: 'work1',
      anchorPoints: [
        [0.5, 1],
        [0, 0.5]
      ]
    },
    {
      id: 'node2',
      x: 700,
      y: 200,
      label: 'watcher',
      anchorPoints: [
        [0.5, 0],
        [0.5, 1]
      ]
    }, {
      id: 'then1',
      x: 600,
      y: 300,
      label: 'then1',
      anchorPoints: [
        [0.5, 0],
        [0.5, 1]
      ]
    }, {
      id: 'then2',
      x: 900,
      y: 300,
      label: 'then2',
      anchorPoints: [
        [0.5, 0],
        [0.5, 1]
      ]
    },

  ],
  edges: [
    {
      source: 'node1',
      target: 'node2',
      type: 'line',
      sourceAnchor: 0,
      // 该边连入 target 点的第 0 个 anchorPoint，
      targetAnchor: 0
    },


    {
      source: 'node2',
      target: 'then1',
      type: 'line',
      label: "否",
      sourceAnchor: 0,
      // 该边连入 target 点的第 0 个 anchorPoint，
      targetAnchor: 0
    },


    {
      source: 'node2',
      target: 'then2',
      type: 'line',
      label: "是",
      sourceAnchor: 0,
      // 该边连入 target 点的第 0 个 anchorPoint，
      targetAnchor: 0
    },


  ]
}

export default DEFAULT_DATA;
