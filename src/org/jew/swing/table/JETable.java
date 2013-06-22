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


import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import sun.swing.table.DefaultTableCellHeaderRenderer;

@SuppressWarnings("serial")
public class JETable extends JTable {

	public JETable(){
		super();
	}

	public JETable(
			final TableModel dm){

		super(dm);
	}

	@Override
	public void createDefaultColumnsFromModel(){
		TableModel m = getModel();
		if (m != null) {             
			TableColumnModel cm = getColumnModel();
			while (cm.getColumnCount() > 0) {
				cm.removeColumn(cm.getColumn(0));
			}
			for (int i = 0; i < m.getColumnCount(); i++) {
				TableColumn newColumn = new TableColumn(i);
				if(getModel() instanceof JETableModel){
					JETableModel model = (JETableModel) getModel();
					if(model.getColumnTooltips() != null){
						DefaultTableCellHeaderRenderer renderer = new DefaultTableCellHeaderRenderer();		                
						renderer.setToolTipText(model.getColumnTooltip(i));
						newColumn.setHeaderRenderer(renderer);
					}
				}
				addColumn(newColumn);
			}
		}
	}	
}
