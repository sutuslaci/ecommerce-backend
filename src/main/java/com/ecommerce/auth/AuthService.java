package com.ecommerce.auth;

import com.ecommerce.auth.models.AuthenticationRequest;
import com.ecommerce.auth.models.AuthenticationResponse;
import com.ecommerce.auth.models.RegistrationRequest;
import com.ecommerce.user.entities.User;
import com.ecommerce.user.repositories.UserRepository;
import com.ecommerce.user.enums.Role;
import com.ecommerce.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var accessToken = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder().accessToken(accessToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var accessToken = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder().accessToken(accessToken).build();
    }
}
