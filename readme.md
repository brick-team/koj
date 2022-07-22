# Action Flow

Action Flow is a lightweight, fast flow engine framework.

## Why Action Flow 

From a macro perspective, as microservices architecture becomes the mainstream of WEB backend architecture, the services are usually split more and more finely in microservices architecture, which are relatively independent of each other for the backend, but these independent services also need to be combined by aggregation services to achieve the actual implementation of a business. As the number of aggregated services grows over time, the maintenance cost of these numerous aggregated services will also increase.
From a micro perspective, the development of individual services (applications) generally takes the smallest granularity to implement each core business service, and then assembles the business processes in the outer layer, and as time goes by, the number of classes in which these business processes are located will increase, and the maintenance cost of these numerous classes will also increase.

From a macro perspective and a micro perspective, we can find that these core operations are nothing more than REST_API calls or function calls, and their various combinations are solidified by the program code (if you need to modify the program code needs to be changed). The Action Flow project wants to manage them through some visual configuration to reduce the number of aggregated services in the microservice architecture, and to visually configure the combined business forms in a single application to reduce the number of combined classes in a single service.




## Action Flow Design Ideas
In the Action Flow project, the execution flow is made up of three core objects.
1. action: used to define the specific behavior that needs to be executed, including JAVA functions, HTTP requests. 2.
2. flow: used to define the execution flow. 3.
3. result: used to define the results of an execution process.

The most important of the above three objects is the flow object, it needs to undertake the entire implementation of the definition of the chain of work, hierarchy: work->watcher->then\cat

```mermaid
  flowchart TD;
  work --execution-->watcher
  watcher-->data logic validation
  data logic validation-->validation passed-->then
  data logic validation-->validation failed-->cat
 
```

> TIPS: In the above execution flow then and cat both store work and support nested use.


