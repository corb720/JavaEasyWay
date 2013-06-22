package org.jew.swing.dataset.table.companies;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;

import org.jew.swing.dataset.DataType;
import org.jew.swing.dataset.EditionEvents;
import org.jew.swing.dataset.table.JTableDataSet;
import org.jew.swing.dataset.table.TableCellRendererParameters;
import org.jew.swing.dataset.table.TableRowProcessData;
import org.jew.swing.dataset.table.companies.datamodel.Company;
import org.jew.swing.dataset.table.companies.datamodel.Nation;


public class startApplication {
		
	static JTableDataSet<Company> tableDataSet;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
    	frame.setLayout(new BorderLayout());
    	    	
    	try {
    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
    	} catch (Exception e) {
    	    // If Nimbus is not available, you can set the GUI to another look and feel.
    	}    	
    	
    	Map<Nation, String> nationMap = new HashMap<Nation, String>();
    	nationMap.put(Nation.USA, "United States");
    	nationMap.put(Nation.EN, "England");
    	nationMap.put(Nation.FR, "France");
    	nationMap.put(Nation.GER, "Germany");
    	nationMap.put(Nation.IT, "Italy");
    	    	    	
    	String[] columnNames = {
			"Name", "Nationality", "Turnover", "Traded"
    	};
    	
    	String[] columnTooltips = {
			"Name of the company", "Origin country", "Turnover in 2011 (in Million)", "Is Traded on stock market"
    	};
    	
    	DataType[] columnTypes = {
			DataType.TEXT, DataType.TEXT(nationMap), DataType.NUMBER("NNN.NNn"), DataType.BOOLEAN
    	};
    	
    	double[] columnWidths = {
    			JTableDataSet.PREFERED, JTableDataSet.FILL, JTableDataSet.FILL, 0.5 
    	};
    	    	
    	TableRowProcessData<Company> processData = new TableRowProcessData<Company>() {			
			@Override
			public Object computeValue(final Company company, final int columnIndex) {
				switch (columnIndex) {
				case 0:
					return company.getName();
				case 1 :
					return company.getNationality();
				case 2:
					return company.getTurnover();
				case 3:
					return company.isTraded();				
				default:
					return null;
				}
			}
		};
    			
		
		tableDataSet = new JTableDataSet<Company>(
    			columnNames,
//    			columnTooltips,
    			columnTypes, 
    			columnWidths, 
    			processData);
				

    	frame.add(new JScrollPane(tableDataSet.getDisplayComponent()), BorderLayout.CENTER);
		
    	tableDataSet.setColumnEditionEvent(3, new EditionEvents<Company>() {
    		@Override
    		public void notifyValueChanged(final Company company, final Object isTraded) {
    			company.setTraded((Boolean) isTraded);
    		}
		});
    	
    	tableDataSet.setColumnEditionEvent(2, new EditionEvents<Company>() {
    		@Override
    		public void notifyValueChanged(final Company company, final Object turnover) {
    			company.setTurnover((Double) turnover);
    		}
		});
    	
    	tableDataSet.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent e) {
//				System.err.println(tableDataSet.getSelectedRow());
//				System.err.println(tableDataSet.getSelectedValue().getName());
//				System.err.println(tableDataSet.getSelectedId());
//				System.err.println();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
    	
    	tableDataSet.setCellRenderParameters(new TableCellRendererParameters < Company > (){
    		@Override
    		public Color getBackgroundColor(
    				final Company company, 
    				final int columnIndex) {
    			
    			if(company.isTraded() && columnIndex == 3){
    				return Color.ORANGE;
    			}
    			if(company.getTurnover() > 50){
    				return Color.YELLOW;
    			}
    			return super.getBackgroundColor(company, columnIndex);
    		}
    		
    		@Override
    		public Color getSelectionBackgroundColor(
    				final Company company,
    				final int columnIndex) {
    			
    			if(company.isTraded() && columnIndex == 3){
    				return Color.BLUE.darker();
    			}
    			if(company.getTurnover() > 50){
    				return Color.BLUE;
    			}
    			return super.getSelectionBackgroundColor(company, columnIndex);
    		}
    	});
    	    	
    	
    	
    	java.util.Random rnd = new java.util.Random(System.currentTimeMillis());
    	for (int i = 1; i < 5; i++) {    		
    		Company c = new Company();
    		c.setName("Company " + i);
    		c.setNationality(Nation.values()[i%5]);
    		double turnover= rnd.nextInt(100);
			c.setTurnover(turnover);
			c.setTraded(i % 2 == 1);
			
			tableDataSet.addValue(i, c);
		}
    	//Refresh Table
    	tableDataSet.fireTableDataChanged();
    	
    	Thread t = new Thread(){
    		int id = 6;
    		public void run() {
    			while(! Thread.currentThread().isInterrupted()){
    				Company c = new Company();
    				c.setName(String.valueOf(id));
    				tableDataSet.addValue(id, c);
    				SwingUtilities.invokeLater(new Runnable() {						
						@Override
						public void run() {
							Company selectedCompany = tableDataSet.getSelectedValue();
							System.err.println(selectedCompany);
							tableDataSet.fireTableDataChanged();
							tableDataSet.selectValue(selectedCompany);
						}
					});    				
    				id ++;
    				try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						break;
					}
    				tableDataSet.removeValue(id -1);
    			}
    		}
    	};
    	t.start();
    	
    	frame.add(createForm(), BorderLayout.NORTH);    	
    	
    	frame.setTitle("Companies");
    	frame.setSize(800, 600);  	
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	
    	frame.setVisible(true);   
	}
	
	static protected JPanel createForm(){
		JPanel form = new JPanel();
		form.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		final JTextField columnIndexTextField = new JTextField("");
		columnIndexTextField.setColumns(50);
		final JCheckBox visibleCheckBox = new JCheckBox();
		JButton applyButton = new JButton("Apply");
		
		form.add(columnIndexTextField);
		form.add(visibleCheckBox);
		form.add(applyButton);
		
		applyButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tableDataSet.getColumn(Integer.valueOf(columnIndexTextField.getText())).setVisible(visibleCheckBox.isSelected());
			}
		});
		
		return form;
	}
}
