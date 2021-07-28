package top.b0x0.spring.transactional.issue11.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.aop.framework.AopContext;
import top.b0x0.spring.transactional.common.mapper.UserMapper;
import top.b0x0.spring.transactional.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author TANG
 * @since 2021-07-28
 * @since JDK1.8
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 验证成功与否：如果user表年龄修改成功，说明 method_b 方法是一个新事务，执行完就会先提交，就说明方法可行
     *
     * @param user /
     * @return /
     */
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
