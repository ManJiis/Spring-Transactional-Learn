package top.b0x0.spring.transactional.issue12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.b0x0.spring.transactional.common.mapper.UserMapper;
import top.b0x0.spring.transactional.common.pojo.User;

/**
 * @author TANG
 * @since 2021-07-28
 * @since JDK1.8
 */
@Component
public class UserService1Impl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    @Qualifier("userService2Impl")
    UserService userService;

    /**
     * 验证成功与否：如果user表年龄修改成功，说明 method_b 方法是一个新事务，执行完就会先提交，就说明方法可行
     *
     * @param user /
     * @return /
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer methodA(User user) {
        int i = userService.methodB(user);
        int iw = 1 / 0;
        return i;
    }

    @Override
    public Integer methodB(User user) {
        return null;
    }

}
