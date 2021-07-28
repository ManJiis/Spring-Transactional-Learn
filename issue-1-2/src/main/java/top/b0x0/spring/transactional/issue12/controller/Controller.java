package top.b0x0.spring.transactional.issue12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.b0x0.spring.transactional.common.pojo.User;
import top.b0x0.spring.transactional.issue12.service.UserService;

/**
 * @author TANG
 * @since 2021-07-28
 * @since JDK1.8
 */
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
