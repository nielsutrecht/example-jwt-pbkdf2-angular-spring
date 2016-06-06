package com.nibado.example.jwtpbkdf2;

import com.nibado.example.jwtpbkdf2.filter.JwtFilter;
import com.nibado.example.jwtpbkdf2.security.HashUtil;
import com.nibado.example.jwtpbkdf2.security.JwtUtil;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class ApplicationConfiguration {

    private String jwtKey = "SUPERSECRET";
    private String hashAlgorithm = "PBKDF2WithHmacSHA1";
    private int hashIterations = 1000;
    private int saltBytes = 24;
    private int hashBytes = 24;
    private int jwtValidForDays = 7;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtKey, Duration.ofDays(jwtValidForDays));
    }

    @Bean
    public HashUtil hashUtil() {
        return new HashUtil(hashAlgorithm, saltBytes, hashBytes, hashIterations);
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/todo/*", "/user/me");

        return registrationBean;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./data");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }

    /**
     * Enable Cross Origin requests globally.
     *
     * @return a configured CorsRegistry wrapped in a WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
