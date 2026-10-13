package cafe.project.common.interceptors;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.DailyRegisterEntryDto;
import cafe.project.services.DailyRegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class DailyRegisterInterceptor implements HandlerInterceptor {
	private final DailyRegisterService registerService;
	
	public DailyRegisterInterceptor(DailyRegisterService registerService) {
		this.registerService = registerService;
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		DailyRegisterEntryDto dto = registerService.getRegisterEntryDtoByDateOpened(LocalDate.now(), ldto.getEmployee_id());

        if (dto == null && !ldto.getEmployee_role().equals("ADMIN")) {
            response.sendRedirect("/openshift");
            return false;
        }

        return true;
    }
}

