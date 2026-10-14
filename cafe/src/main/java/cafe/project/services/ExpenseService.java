package cafe.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.ExpenseDto;
import cafe.project.repositories.ExpenseRepository;
import cafe.project.repositories.entities.Expense;

@Service
public class ExpenseService {

	private final ExpenseRepository repo;

	public ExpenseService(ExpenseRepository repo) {
		this.repo = repo;
	}

	public List<ExpenseDto> findAll(String branch_id) {
		List<Expense> entities = this.repo.findAll(branch_id);
		List<ExpenseDto> expenses = entities.stream().map(this::toDto).toList();
		return expenses;
	}

	public List<ExpenseDto> findAllAdmin() {
		List<Expense> entities = this.repo.findAllAdmin();
		List<ExpenseDto> expenses = entities.stream().map(this::toDto).toList();
		return expenses;
	}

	public ExpenseDto findById(String expense_id, String branch_id) {
		Expense entity = this.repo.findById(expense_id, branch_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense");
		}
		return toDto(entity);
	}

	public int add(ExpenseDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense data is required");
		}
		Expense entity = toEntity(dto);
		return this.repo.save(entity);
	}

	public int edit(String expense_id, ExpenseDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense data is required");
		}
		Expense existingEntity = this.repo.findById(expense_id, null);
		if (existingEntity == null) {
			// Alternatively fallback if branch-specific lookup is preferred, but matching
			// pattern:
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense");
		}
		Expense entity = toEntity(dto);
		return this.repo.edit(expense_id, entity);
	}

	public int delete(String expense_id) {
		return this.repo.softDelete(expense_id);
	}

	public List<ExpenseDto> deletedList(String branch_id) {
		return this.repo.DeletedList(branch_id).stream().map(this::toDto).toList();
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