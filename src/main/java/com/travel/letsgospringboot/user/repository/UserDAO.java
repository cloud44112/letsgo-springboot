package com.travel.letsgospringboot.user.repository;

import com.travel.letsgospringboot.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    UserVO login(String userID, String password);

    boolean signUp(String userID, String email, String name, String password);

    boolean idCheck(String userID);

    boolean updatePassword(String userID, String email, String newPassword);

    String findUserIdByNameAndEmail(String name, String email);
}
