package cafe.project.YinminThiriSoe.services;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.repositories.IngredientBatchRepository;
import cafe.project.NayZarLinn.repositories.SupplierRepository;
import cafe.project.NayZarLinn.repositories.entities.IngredientBatch;
import cafe.project.YatiWinLatt.repositories.BranchRepository;
import cafe.project.YinminThiriSoe.models.StockImportDetailEntry;
import cafe.project.YinminThiriSoe.models.StockImportEntryModel;
import cafe.project.YinminThiriSoe.models.StockImportListModel;
import cafe.project.repositories.EmployeeRepository;
import cafe.project.YinminThiriSoe.repositories.StockImportDetailRepository;
import cafe.project.YinminThiriSoe.repositories.StockImportRepository;
import cafe.project.YinminThiriSoe.repositories.entities.StockImport;

@Service
public class StockImportService {

	private final StockImportRepository sir;
	private final SupplierRepository sr;
	private final EmployeeRepository er;
	private final BranchRepository br;
	private final StockImportDetailRepository sdr;
	private final IngredientBatchRepository ibr;

	public StockImportService(StockImportRepository sir, SupplierRepository sr, EmployeeRepository er,
			BranchRepository br, StockImportDetailRepository sdr, IngredientBatchRepository ibr) {
		this.sir = sir;
		this.sr = sr;
		this.er = er;
		this.br = br;
		this.sdr = sdr;
		this.ibr = ibr;
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
		int result = sir.add(toEntity(se));

		String branch_id = se.getBranch_id();
		String import_id = se.getImport_id();

		if (se.getDetails() != null) {
			for (StockImportDetailEntry detail : se.getDetails()) {

				String detailId = "IMPDT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
				detail.setImport_detail_id(detailId);
				detail.setImport_id(import_id);

				cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail dbDetail = new cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail();

				dbDetail.setImport_detail_id(detailId);
				dbDetail.setImport_id(import_id);
				dbDetail.setIngredient_type_id(detail.getIngredient_type_id());
				dbDetail.setQuantity_ordered(detail.getQuantity_ordered());
				dbDetail.setUnit_cost(detail.getUnit_cost());

				sdr.add(dbDetail);

				IngredientBatch batch = new IngredientBatch();
				batch.setBatchId("BAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
				batch.setBranchId(branch_id);
				batch.setIngredientTypeId(detail.getIngredient_type_id());
				batch.setImportDetailId(detailId);
				batch.setRemainingQuantity(BigDecimal.valueOf(detail.getQuantity_ordered()));
				batch.setUnitCost(BigDecimal.valueOf(detail.getUnit_cost()));
				batch.setManufacturedDate(LocalDate.now());
				batch.setExpireDate(detail.getExpireDate());
				batch.setIsExpired(false);
				batch.setIsDeleted(false);
				batch.setCreatedAt(LocalDateTime.now());

				ibr.save(batch);
			}
		}

		return result;
	}

	public int edit(StockImportEntryModel se) {
		int result = sir.edit(toEntity(se));

		String branch_id = se.getBranch_id();
		String import_id = se.getImport_id();

		List<String> incomingDetailIds = new ArrayList<>();
		if (se.getDetails() != null) {
			for (StockImportDetailEntry detail : se.getDetails()) {

				String detailId = detail.getImport_detail_id();
				if (detailId == null || detailId.isEmpty()) {
					detailId = "IMPDT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
					detail.setImport_detail_id(detailId);
				}

				incomingDetailIds.add(detailId);

				
				IngredientBatch batch = ibr.findByImportDetailId(detailId);
				if (batch != null) {
					
					batch.setRemainingQuantity(BigDecimal.valueOf(detail.getQuantity_ordered()));
					batch.setUnitCost(BigDecimal.valueOf(detail.getUnit_cost()));
					batch.setExpireDate(detail.getExpireDate());
					ibr.edit(batch.getBatchId(), batch);
				} else {
					
					cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail dbDetail = new cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail();

					dbDetail.setImport_detail_id(detailId);
					dbDetail.setImport_id(import_id);
					dbDetail.setIngredient_type_id(detail.getIngredient_type_id());
					dbDetail.setQuantity_ordered(detail.getQuantity_ordered());
					dbDetail.setUnit_cost(detail.getUnit_cost());

					sdr.add(dbDetail); 

					
					batch = new IngredientBatch();
					batch.setBatchId("BAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
					batch.setBranchId(branch_id);
					batch.setIngredientTypeId(detail.getIngredient_type_id());
					batch.setImportDetailId(detailId);
					batch.setRemainingQuantity(BigDecimal.valueOf(detail.getQuantity_ordered()));
					batch.setUnitCost(BigDecimal.valueOf(detail.getUnit_cost()));
					batch.setManufacturedDate(LocalDate.now());
					batch.setExpireDate(detail.getExpireDate());
					batch.setIsExpired(false);
					batch.setIsDeleted(false);
					batch.setCreatedAt(LocalDateTime.now());
					ibr.save(batch);
				}
			}
		}

		StockImport updatedImport = sir.findById(import_id);
		if (updatedImport != null && updatedImport.getStockImportDetails() != null) {
			for (cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail dbDetail : updatedImport
					.getStockImportDetails()) {
				if (!incomingDetailIds.contains(dbDetail.getImport_detail_id())) {
					IngredientBatch batchToDelete = ibr.findByImportDetailId(dbDetail.getImport_detail_id());
					if (batchToDelete != null) {
						ibr.softDelete(batchToDelete.getBatchId());
					}
				}
			}
		}

		return result;
	}

	public int delete(String id) {
		int result = sir.deleted(id);

		StockImport stockImport = sir.findDeletedById(id);
		if (stockImport != null && stockImport.getStockImportDetails() != null) {
			for (cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail detail : stockImport
					.getStockImportDetails()) {
				IngredientBatch batch = ibr.findByImportDetailId(detail.getImport_detail_id());
				if (batch != null) {
					batch.setIsDeleted(true);
					ibr.save(batch);
				}
			}
		}

		return result;
	}

	public int recover(String id) {
		int result = sir.recover(id);

		StockImport stockImport = sir.findById(id);
		if (stockImport != null && stockImport.getStockImportDetails() != null) {
			for (cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail detail : stockImport
					.getStockImportDetails()) {
				IngredientBatch batch = ibr.findByImportDetailId(detail.getImport_detail_id());
				if (batch != null) {
					batch.setIsDeleted(false);
					ibr.save(batch);
				}
			}
		}

		return result;
	}

	private StockImportListModel toListModel(StockImport si, String supplier_name, String employee_name,
			String branch_name) {
		return new StockImportListModel(si.getImport_id(), si.getImported_at(), si.getTotal_cost(), si.isIsdeleted(),
				supplier_name, employee_name, branch_name);
	}

	private StockImportEntryModel toEntryModel(StockImport si) {
		return new StockImportEntryModel(si.getImport_id(), si.getImported_at(), si.getTotal_cost(),
				si.getSupplier_id(), si.getEmployee_id(), si.getBranch_id());
	}

	private StockImport toEntity(StockImportEntryModel se) {
		return new StockImport(se.getImport_id(), se.getImported_at(), se.getTotal_cost(), se.getSupplier_id(),
				se.getEmployee_id(), se.getBranch_id(), false);
	}
}