<template>
  <div>
    <a-button type="primary" @click="aaa">视图模式</a-button>

    <a-button type="primary" @click="ccc">添加点</a-button>

    <a-modal
        :visible="dialogVisible"
        title="Tips"
        width="30%"
    >
      <!--      todo: 添加下拉框和输入框，用来确认加入的是work节点还是watcher节点-->
      <span>添加点咯</span>
      <template #footer>
      <span class="dialog-footer">
        <a-button @click="aaa_dalig_quxiao">取消</a-button>
        <a-button type="primary" @click="aaa_dalig_queding "
        >确认</a-button
        >
      </span>
      </template>
    </a-modal>

<!--todo: 添加线段的时候需要控制三个类型：1. work->watcher watcher->then watcher->cat-->
    <a-button @click="bbb">添加线段</a-button>

  </div>
  <div id="mountNode"></div>
</template>

<script>
import G6 from '@antv/g6';
import DEFAULT_DATA from "@/options";

export default {
  name: "start",
  created() {

  },
  mounted() {
    this.initG6()
  },
  data() {
    return {
      g: {},
      dt: DEFAULT_DATA,
      nodeType: '',
      dialogVisible: false,
      addNode: false,
      addedCount: 0,

    };
  },
  methods: {
    ccc() {
      this.dialogVisible = true;
    },

    aaa() {
      this.g.setMode("default");

    },
    aaa_dalig_queding() {
      this.dialogVisible = false;
      this.addNode = true;
      this.g.setMode("addNode");

    },
    aaa_dalig_quxiao() {
      this.dialogVisible = false;
      this.addNode = false;
      this.g.setMode("default");
    },
    bbb() {
      this.g.setMode("addEdge");
    },
    initG6() {
      const _this = this;
      G6.registerBehavior('click-add-node', {
        getEvents() {
          return {
            'canvas:click': 'onClick'
          }
        },
        onClick(ev) {

          const point = this.graph.getPointByClient(ev.clientX, ev.clientY)


          const graph = this.graph
          const node = this.graph.addItem('node', {
            x: point.x,
            y: point.y,
            type: "star", // 根据第一个弹框加入不同类型的
            id: `node-${_this.addedCount}`  // 生成唯一的 id


          });
          _this.addedCount++;
        },

      })

      G6.registerBehavior('click-add-edge', {
        getEvents() {
          return {
            'node:click': 'onClick',
            mousemove: 'onMousemove',
            'edge:click': 'onEdgeClick' // 点击空白处，取消边
          }
        },
        onClick(ev) {
          console.log(123)
          const node = ev.item
          const graph = this.graph
          const point = {
            x: ev.x,
            y: ev.y
          }
          const model = node.getModel()
          if (this.addingEdge && this.edge) {
            graph.updateItem(this.edge, {
              target: model.id
            })
            // graph.setItemState(this.edge, 'selected', true);
            this.edge = null
            this.addingEdge = false
          } else {
            this.edge = graph.addItem('edge', {
              source: model.id,
              target: point,
            })
            this.addingEdge = true
          }
        },
        onMousemove(ev) {
          const point = {
            x: ev.x,
            y: ev.y
          }
          if (this.addingEdge && this.edge) {
            this.graph.updateItem(this.edge, {
              target: point
            })
          }
        },
        onEdgeClick(ev) {
          const currentEdge = ev.item
          // 拖拽过程中，点击会点击到新增的边上
          if (this.addingEdge && this.edge == currentEdge) {
            graph.removeItem(this.edge)
            this.edge = null
            this.addingEdge = false
          }
        }
      })

      const menu = new G6.Menu({
        offsetX:   -20,
        offsetY:   -50,
        itemTypes: ['node', 'edge'],
        getContent(e) {
          const outDiv = document.createElement('div');

          outDiv.style.width = '80px';
          outDiv.style.cursor = 'pointer';
          outDiv.innerHTML = '<p id="deleteNode">删除节点</p>';
          outDiv.innerHTML += '<p id="info">查看点信息</p>';
          return outDiv;
        },
        handleMenuClick(target, item) {
          const {id} = target;
          const clickType = target.id;
          console.log(clickType);
          // todo: 不知道如何循环
          // _this.dt.nodes.remove(item);
        },
      });


      const graph = new G6.Graph({
        container: 'mountNode',
        width: window.innerWidth,
        height: window.innerHeight,
        modes: {
          default: ['drag-canvas', 'click-select', "drag-node", 'drag-node'],
          addNode: ['click-add-node', 'drag-canvas'],
          addEdge: ['click-add-edge', 'drag-canvas']
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
          type: 'polyline-edge', // polyline
          style: {
            radius: 6,
            offset: 15,
            stroke: '#aab7c3',
            lineAppendWidth: 10, // 防止线太细没法点中
            /* startArrow:      {
                path: 'M 0,0 L 8,4 L 7,0 L 8,-4 Z',
                fill: '#aab7c3',
            }, */
            endArrow: {
              path: 'M 0,0 L 8,4 L 7,0 L 8,-4 Z',
              fill: '#aab7c3',
              stroke: '#aab7c3',
            },
          },
        },
        plugins: [menu],

      });
      graph.read(DEFAULT_DATA);
      this.g = graph;
    }
  }
};

</script>
<style>
#mountNode {
  width: 100%;
  height: 100%;
  border: 1px saddlebrown solid;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>
