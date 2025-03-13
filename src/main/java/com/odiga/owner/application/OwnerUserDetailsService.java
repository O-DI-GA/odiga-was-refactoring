package com.odiga.owner.application;

import com.odiga.owner.dao.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerUserDetailsService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ownerRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 email 입니다."));
    }
}
