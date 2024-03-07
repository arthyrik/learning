package com.epam.learn.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

import static com.epam.learn.security.AuthenticationFailureHandler.LAST_USER_NAME;
import static java.util.stream.Collectors.toMap;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final LoginAttemptService loginAttemptService;
    private final UserRepository userRepository;

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", defaultValue = "false") boolean loginError,
                        Model model, HttpSession httpSession) {
        String username = (String) httpSession.getAttribute(LAST_USER_NAME);

        if (loginError && username != null) {
            if (loginAttemptService.isBlocked(username)) {
                model.addAttribute("accountLocked", Boolean.TRUE);
            }
        }
        return "login";
    }

    @GetMapping("/blocked-users")
    public String blockedUsers(Model model) {
        var blockedUsers = userRepository.findAll().stream()
                .filter(user -> loginAttemptService.isBlocked(user.getUsername()))
                .collect(toMap(user -> user, user -> loginAttemptService.getCachedValue(user.getUsername())));

        model.addAttribute("blockedUsers", blockedUsers);

        return "blockedUsers";
    }

}
