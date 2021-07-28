package top.b0x0.txdemo.controller;

import top.b0x0.txdemo.pojo.User;
import top.b0x0.txdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TANG
 * @since 2021-07-28
 * @since JDK1.8
 */
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
