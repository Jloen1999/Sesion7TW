package es.unex.cum.tw.services;

import es.unex.cum.tw.models.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface LoginService {
    public Optional<User> authenticate(HttpServletRequest request);
}
