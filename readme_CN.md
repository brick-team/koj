# Action Flow

Action Flow是一个轻量，快速的流程引擎框架。

## 为什么有 Action Flow

通常情况下对于程序的执行流程控制采用编码的方式进行处理，如果想要改变流程控制需要对程序源代码进行修改。对于这样的操作经常发生在软件研发中，特别是各种业务系统，对于这样的场景Action
Flow想要寻找突破，通过一些约定的配置来讲流程控制进行编写，实现动态的变化处理流程。

## 使用

现阶段 Action Flow 基于XML文件进行流程配置，下方为 Action Flow 的基础配置，文件名:`base.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<ae>
    <actions>
        <action id="helloPoint" class="com.github.brick.sample.HelloPrint"
                method="hello">
        </action>
    </actions>

    <flows>
        <flow id="1">
            <work id="w1" type="action" ref_id="helloPoint"/>
        </flow>
    </flows>
</ae>
```

完成上述 Action Flow 配置文件编写后调用执行器将其执行。

```java
public class ActionFlowDemo {

    public static void main(String[] args) throws Exception {
        FlowExecute flowExecute = new FlowExecuteImpl();
        flowExecute.execute("base.xml", "1", FLowModel.XML);
    }
}

```
