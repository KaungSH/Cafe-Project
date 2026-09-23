package cafe.project.YinminThiriSoe.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.repositories.SupplierRepository;
import cafe.project.YatiWinLatt.repositories.BranchRepository;
import cafe.project.YinminThiriSoe.models.StockImportEntryModel;
import cafe.project.YinminThiriSoe.models.StockImportListModel;
import cafe.project.YinminThiriSoe.repositories.StockImportRepository;
import cafe.project.YinminThiriSoe.repositories.entities.StockImport;
import cafe.project.repositories.EmployeeRepository;

@Service
public class StockImportService {

	private final StockImportRepository sir;
	private final SupplierRepository sr;
	private final EmployeeRepository er;
	private final BranchRepository br;

	public StockImportService(StockImportRepository sir, SupplierRepository sr, EmployeeRepository er,
			BranchRepository br) {
		this.sir = sir;
		this.sr = sr;
		this.er = er;
		this.br = br;
	}

	public List<StockImportListModel> findAll() {
		List<StockImportListModel> list = new ArrayList<>();

		for (StockImport item : sir.findAll()) {
			String supplierName = sr.findById(item.getSupplier_id()) != null
					? sr.findById(item.getSupplier_id()).getName()
					: "Unknown";
			String employeeName = er.findById(item.getEmployee_id()) != null
					? er.findById(item.getEmployee_id()).getName()
					: "Unknown";
			String branchName = br.findById(item.getBranch_id()) != null ? br.findById(item.getBranch_id()).getName()
					: "Unknown";

			list.add(toListModel(item, supplierName, employeeName, branchName));
		}

		return list;
	}

	public List<StockImportListModel> findDeletedAll() {
		List<StockImportListModel> list = new ArrayList<>();

		for (StockImport item : sir.findDeletedAll()) {
			String supplierName = sr.findById(item.getSupplier_id()) != null
					? sr.findById(item.getSupplier_id()).getName()
					: "Unknown";
			String employeeName = er.findById(item.getEmployee_id()) != null
					? er.findById(item.getEmployee_id()).getName()
					: "Unknown";
			String branchName = br.findById(item.getBranch_id()) != null ? br.findById(item.getBranch_id()).getName()
					: "Unknown";

			list.add(toListModel(item, supplierName, employeeName, branchName));
		}

		return list;
	}

	public StockImportEntryModel findById(String id) {
		return toEntryModel(sir.findById(id));
	}

	public StockImportListModel findDetailById(String id) {
		StockImport item = sir.findById(id);
		return toListModel(item, sr.findById(item.getSupplier_id()).getName(),
				er.findById(item.getEmployee_id()).getName(), br.findById(item.getBranch_id()).getName());
	}

	public StockImportListModel findDeletedById(String id) {
		StockImport item = sir.findDeletedById(id);
		return toListModel(item, sr.findById(item.getSupplier_id()).getName(),
				er.findById(item.getEmployee_id()).getName(), br.findById(item.getBranch_id()).getName());
	}

	public int add(StockImportEntryModel se) {
		return sir.add(toEntity(se));
	}

	public int edit(StockImportEntryModel se) {
		return sir.edit(toEntity(se));
	}

	public int delete(String id) {
		return sir.deleted(id);
	}

	public int recover(String id) {
		return sir.recover(id);
	}

	private StockImportListModel toListModel(StockImport si, String supplier_name, String employee_name,
			String branch_name) {
		return new StockImportListModel(si.getImport_id(), si.getImported_at(), si.getTotal_cost(), si.isIsdeleted(),
				supplier_name, employee_name, branch_name);
	}

	private StockImportEntryModel toEntryModel(StockImport si) {
		return new StockImportEntryModel(si.getImport_id(), si.getImported_at(), si.getTotal_cost(), si.getSupplier_id(),
				si.getEmployee_id(), si.getBranch_id());
	}

	private StockImport toEntity(StockImportEntryModel se) {
		return new StockImport(se.getImport_id(), se.getImported_at(), se.getTotal_cost(), se.getSupplier_id(),
				se.getEmployee_id(), se.getBranch_id(), false);
	}
}