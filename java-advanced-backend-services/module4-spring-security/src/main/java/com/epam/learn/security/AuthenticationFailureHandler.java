package com.epam.learn.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String LAST_USER_NAME = "LAST_USER_NAME";

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        setDefaultFailureUrl("/login?error=true");

        var username = request.getParameter("username");
        request.getSession().setAttribute(LAST_USER_NAME, username);

        if (userRepository.existsByUsername(username)) {
            loginAttemptService.loginFailed(username);
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
