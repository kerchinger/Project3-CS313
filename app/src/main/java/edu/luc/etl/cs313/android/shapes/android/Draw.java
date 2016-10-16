package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Iterator;

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
		Rectangle r1 = new Rectangle(0,0);
		r1 = (Rectangle) c.getShape();
		paint.setColor(c.getColor());
		canvas.drawRect(0,0, r1.getWidth(), r1.getHeight(), paint);
		return null;
	}

	@Override
	public Void onFill(final Fill f) { // it fills a rectangle with a solid color
		Rectangle r2 = null;
		r2 = (Rectangle) f.getShape();
		paint.setStyle(Style.FILL);
		canvas.drawRect(0,0,r2.getHeight(), r2.getWidth(), paint);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		Object shapequestion;
		Rectangle rect = new Rectangle(0,0);
		Circle circ = new Circle(0);
		Iterator<? extends Shape> i = g.getShapes().iterator();

		while (i.hasNext()){
		Location locationt = (Location) i.next();
			canvas.translate(locationt.getX(), locationt.getY());
			shapequestion = locationt.getShape();

			if(shapequestion.equals(rect)){ // problem is right here, how to check whether or not shape is a rectangle or not
				rect = (Rectangle) shapequestion;
				canvas.drawRect(0,0,rect.getWidth(), rect.getHeight(),paint);
			} else {
				circ = (Circle) shapequestion;
				canvas.drawCircle(0,0,circ.getRadius(), paint);
			}
			canvas.translate(-locationt.getX(), - locationt.getY());
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) { // works!!
		Rectangle r = new Rectangle(0,0);
		r = (Rectangle) l.getShape();
		canvas.translate(l.getX(),l.getY()); // moves origin
		canvas.drawRect(0,0,r.getWidth(),r.getHeight(),paint);
		canvas.translate(- l.getX(), - l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0,r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		Rectangle r3 = null;
		r3 = (Rectangle) o.getShape();
	canvas.drawRect(0,0,r3.getHeight(),r3.getWidth(),paint);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		final float[] pts = null;
		canvas.drawLines(pts, paint);
		return null;
	}
}
