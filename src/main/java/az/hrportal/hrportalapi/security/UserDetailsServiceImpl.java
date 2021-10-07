package az.hrportal.hrportalapi.security;

import az.hrportal.hrportalapi.constant.Role;
import az.hrportal.hrportalapi.domain.User;
import az.hrportal.hrportalapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username));
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role role : user.getRoles())
            roles.add(new SimpleGrantedAuthority(role.toString()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), roles);
    }
}
