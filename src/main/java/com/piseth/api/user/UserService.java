package com.piseth.api.user;

import com.github.pagehelper.PageInfo;
import com.piseth.api.user.web.UserDto;
import com.piseth.api.user.web.UserPostDto;

public interface UserService {
    /**
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo<UserDto> findAllUsers(Integer page, Integer limit);

    /**
     *
     * @param id
     * @return
     */
    UserDto findUserById(Integer id);
    /**
     *
     * @param userPostDto
     * @return
     */
    UserDto addNewUser(UserPostDto userPostDto);

    /**
     *
     * @param id
     * @param user
     * @return
     */
    UserDto updateUserById(Integer id, UserPostDto userPostDto);
    Integer deleteUserById(Integer id, Boolean status);
}
