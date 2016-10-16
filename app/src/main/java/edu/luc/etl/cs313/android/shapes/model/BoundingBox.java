package edu.luc.etl.cs313.android.shapes.model;

import java.util.Iterator;
import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius)); // passes TestBpundingBox
	}

	@Override
	public Location onFill(final Fill f) {return f.getShape().accept(this);}

	@Override
	public Location onGroup(final Group g) {
		int xMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE;
		int yMin = Integer.MAX_VALUE;
		int yMax = Integer.MIN_VALUE;
		Iterator<? extends Shape> i = g.getShapes().iterator();
		while (i.hasNext()) {
			Location locationStart = (Location)i.next().accept(this);//gets boundingbox of shape
			Rectangle shapeBoundingBox = (Rectangle)locationStart.getShape();//gets rectangle from the boundingbox
			xMin = Math.min(xMin, locationStart.getX());
			yMin = Math.min(yMin, locationStart.getY());
			xMax = Math.max(xMax, locationStart.getX() + shapeBoundingBox.getWidth());
			yMax = Math.max(yMax, locationStart.getY() + shapeBoundingBox.getHeight());
		}
		return new Location (xMin, yMin, new Rectangle(xMax - xMin, yMax - yMin));
	}

	@Override
	public Location onLocation(final Location l) {
		Location shapeBoundingBox = l.getShape().accept(this);
		return new Location(l.getX() + shapeBoundingBox.getX(), l.getY() + shapeBoundingBox.getY(), shapeBoundingBox.getShape());//location must be corrected by existing location
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		return new Location(0, 0, new Rectangle(r.getWidth(), r.getHeight()));}// passes TestBpundingBox

	@Override
	public Location onStroke(final Stroke c) {return c.getShape().accept(this);}// changed like in Size

	@Override
	public Location onOutline(final Outline o) {
		return o.getShape().accept(this);// changed it like in the Size
	}

	@Override
	public Location onPolygon(final Polygon s) {
		int xl = Integer.MAX_VALUE;
		int xr = Integer.MIN_VALUE;
		int yd = Integer.MAX_VALUE;
		int yu = Integer.MIN_VALUE;
		Iterator<? extends Point> i = s.getPoints().iterator();
		while (i.hasNext()) {
			Point polygonPoint = i.next();
			xl = Math.min(xl, polygonPoint.getX());
			xr = Math.max(xr, polygonPoint.getX());
			yd = Math.min(yd, polygonPoint.getY());
			yu = Math.max(yu, polygonPoint.getY());
		}
		return new Location (xl, yd, new Rectangle(xr-xl, yu-yd));
	}
}
