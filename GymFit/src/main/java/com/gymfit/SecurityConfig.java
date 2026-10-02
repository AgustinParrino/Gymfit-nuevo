package com.gymfit;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration public class SecurityConfig {
 @Bean PasswordEncoder encoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService users(AdminRepository admins){return username -> admins.findById(username).map(a->User.withUsername(a.username).password(a.passwordHash).roles("ADMIN").build()).orElseThrow(()->new UsernameNotFoundException("Usuario desconocido"));}
 @Bean SecurityFilterChain security(HttpSecurity http)throws Exception {return http.authorizeHttpRequests(a->a.requestMatchers("/login","/style.css","/app.js","/favicon.svg","/error").permitAll().anyRequest().hasRole("ADMIN")).formLogin(f->f.loginPage("/login").defaultSuccessUrl("/",true).permitAll()).logout(l->l.logoutSuccessUrl("/login?logout")).build();}
}
