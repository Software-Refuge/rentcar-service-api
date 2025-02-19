package uz.carapp.rentcarapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.carapp.rentcarapp.domain.Authority;
import uz.carapp.rentcarapp.domain.User;
import uz.carapp.rentcarapp.domain.enumeration.GenderEnum;
import uz.carapp.rentcarapp.repository.AuthorityRepository;
import uz.carapp.rentcarapp.repository.UserRepository;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component("InitialDataLoader")
public class InitialDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(authorityRepository.count() == 0) {
            authorityRepository.save(new Authority(AuthoritiesConstants.ADMIN));
            authorityRepository.save(new Authority(AuthoritiesConstants.CONTENT_MANAGER));
            authorityRepository.save(new Authority(AuthoritiesConstants.SALE_MANAGER));
            authorityRepository.save(new Authority(AuthoritiesConstants.USER));
        }

        // On StartUp add one user for managing.
        // 1. login: admin  password: admin [ADMIN]
        if(userRepository.count() == 0) {
            //to create admin
            Set<Authority> adminAuthorities = new HashSet<>(Arrays.asList(authorityRepository.findByName(AuthoritiesConstants.ADMIN)));
            User adminUser = new User();
            adminUser.setLastName("Admin");
            adminUser.setFirstName("Admin");
            adminUser.setLogin("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("12345678"));
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPhone("+998991234567");
            adminUser.setStatus(true);
            adminUser.setActivated(true);
            adminUser.setGender(GenderEnum.MALE);
            adminUser.setAuthorities(adminAuthorities);
            adminUser.setCreatedBy(Constants.SYSTEM);
            userRepository.save(adminUser);
        }
    }
}
