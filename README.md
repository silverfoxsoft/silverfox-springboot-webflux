## 技术架构

| 名称         | 版本    | 备注        |
| ---------- | ----- | --------- |
| springboot | 3.2.0 | 微服务框架     |
| JDK        | 21    | 虚拟线程支持    |
| webflux    | -     | 响应式       |
| redis      | -     | 响应式       |
| mysql      | -     | 响应式       |
| lombok     | -     | 对象封装工具    |
| ddd        | -     | 领域驱动设计    |
| nacos      | -     | 服务发现&配置中心 |

极简架构设计



开箱即用的生产级微服务代码架构模版

## 领域驱动设计

##### 

| 名称    | 包名             | 职责                                                                                |
| ----- | -------------- | --------------------------------------------------------------------------------- |
| 表示层   | presentation   | 负责向用户呈现信息并解释用户命令。                                                                 |
| 应用程序层 | application    | 定义软件应该执行的作业<br/>协调应用程序活动并将工作委派给领域层<br/>不包含任何复杂的业务逻辑，而是在将用户输入传递到领域层之前对用户输入进行的基本验证。 |
| 领域层   | domain         | 负责表示业务概念。<br/><br/>管理业务状态或委托给基础结构层<br/><br/>自包含，不依赖于任何其他层。领域层应与其他层很好地隔离。          |
| 基础结构层 | infrastructure | 提供通用的技术功能用于支持上层的应用。<br/><br/>处理API、持久性、网络等。<br/><br/>实现存储库接口并隐藏领域层的复杂性。           |

### 依赖关系

![](http://pub.silverfoxsoft.com/ddd%E4%BE%9D%E8%B5%96%E5%85%B3%E7%B3%BB.png)

## R2dbc操作mysql

目前有两种方式

### R2dbcEntityTemplate

略 和mybatisPlus用法差不多就不详细介绍了。

### ReactiveCrudRepository

### domain层

```java
@Table("entity_subject")
@Data
public class EntitySubject {
    @Id
    private Long id;
    private String name;
    private String fullname;
    private Long stageId;
}
```

依赖table 和 id 注解

### instructrue层

```java
public interface SubjectRepository extends ReactiveCrudRepository<EntitySubject,Long> {
}
```

### application 层

```java
@Override
    public Mono<EntitySubject> findById(Long id) {
        return subjectRepository.findById(id);
    }
```


