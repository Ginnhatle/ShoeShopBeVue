package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ChangePasswordRequest;
import org.datn.app.core.dto.UserFindRequest;
import org.datn.app.core.entity.User;
import org.datn.app.core.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid User user, BindingResult result) throws NoSuchAlgorithmException, MessagingException {
        User user1 = userService.findByUsername(user.getUsername());
        User user2 = userService.findByEmail(user.getEmail());
        List<String> error = new ArrayList<>();
        if (result.hasErrors()) {
            // Lỗi trong quá trình binding
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            // remove null
            return ResponseEntity.badRequest().body(errorMessages);
        }
        if (user1 != null) {
            error.add("Username đã tồn tại");
        }
        if (user2 != null) {
            error.add("Email đã tồn tại");
        }
        if (!user.getPassword().matches("^[a-zA-Z0-9_]{6,}$")) {
            error.add("Mật khẩu phải có ít nhất 6 ký tự và không chứa ký tự đặc biệt");
        }
        if (error.size() > 0) {
            return ResponseEntity.badRequest().body(error);
        }
        //MailSender.sendCode(user.getEmail(), user.getFullName());
        return ResponseEntity.ok(userService.doInsert(user));
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.findAll();
    }

    @PutMapping("/update/{id}")
    public User update(@RequestBody User user, @PathVariable Long id) {
        return userService.doUpdateById(user, id);
    }

    @DeleteMapping("/delete/{id}")
    public User delete(@PathVariable Long id) {
        return userService.doDeleteById(id);
    }

    @GetMapping("/page/{page}/{size}")
    public Page<User> page(@PathVariable Integer page, @PathVariable Integer size) {
        return userService.findAll(page, size);
    }

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/changePassword")
    public Map<String, String> changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        return userService.uploadFile(file, principal);
    }

    @PostMapping("/findByUsernameOrEmail")
    public List<User> findByUsernameOrEmail(@RequestBody UserFindRequest request) {
        return userService.findByUsernameOrEmail(request);
    }

    @PostMapping("/sendCode")
    public Map<String, Object> sendCode(@RequestBody ChangePasswordRequest request) throws Exception {
        return userService.sendCode(request.getEmail());
    }

    @PostMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestBody ChangePasswordRequest model) throws Exception {
        return userService.resetPassword(model);
    }
}
