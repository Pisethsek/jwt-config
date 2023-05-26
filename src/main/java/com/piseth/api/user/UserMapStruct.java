package com.piseth.api.user;

import com.github.pagehelper.PageInfo;
import com.piseth.api.auth.web.RegisterDto;
import com.piseth.api.user.web.UserDto;
import com.piseth.api.user.web.UserPostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {//user

    //auth mapper
    PageInfo<UserDto> toUserDtoList(PageInfo<User> userPageInfo);

    UserDto toUserDto(User user);

    User fromUserPostDto(UserPostDto userPostDto);

    //auth mapper
    User fromRegisterDto(RegisterDto registerDto);

}
