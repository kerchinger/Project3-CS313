package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
			this.canvas = canvas; //FIXME // it changed it from NULL to canvas it works!!
			this.paint = paint ; // FIXME same thing above and it works! it draws in genymotion
			paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) { // it works and draws a red outlines rectangle
		paint.setColor(c.getColor());
		canvas.drawRect(0,0, 80, 120, paint);
		return null;
	}

	@Override
	public Void onFill(final Fill f) { // it fills a rectangle with a solid color
		paint.setStyle(Style.FILL);
		canvas.drawRect(0,0,50, 100, paint);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {

		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(0,0); // moves origin
		canvas.drawRect(0,0,l.getY(),l.getX(), paint); // doesn't pass
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0,100, 150, paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
	canvas.drawRect(0,0,0,0,paint);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		final float[] pts = null;
		canvas.drawLines(pts, paint);
		return null;
	}
}
