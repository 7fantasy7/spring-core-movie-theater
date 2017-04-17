package ua.epam.spring.hometask.service.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        final ua.epam.spring.hometask.domain.User dbUser = userService.getUserByEmail(mail);
        if (dbUser == null) {
            throw new UsernameNotFoundException("User details not found with this email: " + mail);
        }
        String email = dbUser.getEmail();
        String password = dbUser.getPassword();
        String roles = dbUser.getRoles();

        String encodedPassword = passwordEncoder.encode(password);

//        List<SimpleGrantedAuthority> authList = getAuthorities(roles);
        User user = new User().setEmail(email).setPassword(encodedPassword).setRoles(roles);
//                .setAuthorities(authList);
        return user;
    }

    // Authorities, don't need it
//    private List<SimpleGrantedAuthority> getAuthorities(String roleString) {
//        List<SimpleGrantedAuthority> authList = new ArrayList<>();
//        Stream.of(roleString.split(", ")).forEach(role -> authList.add(new SimpleGrantedAuthority(role)));
//        return authList;
//    }

}