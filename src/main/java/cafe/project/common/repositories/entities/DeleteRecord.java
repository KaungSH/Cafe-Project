package cafe.project.common.repositories.entities;

import java.util.List;

public class DeleteRecord {
	
	private String parent_id, parent_table_name, child_id, child_table_name;
	private List<String> child_ids;
	
	public DeleteRecord() {}
	
	public DeleteRecord(String parent_id, String parent_table_name, String child_id, String child_table_name) {
		this.parent_id = parent_id;
		this.parent_table_name = parent_table_name;
		this.child_id = child_id;
		this.child_table_name = child_table_name;
	}
	
	public DeleteRecord(List<String> child_ids) {
		this.child_ids = child_ids;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_table_name() {
		return parent_table_name;
	}

	public void setParent_table_name(String parent_table_name) {
		this.parent_table_name = parent_table_name;
	}

	public String getChild_id() {
		return child_id;
	}

	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}

	public String getChild_table_name() {
		return child_table_name;
	}

	public void setChild_table_name(String child_table_name) {
		this.child_table_name = child_table_name;
	}

	public List<String> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<String> child_ids) {
		this.child_ids = child_ids;
	}
	
	

}
