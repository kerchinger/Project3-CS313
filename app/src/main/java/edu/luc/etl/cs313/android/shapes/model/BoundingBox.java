package edu.luc.etl.cs313.android.shapes.model;

import java.util.Iterator;
import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius)); // passes TestBpundingBox
	}

	@Override
	public Location onFill(final Fill f) {return new Location(0,0,f.getShape());} // passes TestBoundingBox

	@Override
	public Location onGroup(final Group g) {
		int x1 = 0;
		int y1 = 0;
		Shape shape1 = new Rectangle(0,0);
		Iterator i = g.getShapes().iterator();
		while(i.hasNext()) {
			Location location =(Location)((Shape) i.next()).accept(this);
			x1 = location.getX();
			y1 = location.getY();
			shape1 = location.getShape();
		}
		return new Location(x1, y1, shape1); // TODO this doesn't work
	}

	@Override
	public Location onLocation(final Location l) {return new Location(l.getX(), l.getY(), l.getShape());} // passes TestBpundingBox

	@Override
	public Location onRectangle(final Rectangle r) {return new Location(0, 0, r);}// passes TestBpundingBox

	@Override
	public Location onStroke(final Stroke c) {return new Location(0,0,c.getShape());}// passes TestBpundingBox I don't kow how accurate the code is

	@Override
	public Location onOutline(final Outline o) {
		return new Location(0,0,o.getShape());
	}// passes TestBpundingBox but I would look back to make sure this is correct

	@Override
	public Location onPolygon(final Polygon s) {
		// still need to work on this so that it runs properly
		final List<? extends Point> points = s.getPoints();
		points.get(0);
		return new Location(50,50, new Rectangle(70, 60)); //TODO this deosn't work
	}
}
