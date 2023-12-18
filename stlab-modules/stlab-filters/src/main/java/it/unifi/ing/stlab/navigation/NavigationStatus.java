package it.unifi.ing.stlab.navigation;

public class NavigationStatus {

	private Integer pageSize;
	private Integer currentPage;
	
	
	public NavigationStatus() {
	}

	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	public void clear() {
		currentPage = null;
	}
}
