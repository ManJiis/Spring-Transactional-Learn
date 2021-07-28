package top.b0x0.txdemo.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import top.b0x0.txdemo.mapper.UserMapper;
import top.b0x0.txdemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("userService2Impl")
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(User user) {

//        UserServiceImpl o = (UserServiceImpl) AopContext.currentProxy();
//        int i = o.update2(user);

//        int i = userService.update2(user);

        int i = update2(user);
        int iw = 1 / 0;
        System.out.println("iw = " + iw);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
//    @Transactional(rollbackFor = Exception.class)
    public Integer update2(User user) {
        return userMapper.update(null, new UpdateWrapper<User>().eq("id", user.getId()).set("age", user.getAge()));

    }

}
