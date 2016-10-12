package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Size implements Visitor<Integer> {

	//there is one polygon in polygon
	@Override
	public Integer onPolygon(final Polygon p) {
		return 1;
	}

	//there is one circle in circle
	@Override
	public Integer onCircle(final Circle c) {
		return 1;
	}

	//counts how many shapes are in a group
	@Override
	public Integer onGroup(final Group g) {
		int groupCount = 0;
		for(Shape shape : g.getShapes()){
			groupCount += shape.accept(this);
		}
		return groupCount;
	}

	@Override
	public Integer onRectangle(final Rectangle q) {
		return 1;
	}

	@Override
	public Integer onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	@Override
	public Integer onFill(final Fill c) {
		return c.getShape().accept(this);
	}

	@Override
	public Integer onLocation(final Location l) {
		return l.getShape().accept(this);
	}

	@Override
	public Integer onStroke(final Stroke c) {
		return c.getShape().accept(this);
	}
}
