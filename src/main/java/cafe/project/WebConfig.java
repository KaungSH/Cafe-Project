package cafe.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cafe.project.common.interceptors.AdminInterceptor;
import cafe.project.common.interceptors.LoginInterceptor;
import cafe.project.common.interceptors.ManagerInterceptor;
import cafe.project.common.interceptors.ManagerOnlyInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private final LoginInterceptor login;
	private final AdminInterceptor admin;
	private final ManagerInterceptor manager;
	private final ManagerOnlyInterceptor onlyMan;
	
	public WebConfig(LoginInterceptor login, AdminInterceptor admin, ManagerInterceptor superadmin, ManagerOnlyInterceptor onlyMan) {
		this.login = login;
		this.admin = admin;
		this.manager = superadmin;
		this.onlyMan = onlyMan;
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
			
			registry.addInterceptor(manager).addPathPatterns("/manager/**");
			
			registry.addInterceptor(onlyMan).addPathPatterns("/manager-only/**");
			
			registry.addInterceptor(admin).addPathPatterns("/admin/**");
		}
}