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
package org.jew.swing.dataset.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jew.swing.dataset.DataType;
import org.jew.swing.dataset.EditionEvents;
import org.jew.swing.table.JETable;
import org.jew.swing.table.JETableModel;


public class JTableDataSet <T>
{
	protected class Column{
		String name;
		String tooltips;
		double width;
		boolean visible;
		DataType type;
		public Column(
				final String name,
				final String tooltips,
				final double width, 
				final boolean visible,
				final DataType type) {
			
			this.name = name;
			this.tooltips = tooltips;
			this.width = width;
			this.visible = visible;
			this.type = type;
		}
	}
	
	
	public static final double FILL = -1;
	
	protected List<Column> columns = new ArrayList<Column>();

	protected boolean[] columnVisbilities = null;

	protected Component displayComponent;

	public Component getDisplayComponent () {
		return this.displayComponent;
	}

	protected JETableModel<T> model;

	protected JTable table;

	protected TableCellRendererParameters<T> tableCellRendererParameters = new TableCellRendererParameters<T>();

	protected Map<Integer, EditionEvents<T>> tableMouseEventMap = new HashMap<Integer, EditionEvents<T>>();

	public Map<Integer, EditionEvents<T>> getTableMouseEventMap () {
		return this.tableMouseEventMap;
	}

	protected TableRowProcessData<T> tableRowProcessData;

	protected Map<Integer, T> tableValues;

	public Map<Integer, T> getTableValues () {
		return this.tableValues;
	}
	
	int columnIndex = -1;
	
	TableColumn tableColumn = new TableColumn() {		
		@Override
		public void setWidth(final double width) {
			columns.get(columnIndex).width = width;
			columnModelToView();
		}
		
		@Override
		public void setVisible(final boolean visible) {
			columns.get(columnIndex).visible = visible;
			columnModelToView();
		}
	};

	public JTableDataSet(){

		this.initComponents();    	
		this.initCellRenderer();

		this.tableRowProcessData = new TableRowProcessData<T>() {
			@Override
			public Object computeValue(T obj, int columnIndex) {
				return obj;
			}    		
		};
	}
	
	boolean tooltipsVisible;
	
	public JTableDataSet(
			final String[] columnNames,
			final DataType[] columnTypes,
			final double[] columnWidths,
			final TableRowProcessData<T> rowProcessData){
		
		this.setTableRowProcessData(rowProcessData);
		
		this.initComponents();    	
		this.initCellRenderer();
		
		this.columns.clear();
		for (int i = 0; i < columnNames.length; i++) {
			this.columns.add(new Column(
					columnNames[i], 
					null, 
					columnWidths[i], 
					true,
					columnTypes[i]));
		}
		
		this.columnModelToView();		
	}

	public JTableDataSet(
			final String[] columnNames,
			final String[] columnTooltips,
			final DataType[] columnTypes,
			final double[] columnWidths,
			final TableRowProcessData<T> rowProcessData){

		this(columnNames, columnTypes, columnWidths, rowProcessData);
		this.setColumnTooltips(columnTooltips);
		this.tooltipsVisible = true;
	}

