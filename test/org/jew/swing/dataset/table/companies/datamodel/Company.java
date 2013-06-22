package org.jew.swing.dataset.table.companies.datamodel;


public class Company {
		
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected Nation nationality;
	
	public Nation getNationality() {
		return nationality;
	}

	public void setNationality(Nation nationality) {
		this.nationality = nationality;
	}

	protected double turnover;
	
	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	protected boolean traded;
	
	public boolean isTraded() {
		return traded;
	}

	public void setTraded(boolean traded) {
		this.traded = traded;
	}

	public Company(){
		
	}	
	
	@Override
	public String toString() {
		return this.name;
	}
}
