package com.hfzq.demodb.repository;


import com.hfzq.demodb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description UserDAO
 * @Author Zephyr Lin
 * @Date 2022/3/1 10:34
 **/
@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
