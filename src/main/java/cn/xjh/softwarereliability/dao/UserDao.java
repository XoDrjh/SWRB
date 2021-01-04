package cn.xjh.softwarereliability.dao;

import cn.xjh.softwarereliability.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    @Select("select * from user where username=#{username}")
    User getUser(String username);
}
