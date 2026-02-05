package com.poemorder.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.CacheControlConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Хороший дефолт: Argon2.
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException(
                    "app.admin.password is empty. Set it via env or application-local.properties");
        }

        UserDetails admin = User.builder()
                .username(adminUsername)
                .password(encoder.encode(adminPassword))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // статика
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()

                        // публичные страницы
                        .requestMatchers("/", "/order", "/reviews", "/contacts").permitAll()
                        // публичные сабмиты (если есть)
                        .requestMatchers(HttpMethod.POST, "/order", "/reviews").permitAll()

                        // логин/логаут админа
                        .requestMatchers("/admin/login", "/admin/logout").permitAll()

                        // вся админка
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // остальное
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")      // POST сюда
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureUrl("/admin/login?error=1")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout=1")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                // Сессия: защита от session fixation + одна сессия
                .sessionManagement(sm -> sm
                        .sessionFixation(sf -> sf.migrateSession())
                        .maximumSessions(1)
                )

                // Заголовки + запрет кеша для админки (важно!)
                .headers(headers -> headers
                        .frameOptions(fo -> fo.deny())
                        .contentTypeOptions(Customizer.withDefaults())
                        .cacheControl(CacheControlConfig::disable) // отключим общий, ниже добавим no-store через свой фильтр/конфиг если хочешь
                );

        // CSRF НЕ отключаем.
        return http.build();
    }
}
