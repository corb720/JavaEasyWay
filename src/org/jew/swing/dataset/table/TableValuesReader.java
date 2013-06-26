package org.jew.swing.dataset.table;

public interface TableValuesReader<T> {
	public void readValue(final int id, final T obj);
}
