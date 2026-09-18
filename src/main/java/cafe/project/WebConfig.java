package cafe.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Maps web requests like http://localhost:8080/images/filename.jpg 
        // to your local directory D:/JWD (Java Web Development)/Dev/sevletprojects/posts_img/
		
		String userHome = System.getProperty("user.home");
		String uploadPath = "file:" + userHome + "/Downloads/Cafe Project Images/";
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}