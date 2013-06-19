package org.jew.swing.dataset.tree.familytree;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jew.swing.dataset.tree.JTreeDataSet;
import org.jew.swing.dataset.tree.TreeProcessData;
import org.jew.swing.dataset.tree.familytree.datamodel.Parent;


public class startApplication {
		
	public static void main(String[] args) {
		JFrame frame = new JFrame();
    	frame.setLayout(new BorderLayout());
    	    	
    	TreeProcessData<Parent> processData = new TreeProcessData<Parent>() {	
    		@Override
    		public Object computeValue(final Parent parent) {
    			return parent.getName() + " " + parent.getAge();
    		}

			@Override
			public Object[][] computeParameters(final Parent parent) {
				return null;
			}

			@Override
			public Object computeInternalId(final Parent parent) {
				return parent.getName();
			}
		};    	
    	
    	JTreeDataSet<Parent> treeDataSet = new JTreeDataSet<Parent>(processData);
    	
    	treeDataSet.addPath(new Parent[]{
			new Parent("Granny", 92), new Parent("Roger", 52), new Parent("Kevin", 12)
    	});
    	
//    	treeDataSet.setRootNode(new Parent("Grany", 92));
//    	treeDataSet.getNode(JTreeDataSet.ROOT).addChild(14, new Parent("Roger", 52));
//    	treeDataSet.getNode(14).addChild(15, new Parent("Kevin", 12));
    	treeDataSet.getNode("Roger").addChild(new Parent("Francis", 8));
    	treeDataSet.getNode("Roger").addChild(new Parent("Julie", 5));
    	  	
//    	treeDataSet.getNode(14).remove();
//    	treeDataSet.getNode(14).moveTo(0);
//    	treeDataSet.getNode(14).expand();
//    	treeDataSet.getNode(14).retract();
//    	treeDataSet.getNode(14).setVisible(false);
//    	treeDataSet.getNode(14).setValue(new Parent("toto", 8));
//    	int selectedId = treeDataSet.getSelectedNode().getId();
//    	Parent selectedValue = treeDataSet.getSelectedNode().getValue();
    	    	
    	treeDataSet.fireTreeDataChanged();
    	
    	frame.add(new JScrollPane(treeDataSet.getDisplayComponent()), BorderLayout.CENTER);
       	
    	frame.setTitle("Family Tree");
    	frame.setSize(800, 600);  	
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	
    	frame.setVisible(true);   
	}
}
