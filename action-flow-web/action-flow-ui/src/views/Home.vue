<template>
  <div>

    <el-button @click="hhhh">aaa</el-button>
    <div id="mountNode"></div>


  </div>
</template>

<script>
import G6 from '@antv/g6';

export default {
  name: 'polyline',
  data() {
    return {
      graph: {},
      data: {
        nodes: [{
          id: 'node1',
          x: 100,
          y: 200
        }, {
          id: 'node2',
          x: 300,
          y: 200
        }, {
          id: 'node3',
          x: 300,
          y: 300
        }],
        edges: [{
          id: 'edge1',
          target: 'node2',
          source: 'node1'
        }]
      }
      ,
      tooltip: "",
      top: 0,
      left: 0, addedCount: 0,

    };
  },
  methods: {
    hhhh() {
      this.graph.setMode("addNode");
    },
    ff() {
      console.log("ff");
      G6.registerBehavior('click-add-edge', {
        getEvents() {
          return {
            'node:click': 'onClick',
            mousemove: 'onMousemove',
            'edge:click': 'onEdgeClick' // 点击空白处，取消边
          };
        },
        onClick(ev) {
          const node = ev.item;
          const graph = this.graph;
          const point = {
            x: ev.x,
            y: ev.y
          };
          const model = node.getModel();
          if (this.addingEdge && this.edge) {
            graph.updateItem(this.edge, {
              target: model.id
            });
            // graph.setItemState(this.edge, 'selected', true);
            this.edge = null;
            this.addingEdge = false;
          } else {
            this.edge = graph.addItem('edge', {
              source: model.id,
              target: point
            });
            this.addingEdge = true;
          }
        },
        onMousemove(ev) {
          const point = {
            x: ev.x,
            y: ev.y
          };
          if (this.addingEdge && this.edge) {
            this.graph.updateItem(this.edge, {
              target: point
            });
          }
        },
        onEdgeClick(ev) {
          const currentEdge = ev.item;
          // 拖拽过程中，点击会点击到新增的边上
          if (this.addingEdge && this.edge == currentEdge) {
            this.graph.removeItem(this.edge);
            this.edge = null;
            this.addingEdge = false;
          }
        }
      });

      // Register a custom behavior to add node
      G6.registerBehavior('click-add-node', {
        getEvents() {
          return {
            'canvas:click': 'onClick'
          };
        },
        addedCount: 0,
        onClick(ev) {

          const graph = this.graph;
          const node = this.graph.addItem('node', {
            x: ev.canvasX,
            y: ev.canvasY,
            id: `node-${this.addedCount}`, // 生成唯一的 id
          });
          this.addedCount++;
        }
      });
    },
    createGraphic() {


      this.graph = new G6.Graph({
        container: 'mountNode',
        width: 500,
        height: 500,
        modes: {
          default: ['drag-node', 'click-select'],
          addNode: ['click-add-node', 'click-select'],
          addEdge: ['click-add-edge', 'click-select']
        },
        // The node styles in different states
        nodeStateStyles: {
          // The node styles in selected state, corresponds to the built-in click-select behavior
          selected: {
            stroke: '#666',
            lineWidth: 2,
            fill: 'steelblue'
          }
        }
      });

      this.graph.data(this.data);
      this.graph.render();
    },
    initGraphEvent() {
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.ff();
      this.createGraphic();
      this.initGraphEvent();
      console.log(this.graph);
    });
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
