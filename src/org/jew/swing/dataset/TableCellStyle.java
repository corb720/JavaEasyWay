package org.jew.swing.dataset;

import java.awt.Color;
import java.awt.Font;

public class TableCellStyle {
	protected Color backgroundColor = Color.WHITE;
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	protected Color foregroundColor = Color.BLACK;
	
	public Color getForegroundColor() {
		return foregroundColor;
	}
	
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
	protected Font font = new Font("SansSerif", Font.PLAIN, 12);
	
	public Font getFont() {
		return font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
}
