/*
Copyright (C) 2013 by Florian SIMON

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package org.jew.swing.table;


import java.util.Map;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class JETableModel <T>
extends AbstractTableModel
{
	protected String[] columnTooltips = null;

	public String[] getColumnTooltips () {
		return this.columnTooltips;
	}

	protected String[] columnNames = null;

	public String[] getColumnNames () {
		return this.columnNames;
	}


	public void setColumnTooltips(final String[] tooltips){

		this.columnTooltips = tooltips;

	}

	public void setColumnNames(final String[] value){

		this.columnNames = value;
	}

	public String getColumnTooltip(
			final int n){

		return columnTooltips[n];
	}

	public String getColumnName(
			final int n){

		return columnNames[n];
	}

	public int getColumnCount(){

		if(this.columnNames == null){
			return 0;
		}

		return this.columnNames.length;
	}

	protected Map<Integer, T> tableMap;

	public void setTableMap (Map<Integer, T> value) {
		this.tableMap = value; 
	}


	public int indexOf(
			final int number)
	{

		Object[] tableTab = tableMap.keySet().toArray();
		for (int i = 0; i < tableTab.length; i++) {
			if(((Integer)tableTab[i]) == number){
				return i;
			}
		}


		return -1;

	}

	public int getRowCount()
	{



		return this.tableMap.size();

	}

	public void setValueAt(
			final Object value,
			final int row,
			final int column)
	{


	}

	public boolean isCellEditable(
			final int row,
			final int column)
	{



		return false;

	}

	public Object getValueAt(
			final int row,
			final int col)
	{
		if(getRowCount() > 0){
			if (row < getRowCount()){
				return this.tableMap.values().toArray()[row];
			}
		}
		return null;

	}

	public Class<?> getColumnClass(
			final int col)
			{



		return Object.class;

			}
}
