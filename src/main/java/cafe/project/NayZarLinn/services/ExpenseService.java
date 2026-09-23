package cafe.project.NayZarLinn.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.models.ExpenseDto;
import cafe.project.NayZarLinn.repositories.ExpenseRepository;
import cafe.project.NayZarLinn.repositories.entities.Expense;

@Service
public class ExpenseService {

	private final ExpenseRepository repo;

	public ExpenseService(ExpenseRepository repo) {
		this.repo = repo;
	}
	
	public List<ExpenseDto> findAll(){
		List<Expense> entities = this.repo.findAll();
		List<ExpenseDto> expenses = entities.stream().map(this::toDto).toList();
		return expenses;
	}
	
	public ExpenseDto findById(String expense_id) {
		Expense entity =this.repo.findById(expense_id);
		if(entity == null)
			return null;
		return toDto(entity);
	}
	
	public int add(ExpenseDto dto) {
		Expense entity = toEntity(dto);
		return this.repo.save(entity);
	}

	public int edit(String expense_id,ExpenseDto dto) {
		Expense entity = toEntity(dto);
		return this.repo.edit(expense_id,entity);
	}
	
	public int delete(String expense_id) {
		return this.repo.softDelete(expense_id);
	}
	
	public List<ExpenseDto> deletedList(){
		return this.repo.DeletedList().stream().map(this::toDto).toList();
	}
	
	public int restore(String expense_id) {
		return this.repo.restore(expense_id);
	}
	
	public int hardDelete(String expense_id) {
		return this.repo.hardDelete(expense_id);
	}
	
	private ExpenseDto toDto(Expense entity) {
		ExpenseDto dto = new ExpenseDto();
		
		dto.setExpense_id(entity.getExpense_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setExpense_category_id(entity.getExpense_category_id());
		dto.setAmount(entity.getAmount());
		dto.setExpense_date(entity.getExpense_date());
		dto.setDescription(entity.getDescription());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setCreated_at(entity.getCreated_at());
		dto.setIsdeleted(entity.getIsdeleted());
		dto.setBranch_name(entity.getBranch_name());
		dto.setCategory_name(entity.getCategory_name());
		return dto;
	}
	
	private Expense toEntity(ExpenseDto dto) {
		Expense entity = new Expense();
		
		entity.setExpense_id(dto.getExpense_id());
		entity.setBranch_id(dto.getBranch_id());
		entity.setExpense_category_id(dto.getExpense_category_id());
		entity.setAmount(dto.getAmount());
		entity.setExpense_date(dto.getExpense_date());
		entity.setDescription(dto.getDescription());
		entity.setEmployee_id(dto.getEmployee_id());
		entity.setCreated_at(dto.getCreated_at());
		entity.setIsdeleted(dto.getIsdeleted());
		return entity;
	}

}
