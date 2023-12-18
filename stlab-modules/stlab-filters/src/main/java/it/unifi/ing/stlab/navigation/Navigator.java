package it.unifi.ing.stlab.navigation;

public abstract class Navigator {

	
	protected NavigationStatus navigationStatus;
	
	protected Navigator() {
	}
	
	public void init() {  
	}
	
	public void reset() {
		getNavigationStatus().clear();
		init();
	}

	public NavigationStatus getNavigationStatus() {
		return navigationStatus;
	}
	public void setNavigationStatus(NavigationStatus status) {
		this.navigationStatus = status;
	}



	public Integer getPageCount() {
		if ( getItemCount() == null ) return new Integer( 0 );
		
		if ( getItemCount().intValue() % getNavigationStatus().getPageSize() == 0 ) {
			return getItemCount().intValue() / getNavigationStatus().getPageSize();
		} else {
			return getItemCount().intValue() / getNavigationStatus().getPageSize() + 1;
		}
	}
	
	public Integer getCurrentPage() {
		if ( getNavigationStatus().getCurrentPage() == null ) {
			initCurrent();
		}
		return getNavigationStatus().getCurrentPage();
	}
	private void initCurrent() {
		if ( getItemCount() == null || getItemCount().intValue() == 0 ) {
			getNavigationStatus().setCurrentPage( new Integer( 0 ));
		} else {
			getNavigationStatus().setCurrentPage( new Integer( 1 ));
		}
	}
	public void setCurrentPage( Integer current ) {
		if ( current == null ) 
			throw new NullPointerException( "current page cannot be null" );

		if ( getPageCount().intValue() == 0 && current.intValue() != 0 ||
			 getPageCount().intValue() > 0 && !existsPage( current ) )
			throw new IllegalArgumentException( "current page connot be " + current );

		getNavigationStatus().setCurrentPage( current );
	}


	public boolean existsPage( Integer page ) {
		if ( page == null ) return false;

		return page.intValue() > 0 && 
				page.intValue() <= getPageCount().intValue();
	}

	public Integer getFirstItem() {
		return ( getCurrentPage() - 1 ) * getNavigationStatus().getPageSize() + 1;
	}
	public Integer getLastItem() {
		return getCurrentPage() * getNavigationStatus().getPageSize() < getItemCount() ? getCurrentPage() * getNavigationStatus().getPageSize() : getItemCount();
	}


	public boolean hasNext() {
		return existsPage( getCurrentPage() + 1 ); 
	}

	public void moveNext() {
		if ( !hasNext() ) return;

		setCurrentPage( getCurrentPage() + 1 );
	}

	public boolean hasPrevious() {
		return existsPage( getCurrentPage() - 1 ); 
	}

	public void movePrevious() {
		if ( !hasPrevious() ) return;

		setCurrentPage( getCurrentPage() - 1 );
	}
	
	public Integer getOffset() {
		return getFirstItem() - 1;
	}
	
	public Integer getLimit() {
		return getNavigationStatus().getPageSize();
	}

	public void refreshCurrentPage() {
		if ( getItemCount().intValue() == 0 ) {
			getNavigationStatus().setCurrentPage( new Integer( 0 ));
		} else {
			Integer targetPage = min( max( getNavigationStatus().getCurrentPage(), 1 ), getPageCount() );
			getNavigationStatus().setCurrentPage( targetPage );
		}
	}
	
	private Integer min( Integer i1, Integer i2 ) {
		Integer p1 = ( i1 != null ) ? i1 : 0;
		Integer p2 = ( i2 != null ) ? i2 : 0;
	
		return ( p1.intValue() < p2.intValue() ) ?  p1 : p2;
	}

	private Integer max( Integer i1, Integer i2 ) {
		Integer p1 = ( i1 != null ) ? i1 : 0;
		Integer p2 = ( i2 != null ) ? i2 : 0;
	
		return ( p1.intValue() > p2.intValue() ) ?  p1 : p2;
	}
	
	public abstract Integer getItemCount();
}
