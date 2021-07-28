# Spring事务传播机制
1. REQUIRED(Propagation.REQUIRED) （Spring默认传播事务）

支持当前事务，如果没有事务会创建一个新的事务

2. SUPPORTS(Propagation.SUPPORTS)

支持当前事务，如果没有事务的话以非事务方式执行

3. MANDATORY(Propagation.MANDATORY)

支持当前事务，如果没有事务抛出异常

3. REQUIRES_NEW(Propagation.REQUIRES_NEW)

创建一个新的事务并挂起当前事务

4. NOT_SUPPORTED(Propagation.NOT_SUPPORTED)

以非事务方式执行，如果当前存在事务则将当前事务挂起

5.NEVER(Propagation.NEVER)

以非事务方式进行，如果存在事务则抛出异常

6. NESTED(TransactionDefinition.NESTED)

如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。


# 1. 问题一 (issue-one-)
method_a 方法调用 method_b, 想要让 method_b 方法的事务先提交，方法有两种：

1.1 (issue-1-1) **获取方法 method_a 的类的代理类**去调用 method_b (此时两个方法实现在同一类中)，然后修改 method_b 的事务传播机制为`Propagation.REQUIRES_NEW`

`UserServiceImpl`
```java
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer methodA(User user) {
        UserService userService = (UserServiceImpl) AopContext.currentProxy();
        int i = userService.methodB(user);
        int iw = 1 / 0;
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Integer methodB(User user) {
        return userMapper.update(null, new UpdateWrapper<User>().eq("id", user.getId()).set("age", user.getAge()));
    }

}
```
`Controller`
```java
@RestController
public class Controller {

    @Autowired
    UserService userService;

    @PostMapping("update")
    public Object updateUser(User user) {
        return userService.methodA(user);
    }

}
```
> 验证成功与否：如果user表年龄修改成功，说明 method_b 方法是一个新事务，执行完就会先提交，就说明方法可行

1.2 (issue-1-2) 让 method_a 和 method_b **方法实现写在不同实现类中**，修改 method_b 的事务传播机制为`Propagation.REQUIRES_NEW`
`UserServiceImpl`
```java
@Component
public class UserService1Impl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    @Qualifier("userService2Impl")
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer methodA(User user) {
        int i = userService.methodB(user);
        int iw = 1 / 0;
        return i;
    }

}
```
`UserService2Impl`
```java
@Component
public class UserService2Impl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Integer methodB(User user) {
        return userMapper.update(null, new UpdateWrapper<User>().eq("id", user.getId()).set("age", user.getAge()));
    }

}
```
`Controller`
```java
@RestController
public class Controller {

    @Autowired
    @Qualifier("userService1Impl")
    UserService userService;

    @PostMapping("update")
    public Object updateUser(User user) {
        return userService.methodA(user);
    }

}
```
> 验证成功与否：如果user表年龄修改成功，说明 method_b 方法是一个新事务，执行完就会先提交，就说明方法可行




