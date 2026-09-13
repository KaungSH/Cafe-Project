package cafe.project.YatiWinLatt.models;

public class BranchStatusListDto {
	private String branch_status_id;
	private String name;

	public BranchStatusListDto() {
	}

	public BranchStatusListDto(String branch_status_id, String name) {
		this.branch_status_id = branch_status_id;
		this.name = name;
	}

	public String getBranch_status_id() {
		return branch_status_id;
	}

	public void setBranch_status_id(String branch_status_id) {
		this.branch_status_id = branch_status_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}