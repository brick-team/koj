<template>
  <div id="app">
    <!-- <button @click="toUpdate">切换数据</button> -->
    <div id="container"></div>
    <div>
      <el-button @click="addNode">添加节点</el-button>
      <el-button @click="hh">输出图像信息</el-button>
    </div>
  </div>
</template>

<script>
// import {init,update} from './utils/g6Utils.min.js';
import G6 from '@antv/g6';
// import insertCss from 'insert-css'

export default {
  name: 'gpolyline',
  data() {
    return {
      gdata: {
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
            sourceAnchor: 0,
            // 该边连入 target 点的第 0 个 anchorPoint，
            targetAnchor: 0
          },


          {
            source: 'node2',
            target: 'then2',
            type: 'line',
            sourceAnchor: 0,
            // 该边连入 target 点的第 0 个 anchorPoint，
            targetAnchor: 0
          },


        ]
      },
      graph: null,
      count: 0,

    };
  },
  methods: {
    hh() {
      console.log(this.gdata)
    },
    addNode() {

      this.gdata.nodes.push(
        {
          id: "uid" + this.count,
          x: 100,
          y: 100,
          // size: [300, 60],
          label: '手工添加',
          anchorPoints: [
            [0.5, 1],
            [0, 0.5]
          ]
        }
      )
      this.graph.render();
      this.count++;
    }
  },
  mounted() {

    const width = document.getElementById('container').scrollWidth;
    // const height = document.getElementById('container').scrollHeight || 500;
    this.graph = new G6.Graph({
      container: 'container',
      width,
      height: 700,
      modes: {
        default: ['drag-canvas', 'click-select', "drag-node"]
      },
      // 节点类型及样式
      defaultNode: {
        type: 'rect',
        size: [150, 50],
        style: {
          fill: '#DEE9FF',
          stroke: '#5B8FF9'
        }
      },
      // 连线类型及样式
      defaultEdge: {
        type: 'polyline',
        style: {
          // stroke: '#F6BD16',
          offset: 25,
          endArrow: true,
          lineWidth: 2,
          stroke: '#333'
        }
      }

    });
    this.graph.data(this.gdata);
    this.graph.render();
  }
};
</script>

<style>
#container {
  width: 100%;
  height: 100%;
  border: 1px saddlebrown solid;
}
</style>
