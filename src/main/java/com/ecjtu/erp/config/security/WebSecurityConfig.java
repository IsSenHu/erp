package com.ecjtu.erp.config.security;

import com.ecjtu.erp.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @Author: 胡森
 * @Description: spring security 配置，项目启动的时候自动配置好已设置的权限
 * @Date: Created in 0:54 2018/5/4
 * @Modified By: 胡森
 */
@Configuration
@EnableWebSecurity
/**
 * 开启security注解
 * */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    /**
     * 登录页面
     */
    private static final String LOGIN = "/login";
    /**
     * 认证接口
     */
    private static final String AUTHENTICATE = "/authenticate";
    /**
     * 主页
     */
    private static final String INDEX = "/index";

    /**
     * 静态资源
     */
    private static final String BOWER_COMPONENTS = "/bower_components/**";
    private static final String BUILD = "/build/**";
    private static final String CSS = "/css/**";
    private static final String DIST = "/dist/**";
    private static final String DOCUMENTATION = "/documentation/**";
    private static final String JS = "/js/**";
    private static final String PLUGINS = "/plugins/**";

    /**
     * cookie 私钥
     */
    private static final String KEY = "cookie-private-key";

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("权限初始化...");
        List<String> allUrls = userService.allUrls();
        log.info("所有的资源的数量为:{}", allUrls.size());
        allUrls.stream().forEach(url -> log.info("资源地址为:{}", url));
        http
                // 关闭csrf保护功能（跨域访问）
                .csrf().disable();
        if(CollectionUtils.isNotEmpty(allUrls)){
            int i = 0;
            for(String url : allUrls){
                if(i < allUrls.size() - 1){
                    http
                            .authorizeRequests()
                            .antMatchers(url).hasAuthority(url);
                }else {
                    http.authorizeRequests()
                            .antMatchers(url).hasAuthority(url)
                            .and()
                            .authorizeRequests()
                            //允许所有用户访问 登录页面和进行验证的连接
                            .antMatchers(LOGIN, AUTHENTICATE).permitAll()
                            //其他地址的访问均需要验证权限
                            .anyRequest().authenticated()
                            .and()
                            //指定登录页是
                            .formLogin().loginPage(LOGIN)
                            //登录成功后默认跳转的页面
                            .defaultSuccessUrl(INDEX)
                            .and()
                            //开启cookie保存用户数据
                            .rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7)
                            //设置cookie私钥
                            .key(KEY)
                            .and()
                            //登出成功后默认跳转到登录页面
                            .logout().logoutSuccessUrl(LOGIN).permitAll();
                }
                i++;
            }
        }else {
            http
                    .authorizeRequests()
                    //允许所有用户访问 登录页面和进行验证的连接
                    .antMatchers(LOGIN, AUTHENTICATE).permitAll()
                    //其他地址的访问均需要验证权限
                    .anyRequest().authenticated()
                    .and()
                    //指定登录页是
                    .formLogin().loginPage(LOGIN)
                    //登录成功后默认跳转的页面
                    .defaultSuccessUrl(INDEX)
                    .and()
                    //开启cookie保存用户数据
                    .rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7)
                    //设置cookie私钥
                    .key(KEY)
                    .and()
                    //登出成功后默认跳转到登录页面
                    .logout().logoutSuccessUrl(LOGIN);
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 不去拦截这些静态资源
         * */
        web.ignoring().antMatchers(BOWER_COMPONENTS, BUILD, CSS, DIST, DOCUMENTATION, JS, PLUGINS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
