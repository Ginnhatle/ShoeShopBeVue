package org.datn.app.core.service;

import org.datn.app.core.dto.ChangePasswordRequest;
import org.datn.app.core.dto.UserFindRequest;
import org.datn.app.core.entity.User;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface UserService extends ServiceCore<User,Long> {
    User findByEmail(String email);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
    User findByUsername(String username);
    Map<String,String> changePassword(ChangePasswordRequest request);
    ResponseEntity<String> uploadFile(MultipartFile file, Principal principal);
    List<User> findByUsernameOrEmail(UserFindRequest userFindRequest);
    Map<String,Object> sendCode(String email) throws Exception;
    Map<String,Object> resetPassword(ChangePasswordRequest model) throws Exception;
}
