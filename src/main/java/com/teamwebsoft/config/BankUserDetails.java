package com.teamwebsoft.config;

import com.teamwebsoft.entity.Customer;
import com.teamwebsoft.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class BankUserDetails implements UserDetailsService {
    @Autowired
    private static CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName,password=null;
        List<GrantedAuthority> authorities = null;
        List<Customer> customers = customerRepository.findByEmail(username);
        if(customers.size() == 0){
            throw new UsernameNotFoundException("User details not found for the user."+username);
        }
        else{
            userName = customers.get(0).getEmail();
            password = customers.get(0).getPwd();
            authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
        }
        return new User(userName,password,authorities);
    }
}
