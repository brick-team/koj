<template>
  <div id="app">
    <div id="container"></div>

    <div
      v-if="tooltip && !isMouseDown"
      :style="`top: ${top}px; left: ${left}px;`"
      class="g6-tooltip"
    >
      id: {{ tooltip }}
    </div>
  </div>
</template>

<script>
import G6 from '@antv/g6';
import DEFAULT_DATA from "../options/index";

export default {
  name: 'gpolyline',
  data() {
    return {
      graph: {},
      data: {},
      tooltip: "",
      top: 0,
      left: 0,
    };
  },
  methods: {
    createGraphic() {
      const vm = this;
      const grid = new G6.Grid();
      const menu = new G6.Menu({
        offsetX: -20,
        offsetY: -50,
        itemTypes: ['node', 'edge'],
        getContent(e) {
          const outDiv = document.createElement('div');

          outDiv.style.width = '80px';
          outDiv.style.cursor = 'pointer';
          outDiv.innerHTML = '<p id="deleteNode">删除节点</p>';
          return outDiv;
        },
        handleMenuClick(target, item) {
          // 触发删除操作
          console.log("开始删除节点")
        },
      });
      const minimap = new G6.Minimap({
        size: [100, 100],
      });
      this.graph = new G6.Graph({
        container: 'container',
        width: window.innerWidth,
        height: window.innerHeight,
        modes: {

          default: ['drag-canvas', 'drag-shadow-node', 'canvas-event', 'delete-item', 'select-node', 'hover-node', 'active-edge'],
          originDrag: ['drag-canvas', 'drag-node', 'canvas-event', 'delete-item', 'select-node', 'hover-node', 'active-edge'],
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
        },
        plugins: [menu, minimap, grid],


      })
      this.graph.read(DEFAULT_DATA);
    },
    initGraphEvent() {
      this.graph.on('node:click', e => {
        console.log("节点选择")
      });
      this.graph.on('node:drop', e => {
        // 节点删除
        e.item.getOutEdges().forEach(edge => {
          edge.clearStates('edgeState');
        });
      });


      this.graph.on('node:mouseover', e => {
        console.log("111")
        if (e && e.item) {
          this.tooltip = e.item.get('model').id;
          this.left = e.clientX + 40;
          this.top = e.clientY - 20;
        }
      });


    },
  },
  mounted() {
    this.$nextTick(() => {
      this.createGraphic();
      this.initGraphEvent();
      console.log(this.graph);
    })
  }
};
</script>

<style>
#container {
  width: 100%;
  height: 100%;
  border: 1px saddlebrown solid;
}

.g6-tooltip {
  position: fixed;
  top: 0;
  left: 0;
  font-size: 12px;
  color: #545454;
  border-radius: 4px;
  border: 1px solid #e2e2e2;
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: rgb(174, 174, 174) 0 0 10px;
  padding: 10px 8px;
}

.g6-minimap {
  position: absolute;
  right: 0;
  bottom: 0;
  background: #fff;
}
</style>
