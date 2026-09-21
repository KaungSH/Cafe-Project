package cafe.project.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ManagerInterceptor  implements HandlerInterceptor {
	@Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("loggedInUser") == null) {

            response.sendRedirect("/login");
            return false;
        }
        loginDto user = (loginDto) session.getAttribute("loggedInUser");
        if (!user.getRole().equals(Role.MANAGER)) {
        	 throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT ENOUGH PERMISSION!!!");
            return false;
        }

        return true;
    }
}



     
        
  
       
