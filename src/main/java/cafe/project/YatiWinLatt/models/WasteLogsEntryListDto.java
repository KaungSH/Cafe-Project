package cafe.project.YatiWinLatt.models;

import java.util.List;

public class WasteLogsEntryListDto {
	
	private List<WasteLogsEntryDto> dtoList;
	
	public WasteLogsEntryListDto() {}
	
	public WasteLogsEntryListDto(List<WasteLogsEntryDto> dtoList) {
		this.dtoList = dtoList;
	}

	public List<WasteLogsEntryDto> getDtoList() {
		return dtoList;
	}

	public void setDtoList(List<WasteLogsEntryDto> dtoList) {
		this.dtoList = dtoList;
	}
	
	

}
