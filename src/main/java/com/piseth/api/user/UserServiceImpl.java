package com.piseth.api.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piseth.api.user.web.UserDto;
import com.piseth.api.user.web.UserPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapStruct userMapStruct;

    @Override
    public PageInfo<UserDto> findAllUsers(Integer page, Integer limit) {
        PageInfo<User> userDtoPageInfo = PageHelper.startPage(page, limit)
                .doSelectPageInfo(userRepository::select);
        return userMapStruct.toUserDtoList(userDtoPageInfo);
    }

    @Override
    public UserDto findUserById(Integer id) {
        User user = userRepository.selectById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User With %s Is Not Found!!!", id)
                ));
        return userMapStruct.toUserDto(user);
    }

    @Override
    public UserDto addNewUser(UserPostDto userPostDto) {
        User user = userMapStruct.fromUserPostDto(userPostDto);
        user.setIsDeleted(false);
        userRepository.insert(user);
        return this.findUserById(user.getId());
    }

    @Override
    public UserDto updateUserById(Integer id, UserPostDto userPostDto) {
        Boolean isFound = userRepository.isExist(id);
        if (isFound){
            User user = userMapStruct.fromUserPostDto(userPostDto);
            user.setId(id);
            user.setIsDeleted(false);
            userRepository.update(user);
            return this.findUserById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User With %s Is Not Found!!!", id));
    }

    @Override
    public Integer deleteUserById(Integer id, Boolean status) {
        Boolean isFound = userRepository.isExist(id);
        if (isFound){
            userRepository.delete(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User With %s Is Not Found!!!", id));
    }

}
