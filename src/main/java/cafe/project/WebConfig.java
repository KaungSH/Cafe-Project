package cafe.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cafe.project.common.interceptors.AdminInterceptor;
import cafe.project.common.interceptors.LoginInterceptor;
import cafe.project.common.interceptors.ManagerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private final LoginInterceptor login;
	private final AdminInterceptor admin;
	private final ManagerInterceptor superadmin;
	
	public WebConfig(LoginInterceptor login, AdminInterceptor admin, ManagerInterceptor superadmin) {
		this.login = login;
		this.admin = admin;
		this.superadmin = superadmin;
	}


	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Maps web requests like http://localhost:8080/images/filename.jpg 
        // to your local directory D:/JWD (Java Web Development)/Dev/sevletprojects/posts_img/
		
		String userHome = System.getProperty("user.home");
		String uploadPath = "file:" + userHome + "/Downloads/Cafe Project Images/";
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
	
	  @Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(login).addPathPatterns("/**").excludePathPatterns("/login", "/signup", "/css/**", "/js/**", "/images/**", "/fonts/**", "/favicon.ico");
			
			registry.addInterceptor(superadmin).addPathPatterns("/manager/**");
			
			registry.addInterceptor(admin).addPathPatterns("/admin/**");
		}
}