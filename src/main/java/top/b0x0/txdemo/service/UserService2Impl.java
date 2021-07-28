package top.b0x0.txdemo.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import top.b0x0.txdemo.mapper.UserMapper;
import top.b0x0.txdemo.pojo.User;
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
public class UserService2Impl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(User user) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
//    @Transactional(rollbackFor = Exception.class)
    public Integer update2(User user) {
        return userMapper.update(null, new UpdateWrapper<User>().eq("id", user.getId()).set("age", user.getAge()));

    }

}
