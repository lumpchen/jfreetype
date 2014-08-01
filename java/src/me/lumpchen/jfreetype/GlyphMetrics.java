package me.lumpchen.jfreetype;

import java.awt.geom.Point2D;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;

public class GlyphMetrics {

	public double width;
	public double height;

	public double horiBearingX;
	public double horiBearingY;
	public double horiAdvance;

	public double vertBearingX;
	public double vertBearingY;
	public double vertAdvance;

	public GlyphMetrics(FTGlyphMetrics metrics, AffineTransform at) {
		double d[] = this.transfer(new AffineTransform(at), metrics.width.doubleValue(),
				metrics.height.doubleValue());
		this.width = d[0];
		this.height = d[1];

		d = this.transfer(new AffineTransform(at), metrics.horiBearingX.doubleValue(),
				metrics.horiBearingY.doubleValue());
		this.horiBearingX = d[0];
		this.horiBearingY = d[1];

		d = this.transfer(new AffineTransform(at), metrics.horiAdvance.doubleValue(),
				metrics.vertAdvance.doubleValue());
		this.horiAdvance = d[0];
		this.vertAdvance = d[1];

		d = this.transfer(new AffineTransform(at), metrics.vertBearingX.doubleValue(),
				metrics.vertBearingY.doubleValue());
		this.vertBearingX = d[0];
		this.vertBearingY = d[1];

		// double scale = 1 / 64.0d;
		// this.width = metrics.width.intValue() * scale;
		// this.height = metrics.height.intValue() * scale;
		//
		// this.horiBearingX = metrics.horiBearingX.intValue() * scale;
		// this.horiBearingY = metrics.horiBearingY.intValue() * scale;
		//
		// this.horiAdvance = metrics.horiAdvance.intValue() * scale;
		// this.vertAdvance = metrics.vertAdvance.intValue() * scale;
		//
		// this.vertBearingX = metrics.vertBearingX.intValue() * scale;
		// this.vertBearingY = metrics.vertBearingY.intValue() * scale;
	}

	private double[] transfer(AffineTransform at, double x, double y) {
		double scale = 1 / 64.0d;
		if (at == null) {
			at = AffineTransform.getScaleInstance(scale, scale);
		} else {
			at.scale(scale, scale);
		}

		Point2D.Double p = new Point2D.Double(x, y);
		Point2D.Double ptDst = new Point2D.Double();
		at.transform(p, ptDst);
		return new double[] { ptDst.x, ptDst.y };
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();

		buf.append("width " + this.width);
		buf.append("\n");
		buf.append("height " + this.height);
		buf.append("\n");

		buf.append("horiBearingX " + this.horiBearingX);
		buf.append("\n");
		buf.append("horiBearingY " + this.horiBearingY);
		buf.append("\n");
		buf.append("horiAdvance " + this.horiAdvance);
		buf.append("\n");

		buf.append("vertBearingX " + this.vertBearingX);
		buf.append("\n");
		buf.append("vertBearingY " + this.vertBearingY);
		buf.append("\n");
		buf.append("vertAdvance " + this.vertAdvance);
		buf.append("\n");

		return buf.toString();
	}
}
