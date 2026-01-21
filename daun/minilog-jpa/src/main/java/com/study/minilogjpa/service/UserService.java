package com.study.minilogjpa.service;

import com.study.minilogjpa.dto.UserRequestDto;
import com.study.minilogjpa.dto.UserResponseDto;
import com.study.minilogjpa.entity.Role;
import com.study.minilogjpa.entity.User;
import com.study.minilogjpa.exception.NotAuthorizedException;
import com.study.minilogjpa.exception.UserNotFoundException;
import com.study.minilogjpa.repository.UserRepository;
import com.study.minilogjpa.security.MinilogUserDetails;
import com.study.minilogjpa.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(EntityDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserResponseDto> getUserById(Long userId) {
        return userRepository.findById(userId).map(EntityDtoMapper::toDto);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_AUTHOR);

        if (userRequestDto.getUsername().equals("admin")) {
            roles.add(Role.ROLE_ADMIN);
        }

        User savedUser =
                userRepository.save(
                        User.builder()
                                .username(userRequestDto.getUsername())
                                .password(userRequestDto.getPassword())
                                .roles(roles)
                                .build());

        return EntityDtoMapper.toDto(savedUser);
    }

    public UserResponseDto updateUser(
            MinilogUserDetails userdetails,
            Long userId,
            UserRequestDto userRequestDto) {

        if (!userdetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.ROLE_ADMIN.name()))
                && !userdetails.getId().equals(userId)) {
            throw new NotAuthorizedException("권한이 없습니다.");
        }

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () ->
                                        new UserNotFoundException(
                                                String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());

        User updatedUser = userRepository.save(user);
        return EntityDtoMapper.toDto(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user =
                userRepository.findById(userId)
                        .orElseThrow(
                                () ->
                                        new UserNotFoundException(
                                                String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));
        userRepository.deleteById(user.getId());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(EntityDtoMapper::toDto)
                .orElseThrow(
                        () ->
                                new UserNotFoundException(
                                        String.format("해당 이름(%s)을 가진 사용자를 찾을 수 없습니다.", username)));
    }
}
