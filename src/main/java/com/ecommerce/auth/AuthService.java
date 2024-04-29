package com.ecommerce.auth;

import com.ecommerce.auth.models.AuthenticationRequest;
import com.ecommerce.auth.models.AuthenticationResponse;
import com.ecommerce.auth.models.RegistrationRequest;
import com.ecommerce.user.entities.User;
import com.ecommerce.user.repositories.UserRepository;
import com.ecommerce.user.enums.Role;
import com.ecommerce.auth.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostConstruct
    public void init() {
        var user = User.builder()
                .name("Laszlo")
                .email("admin@test.com")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
    }

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
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad email or password");
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder().accessToken(accessToken).build();
    }
}
