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
package org.jew.swing.progressbar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class Windows7LikeUI extends ComponentUI{
	protected BufferedImage buffer;

	@Override
	public void paint(final Graphics g, final JComponent c) {
		CircularProgressBar circularProgressBar = (CircularProgressBar) c;
		Graphics2D g2d = (Graphics2D) g;

		if(this.buffer == null){
			this.buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dBuffer = this.buffer.createGraphics();
			g2dBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
			float max = (float) (Math.PI * 2) * 1000;
			for(int i = 0 ; i <  max ; i++){
				g2dBuffer.translate(600 / 2, 600 / 2);
				g2dBuffer.rotate(1f / 1000f);

				g2dBuffer.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - (i / max)));

				g2dBuffer.setPaint(new GradientPaint(
						0, 600 / 4, new Color(0.0f, 0.5f, 0.8f, 0.0f),
						0, 600 / 3, new Color(0.0f, 0.5f, 0.8f, 1.0f)
						)
						);				
				g2dBuffer.drawRect(0, 600 / 4, 5, 600 / 8);

				g2dBuffer.setPaint(new GradientPaint(
						0, 600 / 3, new Color(0.0f, 0.5f, 0.8f, 1.0f),
						0, 600 / 2, new Color(0.0f, 0.5f, 0.8f, 0.0f)
						)
						);				
				g2dBuffer.drawRect(0, 600 / 3, 5, 600 / 8);

				g2dBuffer.translate(- 600 / 2, - 600 / 2);
			}
			g2dBuffer.dispose();
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(c.getWidth() / 2, c.getHeight() / 2);
		g2d.scale(c.getHeight() / 600f, c.getHeight() / 600f);
		g2d.rotate(circularProgressBar.getRotationAngle());
		g2d.drawImage(this.buffer, - 300, - 300, null);
		g2d.translate(- c.getWidth() / 2, - c.getHeight() / 2);
		g2d.dispose();
	}

}
