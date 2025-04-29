// member/controller/AuthController.java

package com.khu.cloud.diary.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khu.cloud.diary.member.dto.AuthRequest;
import com.khu.cloud.diary.member.dto.AuthResponse;
import com.khu.cloud.diary.member.dto.SignupRequest;
import com.khu.cloud.diary.member.dto.SignupResponse;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // @PostMapping("/signup")
    // public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
    //     authService.signup(request);
    //     return ResponseEntity.ok("Signup successful");
    // }

    @PostMapping("/signup")
    @Operation(summary = "user signup", description = "create a new user")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        Member createdMember = authService.signup(signupRequest);
        SignupResponse response = new SignupResponse(createdMember.getUserId(), createdMember.getEmail(), createdMember.getNickname());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "user login", description = "authenticate user & generate JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @PostMapping("/logout")
    @Operation(summary = "user logout", description = "logout")
    public ResponseEntity<String> logout() {
        // [client] removeItem("jwtToken") 등으로 token 삭제
        return ResponseEntity.ok("Logged out successfully");
    }
}