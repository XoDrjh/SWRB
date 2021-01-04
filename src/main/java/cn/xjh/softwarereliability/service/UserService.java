package cn.xjh.softwarereliability.service;

import cn.xjh.softwarereliability.dao.UserDao;
import cn.xjh.softwarereliability.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getUser(String username) {
        return userDao.getUser(username);
    }
}
