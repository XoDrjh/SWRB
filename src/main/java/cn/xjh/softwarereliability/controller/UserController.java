package cn.xjh.softwarereliability.controller;

import cn.xjh.softwarereliability.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/showUser/{username}")
    public String showUser(@PathVariable String username) {
        return userService.getUser(username).toString();
    }
}