	protected void columnModelToView() {
		String[] columnNames = new String[this.columns.size()];
		String[] columnTooltips = new String[this.columns.size()];
		double[] columnWidths = new double[this.columns.size()];
		
		for (int i = 0 ; i < this.columns.size() ; i++) {
			columnNames[i] = this.columns.get(i).name;
			columnTooltips[i] = this.columns.get(i).tooltips;
			columnWidths[i] = this.columns.get(i).width;
		}
				
		model.setColumnNames(columnNames);
		if(this.tooltipsVisible){
			model.setColumnTooltips(columnTooltips);
		}
			
		fireTableStructureChanged();

		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < columnWidths.length; i++) {
			if(columns.get(i).visible){
				if(columnWidths[i] != FILL){
					int columnWidth = (int) columnWidths[i];
					columnModel.getColumn(i).setMinWidth(columnWidth);
					columnModel.getColumn(i).setMaxWidth(columnWidth);
					columnModel.getColumn(i).setPreferredWidth(columnWidth);
				}
			}else{
				columnModel.getColumn(i).setMinWidth(0);
				columnModel.getColumn(i).setMaxWidth(0);
				columnModel.getColumn(i).setPreferredWidth(0);
			}
		}
	}

	public void addColumn(
			final String columnName,
			final String columnTooltip,
			final DataType columnType,
			final int columnWidth){

		this.columns.add(new Column(columnName, columnTooltip, columnWidth, true, columnType));
		this.columnModelToView();
		
//		String[] formerNameList = this.model.getColumnNames();
//		String[] newNameList = new String[formerNameList.length + 1];
//		for (int i = 0; i < formerNameList.length; i++) {
//			newNameList[i] = formerNameList[i];
//		}
//		newNameList[formerNameList.length] = columnName;
//		this.setColumnNames(newNameList);
//
//		String[] formerTooltipList = this.model.getColumnNames();
//		String[] newTooltipList = new String[formerTooltipList.length + 1];
//		for (int i = 0; i < formerTooltipList.length; i++) {
//			newTooltipList[i] = formerTooltipList[i];
//		}
//		newTooltipList[formerTooltipList.length] = columnTooltip;
//		this.setColumnTooltips(newTooltipList);
//
//		DataType[] formerDataTypeList = this.columnTypes;
//		DataType[] newDataTypeList = new DataType[formerDataTypeList.length + 1];
//		for (int i = 0; i < formerDataTypeList.length; i++) {
//			newDataTypeList[i] = formerDataTypeList[i];
//		}
//		newDataTypeList[formerDataTypeList.length] = columnType;
//		this.setColumnTypes(newDataTypeList);
//
//		fireTableStructureChanged();
//
//		double[] formerWidthList = this.columnWidths;
//		double[] newWidthList = new double[formerWidthList.length + 1];
//		for (int i = 0; i < formerWidthList.length; i++) {
//			newWidthList[i] = formerWidthList[i];
//		}
//		newWidthList[formerWidthList.length] = columnWidth;
//		this.setColumnWidths(newWidthList);
	}

	public void removeColumn(final int index){
		this.columns.remove(index);
		this.columnModelToView();
	}
	
	public void addMouseListener(
			final MouseListener ml){

		this.table.addMouseListener(ml);

	}

	public void addMouseMotionListener(
			final MouseMotionListener mml){

		this.table.addMouseMotionListener(mml);

	}

	public void setAutoCreateRowSorter(
			final boolean enabled){

		this.table.setAutoCreateRowSorter(enabled);

	}

	public void setAutoResizeMode(
			final int autoResizeMode){

		this.table.setAutoResizeMode(autoResizeMode);

	}

	public void setCellRenderParameters(
			final TableCellRendererParameters<T> parameters){

		this.tableCellRendererParameters = parameters;

	}

	public void setCellSelectionEnabled(
			final boolean enabled){

		this.table.setCellSelectionEnabled(enabled);

	}

	public void setColumnEditionEvent(
			final int columnIndex,
			final EditionEvents<T> tme){

		this.tableMouseEventMap.put(columnIndex, tme);

	}

	public void setColumnNames(
			final String[] names){

		for (int i = 0; i < names.length; i++) {
			this.columns.get(i).name = names[i];
		}
		this.columnModelToView();
	}

	public void setColumnSelectionAllowed(
			final boolean enabled){

		this.table.setColumnSelectionAllowed(enabled);

	}

	public void setColumnTooltips(
			final String[] tooltips){

		for (int i = 0; i < tooltips.length; i++) {
			this.columns.get(i).tooltips = tooltips[i];
		}
		this.columnModelToView();
	}

	public void setColumnWidths(
			final double[] columnWidths){

		for (int i = 0; i < columnWidths.length; i++) {
			this.columns.get(i).width = columnWidths[i];
		}
		this.columnModelToView();
	}

	public void setColumnTypes (DataType[] columnTypes) {
		for (int i = 0; i < columnTypes.length; i++) {
			this.columns.get(i).type = columnTypes[i];
		}
		this.columnModelToView();
	}

	public void setDragEnabled(
			final boolean enabled){

		this.table.setDragEnabled(enabled);

	}

	public void setEnabled(
			final boolean enabled){

		this.table.setEnabled(enabled);

	}

	public void setRowHeight(
			final int rowHeight){

		this.table.setRowHeight(rowHeight);

	}

	public void setSelectionMode(
			final int model){

		this.table.setSelectionMode(model);

	}

	public void setTableRowProcessData(
			final TableRowProcessData<T> processData){

		this.tableRowProcessData = processData;

	}

	public int getSelectedRow()	{

		return this.table.getSelectedRow();
	}

	public int[] getSelectedRows(){

		return this.table.getSelectedRows();
	}

	@SuppressWarnings("unchecked")
	public T getSelectedValue()	{

		if(this.table.getSelectedRow() == -1){
			return null;
		}
		return (T) tableValues.values().toArray()[this.table.convertRowIndexToModel(this.table.getSelectedRow())];
	}

	@SuppressWarnings("unchecked")
	public T[] getSelectedValues(){

		int[] selRowsTab = this.table.getSelectedRows();
		Object[] selectedValues = new Object[selRowsTab.length];
		for (int i = 0; i < selRowsTab.length; i++) {
			selectedValues[i] = (T) tableValues.values().toArray()[this.table.convertRowIndexToModel(selRowsTab[i])];
		}

		return (T[])selectedValues;
	}

	public int getSelectedId(){		
		return getSelectedId(this.getSelectedValue());
	}

	public int[] getSelectedIds(){
		T[] values = this.getSelectedValues();
		int[] selectedIds = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			selectedIds[i] = this.getSelectedId(values[i]);
		}
		return selectedIds;
	}

	protected int getSelectedId(T value){
		for (int id : this.tableValues.keySet()) {
			T currentValue = this.tableValues.get(id);
			if(value.equals(currentValue)){
				return id;
			}
		}		
		return -1;
	}

	public JTableHeader getTableHeader(){

		return this.table.getTableHeader();
	}

	public void fireTableDataChanged(){

		((AbstractTableModel)this.model).fireTableDataChanged();
	}

	public void fireTableStructureChanged()	{

		((AbstractTableModel)this.model).fireTableStructureChanged();
	}

	public void selectRow(final int row){

		this.table.changeSelection(row, 0, true, false);
	}

	public void selectRows(
			final int[] rows){

		for (int i = 0; i < rows.length; i++) {
			this.table.changeSelection(rows[i], 0, true, false);
		}
	}

	@SuppressWarnings("unchecked")
	public void selectValue(
			final T value){

		T[] valTab = (T[]) this.tableValues.values().toArray();
		for (int i = 0; i < valTab.length; i++) {
			if(valTab[i].equals(value)){
				this.table.changeSelection(i, 0, true, false);
			}
		}    	
	}

	@SuppressWarnings("unchecked")
	public void selectValues(
			final T[] values){

		T[] valTab = (T[]) this.tableValues.values().toArray();
		for (int i = 0; i < valTab.length; i++) {
			for (int j = 0; j < values.length; j++) {			
				if(valTab[i].equals(values[j])){
					this.table.changeSelection(i, 0, true, false);
				}
			}
		}    
	}

	public TableColumn getColumn(final int index) {
		this.columnIndex = table.convertColumnIndexToModel(index);
		return this.tableColumn;
	}


	@SuppressWarnings("serial")
	protected void initCellRenderer(){

		TableCellRenderer renderer = new DefaultTableCellRenderer() {
			protected JCheckBox checkBox = new JCheckBox();

			@SuppressWarnings("unchecked")
			@Override
			public Component getTableCellRendererComponent(
					final JTable table, 
					final Object value,
					final boolean isSelected, 
					final boolean hasFocus,
					final int row, 
					final int column) {

				if (value instanceof String || value instanceof Enum) {
					this.setHorizontalAlignment(SwingConstants.CENTER);
				} else {
					this.setHorizontalAlignment(SwingConstants.RIGHT);
				}

				int columnIndex = table.convertColumnIndexToModel(column);

				Object data = value;
				if( ! (data instanceof Boolean)){
					data = columns.get(columnIndex).type.format(data);
				}

				T obj = (T) tableValues.values().toArray()[table.convertRowIndexToModel(row)];
				Color selectionBgColor = tableCellRendererParameters.getSelectionBackgroundColor(obj, columnIndex);
				Color selectionFgColor = tableCellRendererParameters.getSelectionForegroundColor(obj, columnIndex);
				Color bgColor = tableCellRendererParameters.getBackgroundColor(obj, columnIndex);
				Color fgColor = tableCellRendererParameters.getForegroundColor(obj, columnIndex);

				if (value !=null) {		    		

					if(value instanceof Boolean) {
						this.checkBox.setOpaque(true);
						this.checkBox.setSelected((Boolean) value);
						this.checkBox.setHorizontalAlignment(SwingConstants.CENTER);

						this.checkBox.setEnabled(this.isEnabled());

						if(isSelected){
							this.checkBox.setBackground(selectionBgColor);				
						}else{
							this.checkBox.setBackground(bgColor);
						}

						return checkBox;
					}
				}

				if(isSelected){
					this.setBackground(selectionBgColor);
					this.setForeground(selectionFgColor);
				}else{
					this.setBackground(bgColor);
					this.setForeground(fgColor);
				}		
				if(tableCellRendererParameters.getFont(obj, columnIndex) == null){
					this.getFont().deriveFont(Font.BOLD);
					setFont(this.getFont());
				}
				setValue(data);

				return this;
			}
		};
		this.table.setDefaultRenderer(Object.class, renderer);	
		this.table.setDefaultRenderer(Integer.class, renderer);	
		this.table.setDefaultRenderer(Double.class, renderer);	
		this.table.setDefaultRenderer(Float.class, renderer);	
		this.table.setDefaultRenderer(Boolean.class, renderer);	
		this.table.setDefaultRenderer(String.class, renderer);	
	}

	@SuppressWarnings("serial")
	protected void initComponents()
	{

		this.table = new JETable();

		this.tableValues = new HashMap<Integer, T>();

		JETableModel<T> model = new JETableModel<T>(){
			@SuppressWarnings("unchecked")
			@Override
			public void setValueAt(
					final Object value, 
					final int row, 
					final int column) {

				if(tableMouseEventMap.containsKey(column)){
					T obj = (T) tableValues.values().toArray()[row];
					tableMouseEventMap.get(column).notifyValueChanged(obj, value);
				}
			}
			@Override
			public boolean isCellEditable(
					final int row, 
					final int column) {

				return tableMouseEventMap.containsKey(column);
			}
			@SuppressWarnings("unchecked")
			@Override
			public Object getValueAt(
					final int row, 
					final int col) {

				T value = (T) tableValues.values().toArray()[row];    			
				Object data = tableRowProcessData.computeValue(value, col);	
				return data;
			}
			@Override
			public Class<?> getColumnClass(final int col) {
				return columns.get(col).type.getCellType();
			}
		};
		model.setTableMap(this.tableValues);
		this.model = model;

		this.table.setModel(model);
		this.table.setEnabled(true);
		this.table.setCellSelectionEnabled(true);
		this.table.setDragEnabled(true);
		this.table.setColumnSelectionAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);		
		this.table.setAutoCreateRowSorter(true);    	

		this.displayComponent = table;
	}
}
