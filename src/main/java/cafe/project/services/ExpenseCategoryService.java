package cafe.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.ExpenseCategoryDto;
import cafe.project.repositories.ExpenseCategoryRepository;
import cafe.project.repositories.entities.ExpenseCategory;

@Service
public class ExpenseCategoryService {

	private final ExpenseCategoryRepository repo;

	public ExpenseCategoryService(ExpenseCategoryRepository repo) {
		this.repo = repo;
	}

	public List<ExpenseCategoryDto> findAll() {
		List<ExpenseCategory> entities = this.repo.findAll();
		List<ExpenseCategoryDto> expenseCategories = entities.stream().map(this::toDto).toList();
		return expenseCategories;
	}

	public ExpenseCategoryDto findById(String expense_category_id) {
		ExpenseCategory entity = this.repo.findById(expense_category_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense Category");
		}
		return toDto(entity);
	}

	public int add(ExpenseCategoryDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense Category data is required");
		}
		ExpenseCategory entity = toEntity(dto);
		return this.repo.add(entity);
	}

	public int edit(String expense_category_id, ExpenseCategoryDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense Category data is required");
		}
		ExpenseCategory existingEntity = this.repo.findById(expense_category_id);
		if (existingEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense Category");
		}
		ExpenseCategory entity = toEntity(dto);
		return this.repo.edit(expense_category_id, entity);
	}

	public int delete(String expense_category_id) {
		ExpenseCategory entity = this.repo.findById(expense_category_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense Category");
		}
		return this.repo.delete(expense_category_id);
	}

	private ExpenseCategoryDto toDto(ExpenseCategory entity) {
		ExpenseCategoryDto dto = new ExpenseCategoryDto();
		dto.setExpense_category_id(entity.getExpense_category_id());
		dto.setCategory_name(entity.getCategory_name());
		return dto;
	}

	private ExpenseCategory toEntity(ExpenseCategoryDto dto) {
		ExpenseCategory entity = new ExpenseCategory();
		entity.setExpense_category_id(dto.getExpense_category_id());
		entity.setCategory_name(dto.getCategory_name());
		return entity;
	}
}