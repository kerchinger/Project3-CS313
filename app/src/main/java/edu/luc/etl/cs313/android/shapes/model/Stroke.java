package edu.luc.etl.cs313.android.shapes.model;

import android.graphics.Color;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class Stroke implements Shape {

	protected final Shape shape;
	private final int color;

	public Stroke(final int color, final Shape shape) { // IDK how correct this is but it passes test
		this.color = color;
		this.shape = shape;
	}

	public int getColor() { return color; }

	public Shape getShape() {
		return shape;
	}

	@Override
	public <Result> Result accept(Visitor<Result> v) {
		return v.onStroke(this);
	}
}
