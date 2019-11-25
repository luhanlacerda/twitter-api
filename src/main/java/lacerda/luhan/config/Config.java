package lacerda.luhan.config;

import static org.springframework.web.cors.CorsConfiguration.ALL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
//@Configuration
//@EnableWebSecurity
//@ComponentScan
public class Config extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(ALL).allowedMethods(ALL).allowedHeaders(ALL)
						.allowCredentials(true);
			}
		};
	}
}
