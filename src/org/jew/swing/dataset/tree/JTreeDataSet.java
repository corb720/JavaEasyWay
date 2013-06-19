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
package org.jew.swing.dataset.tree;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


public class JTreeDataSet <T>
{

	public static final int ROOT = 0;

	protected JTree tree;

	protected TreeModelDataSet<T> treeModel;

	protected TreeProcessData<T> processData;

	public Component getDisplayComponent() {
		return this.tree;
	}

	public JTreeDataSet(final TreeProcessData<T> processData) {
		this.processData = processData;
		this.initComponents();
	}

	public void addPath(T[] path) {
		this.setRootNode(path[0]);
		T parent = path[0];
		for (int i = 1; i < path.length; i++) {
			this.getNode(this.processData.computeInternalId(parent)).addChild(path[i]);
			parent = path[i];
		}
	}

	public void setRootNode(final T value) {
		TreeNode rootNode = this.createTreeNode(value);
		this.treeModel.setRoot(this.processData.computeInternalId(value), rootNode);
	}

	public TreeNode getNode(final Object id) {
		return this.treeModel.getNode(id);
	}

	public int getSelectedNode() {
		return 0;
	}

	public void fireTreeDataChanged() {
		this.treeModel.fireTreeStructureChanged();
	}

	protected void initComponents() {
		this.treeModel = new TreeModelDataSet<T>();
		this.tree = new JTree(this.treeModel);
		this.tree.setRootVisible(true);
		this.tree.setShowsRootHandles(true);
		//		this.tree.setModel(this.treeModel);

		this.tree.setCellRenderer(new DefaultTreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(
					JTree tree, Object value,
					boolean arg2, 
					boolean arg3, 
					boolean arg4, 
					int arg5, 
					boolean arg6) {

				TreeNode node = (TreeNode) value;
				Object displayValue = processData.computeValue((T) node.getData());
				this.setText(displayValue.toString());
				return this;
			}
		});
	}

	protected TreeNode createTreeNode(final T value) {
		TreeNode node = new TreeNode() {

			protected List<TreeNode> children = new ArrayList<TreeNode>();

			protected T data;

			@Override
			public void addChild(final Object data) {
				TreeNode node = createTreeNode((T) data);
				this.children.add(node);
				treeModel.addNode(processData.computeInternalId((T)data), node);
			}

			@Override
			public List<TreeNode> getChildren() {
				return this.children;
			}

			@Override
			public void setVisible(final boolean visible) {				
			}

			@Override
			public void retract() {				
			}

			@Override
			public void remove() {				
			}

			@Override
			public void moveTo(final int id) {				
			}

			@Override
			public void expand() {

			}

			@Override
			public Object getData() {
				return this.data;
			}

			@Override
			public void setData(final Object data) {
				this.data = (T) data;
			}
		};
		node.setData(value);
		return node;
	}

}
