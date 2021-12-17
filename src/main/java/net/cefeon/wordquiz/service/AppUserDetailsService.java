package net.cefeon.wordquiz.service;

import net.cefeon.wordquiz.model.WordQuizUser;
import net.cefeon.wordquiz.nonmodel.AppUserDetails;
import net.cefeon.wordquiz.repository.WordQuizUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    final WordQuizUserRepository wordQuizUserRepository;

    public AppUserDetailsService(WordQuizUserRepository wordQuizUserRepository) {
        this.wordQuizUserRepository = wordQuizUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<WordQuizUser> user = wordQuizUserRepository.findByUserName(userName);
        if (user.isPresent()) {
            return user.map(AppUserDetails::new).get();
        } else throw new UsernameNotFoundException("Not found: " + userName);
    }
}