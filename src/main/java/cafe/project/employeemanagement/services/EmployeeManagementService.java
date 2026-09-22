package cafe.project.employeemanagement.services;

import org.springframework.stereotype.Service;

import cafe.project.employeemanagement.models.ChangePasswordDto;
import cafe.project.employeemanagement.models.ChangeProfileDto;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.employeemanagement.repositories.EmployeeManagementRepository;

@Service
public class EmployeeManagementService {
	
	private final EmployeeManagementRepository repo;
	private final PasswordService pws;
	
	public EmployeeManagementService(EmployeeManagementRepository repo, PasswordService pws) {
		this.repo = repo;
		this.pws = pws;
	}
	
	public LoginDto findByEmail(String email) {
		return (repo.findByEmail(email) == null) ? null : repo.findByEmail(email);
	}
	
	public LoginDto findByLogin(LoginDto dto) {
		if(repo.findByEmail(dto.getEmail()) == null) {System.out.println("if method"); return null;}
		return (pws.matches(dto.getPassword(), repo.findByEmail(dto.getEmail()).getPassword())) ? repo.findByLogin(dto.getEmail(), repo.findByEmail(dto.getEmail()).getPassword()) : null;
	}
	
	public int changePassword(ChangePasswordDto cpdto) {
		if(repo.findById(cpdto.getEmployee_id()) == null) return 0;
		return (pws.matches(cpdto.getOldPassword(), repo.findById(cpdto.getEmployee_id()).getPassword())) ? repo.changePassword(cpdto.getEmployee_id(), pws.encode(cpdto.getNewPassword())) : 0;
	}
	
	public int changeProfile(ChangeProfileDto cfdto) {
		if(repo.findById(cfdto.getEmployee_id()) == null) {System.out.println("if method"); return 0;}
		return (pws.matches(cfdto.getPassword(), repo.findById(cfdto.getEmployee_id()).getPassword())) ? repo.changeProfile(cfdto) : 0;
	}
	
	public int changeStatus(String id, String status_id) {
		if(repo.findById(id) == null) {return 0;}
		return repo.changeStatus(id, status_id);
	}

}
