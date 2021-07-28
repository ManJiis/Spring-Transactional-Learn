package top.b0x0.spring.transactional.issue12.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService2Impl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Integer methodA(User user) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Integer methodB(User user) {
        return userMapper.update(null, new UpdateWrapper<User>().eq("id", user.getId()).set("age", user.getAge()));
    }

}
