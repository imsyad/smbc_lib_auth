package com.smbc.library.auth_service.service.impl;

import java.util.HashMap;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smbc.library.auth_service.dto.FindMemberDto;
import com.smbc.library.auth_service.dto.MemberRegisterDto;
import com.smbc.library.auth_service.dto.ResponseDto;
import com.smbc.library.auth_service.dto.UserLoginDto;
import com.smbc.library.auth_service.dto.UserRegisterDto;
import com.smbc.library.auth_service.exception.InternalServerErrorException;
import com.smbc.library.auth_service.model.Users;
import com.smbc.library.auth_service.repository.UsersRepository;
import com.smbc.library.auth_service.service.iservice.UserService;
import com.smbc.library.auth_service.utils.JwtUtil;
import com.smbc.library.auth_service.utils.ResponseUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${smbc.properties.external.member_service.url}")
    private String MEMBER_SERVICE_URL;

    private final UsersRepository usersRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public ResponseDto<?> register(UserRegisterDto userData) {
        try {
            Users existingUser = usersRepository.findOneByUsername(userData.getUsername());
            if (existingUser != null) {
                return ResponseUtil.failed(HttpStatus.BAD_REQUEST.value(),
                        "The username is not available. Please use another username!");
            }

            Users model = Users.builder().username(userData.getUsername()).password(userData.getPassword()).build();
            Users saved = usersRepository.save(model);

            if (saved == null) {
                throw new InternalException("Failed to register a new user.");
            }

            MemberRegisterDto memberRegisterRequest = MemberRegisterDto.builder().userId(saved.getId())
                    .fullname(userData.getFullname()).build();
            HttpEntity<MemberRegisterDto> requestHttpEntity = new HttpEntity<>(memberRegisterRequest);
            ParameterizedTypeReference<ResponseDto<?>> responseType = new ParameterizedTypeReference<ResponseDto<?>>() {
            };
            var response = restTemplate.exchange(MEMBER_SERVICE_URL.concat("/member/register"), HttpMethod.POST,
                    requestHttpEntity, responseType);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody().getStatus() != HttpStatus.OK.value()) {
                throw new InternalServerErrorException("Failed to register member data");
            }

            return ResponseUtil.success(HttpStatus.CREATED.value(), "Success to register a new user", null);
        } catch (Exception e) {
            log.error("Failed to register user", e);
            throw e;
        }
    }

    @Override
    public ResponseDto<?> login(UserLoginDto userLogin) {
        try {
            Users user = usersRepository.findOneByUsernameAndPassword(userLogin.getUsername(), userLogin.getPassword());
            if (user == null) {
                return ResponseUtil.failed(HttpStatus.BAD_REQUEST.value(),
                        "The username or password is wrong!");
            }

            ParameterizedTypeReference<ResponseDto<FindMemberDto>> responseType = new ParameterizedTypeReference<ResponseDto<FindMemberDto>>() {
            };

            String memberServiceUrlWithParameter = MEMBER_SERVICE_URL.concat("/member?userId=" + user.getId());

            var response = restTemplate.exchange(memberServiceUrlWithParameter, HttpMethod.GET,
                    null, responseType);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody().getStatus() != HttpStatus.OK.value()) {
                throw new InternalServerErrorException("Failed to register member data");
            }

            var member = response.getBody().getData();

            HashMap<String, Object> claim = new HashMap<>();
            claim.put("id", user.getId());
            claim.put("username", user.getUsername());
            claim.put("memberId", member.getMemberId());
            claim.put("fullname", member.getFullname());

            String token = jwtUtil.generateToken(user.getUsername(), claim);
            return ResponseUtil.success(HttpStatus.OK.value(), "Succses login", token);
        } catch (Exception e) {
            throw e;
        }

    }
}
