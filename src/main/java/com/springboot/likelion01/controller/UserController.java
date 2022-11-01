package com.springboot.likelion01.controller;

/*
POST /user - user등록하는 기능
        DELETE /user/{id} - 1명 유저 지우는 기능
        DELETE /user/all - 전체 유저 지우는 기능
        GET ALL
        GET /user/{id}
*/

import com.springboot.likelion01.dao.UserDao;
import com.springboot.likelion01.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class UserController {

    private UserDao userDao;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        try {
            User user = this.userDao.findById(id);
            return ResponseEntity.ok()
                    .body(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
