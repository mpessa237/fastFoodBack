package com.herve.fastfood.securities;

import com.herve.fastfood.repositories.UtilisateurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepo utilisateurRepo;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return this.utilisateurRepo.findByEmail(userEmail)
                .orElseThrow(()->new RuntimeException("user not found!!"));
    }
}
