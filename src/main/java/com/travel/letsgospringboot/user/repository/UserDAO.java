package com.travel.letsgospringboot.user.repository;

import com.travel.letsgospringboot.user.vo.UserVO;

public interface UserDAO {
    UserVO login(String userID, String password);

    boolean signup(String userID, String email, String name, String password);

    boolean idcheck(String userID);

    boolean updatePassword(String userID, String email, String newPassword);

    String findUserIdByNameAndEmail(String name, String email);
}
