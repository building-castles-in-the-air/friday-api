package com.github.friday.app.config.shiro;

import com.github.friday.app.config.redis.RedisProperties;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置类
 *
 * @author Administrator
 *
 */
@Configuration
@Component
public class ShiroConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisProperties redisProperties;

	/**
	 * 保证跨域filter 在shiroFilter 之前注册
	 *
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
		corsConfiguration.addAllowedHeader("*"); // 2允许任何头
		corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration); // 4
		return new CorsFilter(source);
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);

		// 配置访问权限 anon-放行，authc-需要校验
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("**/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources", "anon");
		filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");

		// 表示需要认证才可以访问
//		filterChainDefinitionMap.put("/*", "authc");
		filterChainDefinitionMap.put("/**", "authc");
//		filterChainDefinitionMap.put("/*.*", "authc");

		// 未授权界面;
		bean.setUnauthorizedUrl("/401");
		// 配置登录的url和登录成功的url
		bean.setLoginUrl("/401");

		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
		filterMap.put("authc", new JWTFilter());
		bean.setFilters(filterMap);
		return bean;
	}

	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setCacheManager(redisCacheManager());
		manager.setRealm(getRealm());
//		manager.setSessionManager(sessionManager()); // 启用session时，解开这行，注释下面几行
		/*
		 * 关闭shiro自带的session，详情见文档
		 * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
		 */
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		manager.setSubjectDAO(subjectDAO);
		return manager;
	}

	@Bean
	public AuthRealm getRealm() {
		AuthRealm authRealm = new AuthRealm();
		return authRealm;
	}

	/**
	 * cacheManager 缓存 redis实现 使用的是shiro-redis开源插件
	 *
	 * @return
	 */
	public RedisCacheManager redisCacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	 * 配置shiro redisManager 使用的是shiro-redis开源插件
	 *
	 * @return
	 */
	@Bean
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(redisProperties.getHost());
		return redisManager;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}

	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}

}
