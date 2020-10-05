package com.jungjoongi.debugapp.config.auth.WebLogin;

import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.domain.auth.User;
import com.jungjoongi.debugapp.domain.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 인증 하는 부분
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("@@@email "+email);
        Optional<User> userEntity = userRepository.findByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = userEntity.get();
        System.out.println("@@@user "+ObjectHelper.convertObjectToString(user));
        authorities.add(new SimpleGrantedAuthority(user.getRole().getKey()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

//    /**
//     * 권한 받아오는 부분
//     * @param memberInfo
//     * @return
//     */
//    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        String[] userRoles =  convert(memberInfo.getMemberAuthoritiesMappingList());
//        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
//        return authorities;
//    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().toString());
        return authorities;
    }


//    /**
//     * 실제 권한 매핑 함수
//     * @param list
//     * @return
//     */
//    public String[] convert(List<MemberAuthoritiesMapping> list)
//    {
//        String[] arrayOfString = new String[list.size()];
//
//        int index = 0;
//        for (MemberAuthoritiesMapping memberAuthoritiesMapping : list) {
//            arrayOfString[index++] = memberAuthoritiesMapping.getMemberAuthoritiesCode().getAuthority();
//        }
//        return arrayOfString;
//    }
}
