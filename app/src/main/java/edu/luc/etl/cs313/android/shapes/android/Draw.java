package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Iterator;
import java.util.List;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
			this.canvas = canvas; // it changed it from NULL to canvas it works!!
			this.paint = paint ; // same thing above and it works! it draws in genymotion
			paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) { // it works and draws a red outlines rectangle
		int defaultColor = paint.getColor(); //keeps original color
		paint.setColor(c.getColor());
		c.getShape().accept(this);
		paint.setColor(defaultColor);	//resets color
		return null;
	}

	@Override
	public Void onFill(final Fill f) { // it fills a rectangle with a solid color
		Style defaultStyle = paint.getStyle();	//keeps original style
		if(paint.getColor() > 0){
			paint.setStyle(Style.FILL);
		}
		else{
			paint.setStyle(Style.FILL_AND_STROKE);
		}
		f.getShape().accept(this);
		paint.setStyle(defaultStyle);	//resets style
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		Iterator<? extends Shape> i = g.getShapes().iterator();
		while(i.hasNext()){
			Shape shape1 = (Shape) i.next();
			shape1.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) { // works!!
		canvas.translate(l.getX(),l.getY()); // moves origin
		l.getShape().accept(this);
		canvas.translate(- l.getX(), - l.getY()); // idk i think this might have to change
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0,r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		Style defaultStyle = paint.getStyle();	//keeps original style
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		paint.setStyle(defaultStyle);	//resets style
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
		List<? extends Point> polygonPoints = s.getPoints();
		final float[] pts = new float[4 * polygonPoints.size()];
		for (int i = 0, j = 0; i < pts.length; i+=4, j++){
			Point pointXAndY1 = polygonPoints.get(j);
			float pointX1 = pointXAndY1.getX();//cast is automatic
			float pointY1 = pointXAndY1.getY();
			pts[i] = pointX1;
			pts[i+1] = pointY1;

			Point pointXAndY2 = null;
			if(j + 1 < polygonPoints.size()){
				pointXAndY2 = polygonPoints.get(j+1);
			} else {
				pointXAndY2 = polygonPoints.get(0);//wraps to first point
			}

			float pointX2 = pointXAndY2.getX();
			float pointY2 = pointXAndY2.getY();
			pts[i+2] = pointX2;
			pts[i+3] = pointY2;
		}
		canvas.drawLines(pts, paint);
		return null;
	}
}
