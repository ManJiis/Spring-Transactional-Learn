# 1. 需求一
method_a 方法调用 method_b, 想要让 method_b 方法的事务先提交，方法有两种：
## 1.1 获取方法 method_a 的类的代理类去调用 method_b (此时两个方法实现在同一类中)，然后修改 method_b 的事务传播机制为`Propagation.REQUIRES_NEW`

`UserServiceImpl`
```java
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    @Qualifier("userService2Impl")
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(User user) {
        UserServiceImpl o = (UserServiceImpl) AopContext.currentProxy();
        int i = o.update2(user);
        int iw = 1 / 0;
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Integer update2(User user) {
        return userMapper.update(null, new UpdateWrapper<User>().eq("id", user.getId()).set("age", user.getAge()));
    }

}
```
`Controller`
```java
@RestController
public class Controller {

    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;

    @PostMapping("update")
    public Object updateUser(User user) {
        return userService.update(user);
    }

}
```
> 验证成功与否：如果user表年龄修改成功，说明 method_b 方法是一个新事务，执行完就会先提交，就说明成功实现该需求

## 1.2 让 method_a 和 method_b 方法实现写在不同实现类中，修改 method_b 的事务传播机制为`Propagation.REQUIRES_NEW`
`UserServiceImpl`
```java
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    @Qualifier("userService2Impl")
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(User user) {
        int i = userService.update2(user);
        int iw = 1 / 0;
        return i;
    }
}
```
`UserService2Impl`





