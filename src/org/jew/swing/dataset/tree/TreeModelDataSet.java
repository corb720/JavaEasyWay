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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class TreeModelDataSet <T>
implements TreeModel
{

	protected Map<Object, TreeNode> datas = new HashMap<Object, TreeNode>();

	protected TreeNode root;

	public TreeModelDataSet() {
	}

	public void setRoot(final Object id, final TreeNode rootNode) {
		this.datas.put(id, rootNode);
		this.root = rootNode;
	}

	public void addNode(final Object id, final TreeNode node){
		this.datas.put(id, node);
	}

	@Override
	public Object getRoot(){
		return this.root;
	}

	public TreeNode getNode(final Object id) {
		return this.datas.get(id);
	}

	@Override
	public Object getChild(final Object parent, final int index) {
		TreeNode node = (TreeNode) parent;
		return node.getChildren().get(index);
	}

	@Override
	public int getChildCount(final Object parent) {
		TreeNode node = (TreeNode) parent;
		return node.getChildren().size();
	}

	@Override
	public int getIndexOfChild(final Object parent, final Object child) {
		TreeNode node = (TreeNode) parent;
		for (int i = 0 ; i < node.getChildren().size() ; i++) {
			if(node.getChildren().get(i).equals(child)){
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isLeaf(final Object obj) {
		TreeNode node = (TreeNode) obj;
		return node.getChildren().size() == 0;
	}

	@Override
	public void valueForPathChanged(final TreePath path, final Object newValue) {
	}


	protected List<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();

	@Override
	public void addTreeModelListener(TreeModelListener tml) {
		treeModelListeners.add(tml);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener tml) {
		treeModelListeners.remove(tml);
	}

	protected void fireTreeStructureChanged() {
		TreeModelEvent e = new TreeModelEvent(this, new Object[] {this.root});
		for (TreeModelListener tml : treeModelListeners) {
			tml.treeStructureChanged(e);
		}
	}
}
