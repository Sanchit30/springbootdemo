package com.anaadih.aclassdeal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.BeanIds;

import com.anaadih.aclassdeal.Service.CustomOAuth2UserService;
import com.anaadih.aclassdeal.Service.CustomUserDetailsService;
import com.anaadih.aclassdeal.util.OAuth2AuthenticationFailureHandler;
import com.anaadih.aclassdeal.util.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig	extends WebSecurityConfigurerAdapter  {

	@Autowired
    CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	    
	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	   @Bean
	    public TokenAuthenticationFilter tokenAuthenticationFilter() {
	        return new TokenAuthenticationFilter();
	    }
	    
	    @Bean
	    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
	        return new HttpCookieOAuth2AuthorizationRequestRepository();
	    }

	
	   @Override
	    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	        authenticationManagerBuilder
	                .userDetailsService(customUserDetailsService)
	                .passwordEncoder(passwordEncoder());
	    }
	 
	   @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	  @Bean(BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
    
	
	 
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .cors()
	                .and()
	                .sessionManagement()
	                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                    .and()
	                .csrf()
	                    .disable()
	                .formLogin()
	                    .disable()
	                .httpBasic()
	                    .disable()
	                .exceptionHandling()
	                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
	                    .and()
	                .authorizeRequests()
	                    .antMatchers("/","/api/getAllCategorieswithCount","/api/getAllDetailsOfProduct","/api/getAllProductsOfCategory",
	                    		"/api/getAllProductsOfSubCategory",	"/api/getAllCategorieswithSubcategory",
                    		"/api/getAllProducts","/api/downloadFile/*","/api/getAllCategories","/api/searchProduct",
                    		"/api/getAllSubCategoryAndAttributeOfCategoryCode","/api/getAllCategoryandSubCategory",
                    		"/api/getAllAttributeOfSubCategory","/api/getAllSubCategoryOfCategoryCode",
	                        "/error",
	                        "/socket/*",
	                        "/topic/*",
	                        "/favicon.ico",
	                        "/**/*.png",
	                        "/**/*.gif",
	                        "/**/*.svg",
	                        "/**/*.jpg",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js"
	                        )
	                        .permitAll()
	                    .antMatchers("aclassdeal/api/auth/**", "/api/oauth2/**","/api/**")
	                        .permitAll()
	                    .anyRequest()
	                        .authenticated()
	                    .and()
	                .oauth2Login()
	                    .authorizationEndpoint()
	                        .baseUri("/oauth2/authorize")
	                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
	                        .and()
	                    .redirectionEndpoint()
	                        .baseUri("/oauth2/callback/*")
	                        .and()
	                    .userInfoEndpoint()
	                        .userService(customOAuth2UserService)
	                        .and()
	                    .successHandler(oAuth2AuthenticationSuccessHandler)
	                    .failureHandler(oAuth2AuthenticationFailureHandler);

	        // Add our custom Token based authentication filter
	        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
	 
 //Till now we have enable every api
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//    http
//            .cors()
//                .and()
//            .csrf()
//                .disable()
//            .exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler)
//                .and()
//            .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//            .authorizeRequests()
//                .antMatchers("/","/api/getAllCategorieswithCount","/api/getAllDetailsOfProduct",
//            		"/api/getAllProducts","/api/downloadFile/*","/api/getAllCategories",
//            		"/api/getAllSubCategoryAndAttributeOfCategoryCode",
//                    "/favicon.ico",
//                    "/*/.png",
//                    "/*/.gif",
//                    "/*/.svg",
//                    "/*/.jpg",
//                    "/*/.html",
//                    "/*/.css",
//                    "/*/.js")
//                    .permitAll()
//                .antMatchers("/api/auth/**")
//                    .permitAll()
//                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
//                    .permitAll()
//                .antMatchers(HttpMethod.GET,  "/api/users/**")
//                    .permitAll()
//                .anyRequest()
//                    .authenticated();
//
//    // Add our custom JWT security filter
//    http.addFilterBefore((Filter) jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//}


	 
}
