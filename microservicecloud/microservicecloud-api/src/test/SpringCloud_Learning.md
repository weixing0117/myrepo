#SpringCloud
##一、Eureka
	
	服务注册和发现中心，是Netflix的一个子模块。Eureka是一个基于
	Rest的服务，用于定位服务，以实现云端中间层服务发现和故障转移。
	服务注册与发现对于微服务架构来说是非常重要的，有了服务发现与注册，
	只要使用服务的标识符，就可以访问到服务，
	而不需要修改服务调用的配置文件了。功能类似于dubbo的注册中心，比如Zookeeper。
	一、构建注册中心：
	1.引入maven依赖，引入jar包 starter-eureka-server。
	告诉springboot这是一个服务端
	2.对eureka进行配置。使用yml文件进行配置。instance：hostname配置名字
	client 注册中心要配置 register-with-eureka:false表示不注册自己
	service-url：defaultzone设置注册中心地址 等等
	3.在启动类上添加Eureka注解标签，启动eureka组件@EnableEurekaServcer。
	二、建立微服务，注册到eureka上。
	1.引入starter-eureka starter-config
	2.设置yml，register设置为true，clinet serverurl设置为注册中心
	3.启动类上添加eurekaclient注解，@EnableEurekaClinet，向注册中心注册
###完善微服务
	一、eureka的自我保护机制
		（好死不如赖活着）
		1.某一个微服务不可用了，eureka不会立刻清理，依旧会对该微服务的信息进行保存。
		默认90S内没收到心跳，那么注册中心会down掉该微服务，但是
		保护机制会保存该服务的信息，如果他的心跳数恢复到阈值
		节点会退出自我保护机制，重新启用该服务。
		2.一般情况下，都不要修改自我保护机制，推荐使用默认
	二、完善细节。
		1.修改注册名称：yml中 设置 eureka： instance：instance-id：
		2.访问信息显示ip提示：设置prefer-ip-address：true
		3.在eureka中对微服务info内容详细信息的设置
		！引入actuator依赖（负责监控和信息配置）
		！！在父工程中引入build构建，添加文件名，允许访问资源的路径，在这个路径下以$。。。$形式的文件。
		！！！在client中yml文件中，配置info：$project.id$可以动态获取参数。
###服务发现
	一、对注册进eureka里面的服务，可以通过服务发现来获得该服务的信息。
	二、添加@Autowired DiscoveryClient 的组件client.getServices 可以获得所有的微服务的名称，
	三、getInstance获取实例，并在主启动类添加@EnableDiscoveryClient注解
	

###分布式 结构
	CAP理论：
		1.C：Consistency 强一致性
		2.A: Avaliability 可用性
		3.P: Partition tolerance 分区容错性

	NOSQL（Redis/mongdb）：
		遵守的也是CAP原则


	RDBMS(Mysql/oracle）：
	遵守的是ACID原则：
		A：Atomicity 原子性
		C：Consistency 一致性
		I：Isolation 独立性
		D：Durability 持久性


	CAP理论中：最多只能同时较好的满足两个。不可能同时很好的满足三个原则。
	因此CAP原理将NoSQL数据库分成了满足CA、CP和AP三大类
		1.CA-单点集群，满足一致性，可用行，但是可扩展性上不强
		2.CP-满足一致性，分区容错，通常性能不是特别高
		3.AP-满组可用和分区容错，通常对一致性要求低一些
	
	一般来说，在分布式系统的设计原则上，P是一定满足。根据需求来选定A还是P。
###Eureka和Zooker的比较
	**！zookeeper是保证 CP原则的框架**
	
	**！Netflix在设计Eureka时遵守的是AP原则**
	Eureka可以很好的应对因网络故障导致部分节点失去联系的情况
	。而不会像zookeeper那样使整个注册服务瘫痪。
##二、Ribbon 负载均衡
	1.nginx负载均衡是服务器端的负载均衡
		
	2.Ribbon是客户端的负载均衡（进程内LB,将LB逻辑集成到消费方）
		《客户端根据一系列算法去连接服务端的机器》
	3.Feign是集成Ribbon的轮询算法和Restful的面向接口的客户端的负载均衡
	4.硬件F5（集中式LB，贵）
###Ribbon配置
	1.引入Ribbon的Maven依赖
	2.在客户端添加@LoadBalanced注解，启动类上添加@EnableEurekaClient（R需要与E整合）
	整合后消费端可以直接访问服务，而不用关心IP和PORT
###Ribbon负载均衡算法
	一、七种负载均衡算法，常用轮询、随机和响应时间加权
	1.通过@Bean注入 选择自己需要的算法
	

	二、自定义算法
	1.在启动类上添加@RibbonClient（name=“服务名”，config=自定义的算法类
	2.自定义的配置类不能房子@ComponentScan所扫描的包以及子包下，不然不能被自定义
	3.在自定义类中添加@Config ，方法注入@Bean，该类返回一个方法类（众多实现其中一种）
	4.新建返回的方法类，继承AbtractLoadBalancerRule，重写其中choose算法。
	5.其中while循环算法中，判断线程是否中断，中断就会返回null，
	如果找到服务，则会停止线程（Yiled（））

##Feign负载均衡
###Feign简介
	rest client。Feign是一个申明式webservice的client（客户端）
	由于习惯性面向接口编程，比如webservice接口，Dao接口。由于已经形成规范
	从而推出了Feign，通过接口加注解来调用微服务。
	Feign相当于对RestTemplate和Ribbon整合后的进行进一步封装
###Feign的使用
	1.引入maven依赖
	2.对公共模块，（common或者api）引入maven依赖
	3.在公共模块添加一个ClientService的接口
	4.添加FeignClient注解（Value=？）调用？微服务的接口。
	5.该接口中，Mapping中为微服务的调用资源
	6.消费端通过调用FeignClient接口，就能访问到微服务中，其他
	7.在消费端添加@EnableFeignClient注解，开启
	8.Feign是使用Ribbon的默认轮询算法来实现负载均衡。
##Hystrix断路器
###分布式系统可能面临的问题：
	1.分布式系统有多个依赖关系，每个外部依赖不可避免的有时候会发生失败。
	2.高并发可能会导致延迟增加，备份队列，线程和其他资源紧张。导致整个系统发生更多的级联故障。
	
###简介（服务端）
	Hystrix是一个用于处理分布式系统的延迟和容错的开源库，
	在分布式系统里，许多依赖不可避免的会调用失败，比如超时，异常
	等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，
	避免级联故障、以提高分布式系统的可用性。
	
	它本身是一个开关装置，当某个服务单元发生故障之后，通过断路器的故障监控，
	向调用方返回一个符合预期的、可处理的备选响应（FallBack），从而避免雪崩。
	当某个链路不可用或超时时，会熔断服务调用；当检测到该节点微服务调用响应恢复到正常后恢复链路。
	（比如当缺省为5S/20次时就会启动熔断机制）
	类似Spring面向切面的环绕通知和前置通知。
###一、Hystrix的服务熔断
	1.引入maven的依赖 starter-Hystrix
	2.在yml中定义hystrix（在instance-id中添加）
	3.熔断注解为@HystrixCommand（报异常后的处理）---》（FallBackMethod来自定义熔断后返回）
	4.在主启动类上添加@EableCiruitBreaker开启熔断组件
	5.在处理时，可以利用面向接口的思维进行优化