package org.jew.swing.dataset.tree.familytree.datamodel;

public class Parent {
		
	protected String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	protected int age;
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public Parent(final String name, final int age) {
		this.name = name;
		this.age = age;
	}
}
