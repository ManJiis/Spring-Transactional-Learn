package top.b0x0.spring.transactional.issue12.service;


import top.b0x0.spring.transactional.common.pojo.User;

/**
 * @author TANG
 * @since 2021-07-28
 * @since JDK1.8
 */
public interface UserService {
    Integer methodA(User user);

    Integer methodB(User user);
}
