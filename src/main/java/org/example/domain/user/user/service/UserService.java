package org.example.domain.user.user.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.question.question.exception.DataNotFoundException;
import org.example.domain.user.user.repository.UserRepository;
import org.example.domain.user.user.entity.SiteUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password,boolean paid){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPaid(paid);
        userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        }else{
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public long count() {
        return userRepository.count();
    }
}
