package org.rnd.util;

public abstract class Graphics2DAdapter extends java.awt.Graphics2D
{
	private java.awt.Graphics2D delegate;

	protected Graphics2DAdapter(java.awt.Graphics2D graphics)
	{
		this.delegate = graphics;
	}

	@Override
	public void addRenderingHints(java.util.Map<?, ?> hints)
	{
		this.delegate.addRenderingHints(hints);
	}

	@Override
	public void clearRect(int x, int y, int width, int height)
	{
		this.delegate.clearRect(x, y, width, height);
	}

	@Override
	public void clip(java.awt.Shape s)
	{
		this.delegate.clip(s);
	}

	@Override
	public void clipRect(int x, int y, int width, int height)
	{
		this.delegate.clipRect(x, y, width, height);
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy)
	{
		this.delegate.copyArea(x, y, width, height, dx, dy);
	}

	@Override
	public void dispose()
	{
		this.delegate.dispose();
	}

	@Override
	public void draw(java.awt.Shape s)
	{
		this.delegate.draw(s);
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		this.delegate.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void drawGlyphVector(java.awt.font.GlyphVector g, float x, float y)
	{
		this.delegate.drawGlyphVector(g, x, y);
	}

	@Override
	public void drawImage(java.awt.image.BufferedImage img, java.awt.image.BufferedImageOp op, int x, int y)
	{
		this.delegate.drawImage(img, op, x, y);
	}

	@Override
	public boolean drawImage(java.awt.Image img, java.awt.geom.AffineTransform xform, java.awt.image.ImageObserver obs)
	{
		return this.delegate.drawImage(img, xform, obs);
	}

	@Override
	public boolean drawImage(java.awt.Image img, int x, int y, java.awt.image.ImageObserver observer)
	{
		return this.delegate.drawImage(img, x, y, observer);
	}

	@Override
	public boolean drawImage(java.awt.Image img, int x, int y, int width, int height, java.awt.image.ImageObserver observer)
	{
		return this.delegate.drawImage(img, x, y, width, height, observer);
	}

	@Override
	public boolean drawImage(java.awt.Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, java.awt.image.ImageObserver observer)
	{
		return this.delegate.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	}

	@Override
	public boolean drawImage(java.awt.Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, java.awt.Color bgcolor, java.awt.image.ImageObserver observer)
	{
		return this.delegate.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
	}

	@Override
	public boolean drawImage(java.awt.Image img, int x, int y, int width, int height, java.awt.Color bgcolor, java.awt.image.ImageObserver observer)
	{
		return this.delegate.drawImage(img, x, y, width, height, bgcolor, observer);
	}

	@Override
	public boolean drawImage(java.awt.Image img, int x, int y, java.awt.Color bgcolor, java.awt.image.ImageObserver observer)
	{
		return this.delegate.drawImage(img, x, y, bgcolor, observer);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2)
	{
		this.delegate.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawOval(int x, int y, int width, int height)
	{
		this.delegate.drawOval(x, y, width, height);
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
	{
		this.delegate.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints)
	{
		this.delegate.drawPolyline(xPoints, yPoints, nPoints);
	}

	@Override
	public void drawRenderableImage(java.awt.image.renderable.RenderableImage img, java.awt.geom.AffineTransform xform)
	{
		this.delegate.drawRenderableImage(img, xform);
	}

	@Override
	public void drawRenderedImage(java.awt.image.RenderedImage img, java.awt.geom.AffineTransform xform)
	{
		this.delegate.drawRenderedImage(img, xform);
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
	{
		this.delegate.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	@Override
	public void drawString(java.text.AttributedCharacterIterator iterator, float x, float y)
	{
		this.delegate.drawString(iterator, x, y);
	}

	@Override
	public void drawString(java.text.AttributedCharacterIterator iterator, int x, int y)
	{
		this.delegate.drawString(iterator, x, y);
	}

	@Override
	public void drawString(String str, float x, float y)
	{
		this.delegate.drawString(str, x, y);
	}

	@Override
	public void drawString(String str, int x, int y)
	{
		this.delegate.drawString(str, x, y);
	}

	@Override
	public void fill(java.awt.Shape s)
	{
		this.delegate.fill(s);
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		this.delegate.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void fillOval(int x, int y, int width, int height)
	{
		this.delegate.fillOval(x, y, width, height);
	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints)
	{
		this.delegate.fillPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void fillRect(int x, int y, int width, int height)
	{
		this.delegate.fillRect(x, y, width, height);
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
	{
		this.delegate.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	@Override
	public java.awt.Color getBackground()
	{
		return this.delegate.getBackground();
	}

	@Override
	public java.awt.Shape getClip()
	{
		return this.delegate.getClip();
	}

	@Override
	public java.awt.Rectangle getClipBounds()
	{
		return this.delegate.getClipBounds();
	}

	@Override
	public java.awt.Color getColor()
	{
		return this.delegate.getColor();
	}

	@Override
	public java.awt.Composite getComposite()
	{
		return this.delegate.getComposite();
	}

	@Override
	public java.awt.GraphicsConfiguration getDeviceConfiguration()
	{
		return this.delegate.getDeviceConfiguration();
	}

	@Override
	public java.awt.Font getFont()
	{
		return this.delegate.getFont();
	}

	@Override
	public java.awt.FontMetrics getFontMetrics(java.awt.Font f)
	{
		return this.delegate.getFontMetrics(f);
	}

	@Override
	public java.awt.font.FontRenderContext getFontRenderContext()
	{
		return this.delegate.getFontRenderContext();
	}

	@Override
	public java.awt.Paint getPaint()
	{
		return this.delegate.getPaint();
	}

	@Override
	public Object getRenderingHint(java.awt.RenderingHints.Key hintKey)
	{
		return this.delegate.getRenderingHint(hintKey);
	}

	@Override
	public java.awt.RenderingHints getRenderingHints()
	{
		return this.delegate.getRenderingHints();
	}

	@Override
	public java.awt.Stroke getStroke()
	{
		return this.delegate.getStroke();
	}

	@Override
	public java.awt.geom.AffineTransform getTransform()
	{
		return this.delegate.getTransform();
	}

	@Override
	public boolean hit(java.awt.Rectangle rect, java.awt.Shape s, boolean onStroke)
	{
		return this.delegate.hit(rect, s, onStroke);
	}

	@Override
	public void rotate(double theta)
	{
		this.delegate.rotate(theta);
	}

	@Override
	public void rotate(double theta, double x, double y)
	{
		this.delegate.rotate(theta, x, y);
	}

	@Override
	public void scale(double sx, double sy)
	{
		this.delegate.scale(sx, sy);
	}

	@Override
	public void setBackground(java.awt.Color color)
	{
		this.delegate.setBackground(color);
	}

	@Override
	public void setClip(int x, int y, int width, int height)
	{
		this.delegate.setClip(x, y, width, height);
	}

	@Override
	public void setClip(java.awt.Shape clip)
	{
		this.delegate.setClip(clip);
	}

	@Override
	public void setColor(java.awt.Color c)
	{
		this.delegate.setColor(c);
	}

	@Override
	public void setComposite(java.awt.Composite comp)
	{
		this.delegate.setComposite(comp);
	}

	@Override
	public void setFont(java.awt.Font font)
	{
		this.delegate.setFont(font);
	}

	@Override
	public void setPaint(java.awt.Paint paint)
	{
		this.delegate.setPaint(paint);
	}

	@Override
	public void setPaintMode()
	{
		this.delegate.setPaintMode();
	}

	@Override
	public void setRenderingHint(java.awt.RenderingHints.Key hintKey, Object hintValue)
	{
		this.delegate.setRenderingHint(hintKey, hintValue);
	}

	@Override
	public void setRenderingHints(java.util.Map<?, ?> hints)
	{
		this.delegate.setRenderingHints(hints);
	}

	@Override
	public void setStroke(java.awt.Stroke s)
	{
		this.delegate.setStroke(s);
	}

	@Override
	public void setTransform(java.awt.geom.AffineTransform Tx)
	{
		this.delegate.setTransform(Tx);
	}

	@Override
	public void setXORMode(java.awt.Color c1)
	{
		this.delegate.setXORMode(c1);
	}

	@Override
	public void shear(double shx, double shy)
	{
		this.delegate.shear(shx, shy);
	}

	@Override
	public void transform(java.awt.geom.AffineTransform Tx)
	{
		this.delegate.transform(Tx);
	}

	@Override
	public void translate(double tx, double ty)
	{
		this.delegate.translate(tx, ty);
	}

	@Override
	public void translate(int x, int y)
	{
		this.delegate.translate(x, y);
	}
}
