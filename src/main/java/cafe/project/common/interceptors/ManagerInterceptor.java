package cafe.project.common.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import cafe.project.employeemanagement.models.LoginDto;
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
        LoginDto user = (LoginDto) session.getAttribute("loggedInUser");
        if (!user.getEmployee_role().equals("MANAGER") && !user.getEmployee_role().equals("ADMIN")) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT ENOUGH PERMISSION!!!");
        }

        return true;
    }
}



     
        
  
       
