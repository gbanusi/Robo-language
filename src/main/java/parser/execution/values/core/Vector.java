/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package parser.execution.values.core;

import java.io.Serializable;


/** Encapsulates a 3D vector. Allows chaining operations by returning a reference to itself in all modification methods.
 * @author badlogicgames@gmail.com */
public class Vector implements Serializable {
	private static final long serialVersionUID = 3840054589595372522L;

	/** the x-component of this vector **/
	public double x;
	/** the y-component of this vector **/
	public double y;
	/** the z-component of this vector **/
	public double z;

	// JEDINICNNI VEKTORI !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public final static Vector ex = new Vector(1, 0, 0);
	public final static Vector ey = new Vector(0, 1, 0);
	public final static Vector ez = new Vector(0, 0, 1);
	public final static Vector Zero = new Vector(0, 0, 0);

	private final static Matrix4 tmpMat = new Matrix4();

	/** Constructs a vector at (0,0,0) */
	public Vector() {
	}

	/** Creates a vector with the given components
	 * @param x The x-component
	 * @param y The y-component
	 * @param z The z-component */
	public Vector(double x, double y, double z) {
		this.set(x, y, z);
	}

	/** Creates a vector from the given vector
	 * @param vector The vector */
	public Vector(final Vector vector) {
		this.set(vector);
	}

	/** Sets the vector to the given components
	 *
	 * @param x The x-component
	 * @param y The y-component
	 * @param z The z-component
	 * @return this vector for chaining */
	public Vector set (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector set (final Vector vector) {
		return this.set(vector.x, vector.y, vector.z);
	}

	public Vector cpy () {
		return new Vector(this);
	}

	public Vector add (final Vector vector) {
		return new Vector(this.x + vector.x, this.y + vector.y, this.z + vector.z);
	}

	/** Adds the given value to all three components of the vector.
	 *
	 * @param values The value
	 * @return This vector for chaining */
	public Vector add (double values) {
		return new Vector(this.x + values, this.y + values, this.z + values);
	}

	public Vector sub (final Vector vector) {
		return new Vector(this.x - vector.x, this.y - vector.y, this.z - vector.z);
	}

	/** Subtracts the given value from all components of this vector
	 *
	 * @param value The value
	 * @return This vector for chaining */
	public Vector sub (double value) {
		return new Vector(this.x - value, this.y - value, this.z - value);
	}

	public Vector scl (double scalar) {
		return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
	}

	public Vector scl (final Vector vec) {
		return new Vector(this.x * vec.x, this.y * vec.y, this.z * vec.z);
	}

	public double len () {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double len (double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double len2 () {
		return x * x + y * y + z * z;
	}

	public double len2 (double x, double y, double z) {
		return x * x + y * y + z * z;
	}

	/** @param vector The other vector
	 * @return Whether this and the other vector are equal */
	public boolean idt (final Vector vector) {
		return x == vector.x && y == vector.y && z == vector.z;
	}

	public double distance (Vector vector) {
		final double a = vector.x - x;
		final double b = vector.y - y;
		final double c = vector.z - z;
		return Math.sqrt(a * a + b * b + c * c);
	}

	public double distance2 (Vector vector) {
		final double a = vector.x - x;
		final double b = vector.y - y;
		final double c = vector.z - z;
		return a * a + b * b + c * c;
	}

	public Vector normalize () {
		final double len2 = this.len2();
		if (len2 == 0f || len2 == 1f) return this;
		return this.scl(1f / Math.sqrt(len2));
	}

	public double dot (final Vector vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/** Sets this vector to the cross product between it and the other vector.
	 * @param vector The other vector
	 * @return This vector for chaining */
	public Vector cross (final Vector vector) {
		return new Vector(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
	}

	/** Left-multiplies the vector by the given matrix, assuming the fourth (w) component of the vector is 1.
	 * @param matrix The matrix
	 * @return This vector for chaining */
	public Vector mul (final Matrix4 matrix) {
		final double l_mat[] = matrix.val;
		return new Vector(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M01] + z * l_mat[Matrix4.M02] + l_mat[Matrix4.M03], x
				* l_mat[Matrix4.M10] + y * l_mat[Matrix4.M11] + z * l_mat[Matrix4.M12] + l_mat[Matrix4.M13], x * l_mat[Matrix4.M20] + y
				* l_mat[Matrix4.M21] + z * l_mat[Matrix4.M22] + l_mat[Matrix4.M23]);
	}

	/** Multiplies the vector by the transpose of the given matrix, assuming the fourth (w) component of the vector is 1.
	 * @param matrix The matrix
	 * @return This vector for chaining */
	public Vector traMul (final Matrix4 matrix) {
		final double l_mat[] = matrix.val;
		return new Vector(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M10] + z * l_mat[Matrix4.M20] + l_mat[Matrix4.M30], x
				* l_mat[Matrix4.M01] + y * l_mat[Matrix4.M11] + z * l_mat[Matrix4.M21] + l_mat[Matrix4.M31], x * l_mat[Matrix4.M02] + y
				* l_mat[Matrix4.M12] + z * l_mat[Matrix4.M22] + l_mat[Matrix4.M32]);
	}

	/** Multiplies the vector by the given {@link Quaternion}.
	 * @return This vector for chaining */
	public Vector mul (final Quaternion quat) {
		return quat.transform(this);
	}

	/** Multiplies this vector by the first three columns of the matrix, essentially only applying rotation and scaling.
	 *
	 * @param matrix The matrix
	 * @return This vector for chaining */
	public Vector rot (final Matrix4 matrix) {
		final double l_mat[] = matrix.val;
		return new Vector(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M01] + z * l_mat[Matrix4.M02], x * l_mat[Matrix4.M10] + y
				* l_mat[Matrix4.M11] + z * l_mat[Matrix4.M12], x * l_mat[Matrix4.M20] + y * l_mat[Matrix4.M21] + z * l_mat[Matrix4.M22]);
	}

	/** Multiplies this vector by the transpose of the first three columns of the matrix. Note: only works for translation and
	 * rotation, does not work for scaling. For those, use {@link #rot(Matrix4)} with {@link Matrix4#inverse()}.
	 * @param matrix The transformation matrix
	 * @return The vector for chaining */
	public Vector unrotate (final Matrix4 matrix) {
		final double l_mat[] = matrix.val;
		return new Vector(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M10] + z * l_mat[Matrix4.M20], x * l_mat[Matrix4.M01] + y
				* l_mat[Matrix4.M11] + z * l_mat[Matrix4.M21], x * l_mat[Matrix4.M02] + y * l_mat[Matrix4.M12] + z * l_mat[Matrix4.M22]);
	}

	/** Translates this vector in the direction opposite to the translation of the matrix and the multiplies this vector by the
	 * transpose of the first three columns of the matrix. Note: only works for translation and rotation, does not work for
	 * scaling. For those, use {@link #mul(Matrix4)} with {@link Matrix4#inverse()}.
	 * @param matrix The transformation matrix
	 * @return The vector for chaining */
	public Vector untransform (final Matrix4 matrix) {
		final double l_mat[] = matrix.val;
		x -= l_mat[Matrix4.M03];
		y -= l_mat[Matrix4.M03];
		z -= l_mat[Matrix4.M03];
		return new Vector(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M10] + z * l_mat[Matrix4.M20], x * l_mat[Matrix4.M01] + y
				* l_mat[Matrix4.M11] + z * l_mat[Matrix4.M21], x * l_mat[Matrix4.M02] + y * l_mat[Matrix4.M12] + z * l_mat[Matrix4.M22]);
	}

	/** Rotates this vector by the given angle in degrees around the given axis.
	 *
	 * @param axis the axis
	 * @param degrees the angle in degrees
	 * @return This vector for chaining */
	public Vector rotate (final Vector axis, double degrees) {
		tmpMat.setToRotation(axis, degrees);
		return this.mul(tmpMat);
	}

	public boolean isUnit (final double margin) {
		return Math.abs(len2() - 1f) < margin;
	}

	public boolean isZero (final double margin) {
		return len2() < margin;
	}

	public boolean isOnLine (Vector other, double epsilon) {
		return len2(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x) <= epsilon;
	}

	public boolean isCollinear (Vector other, double epsilon) {
		return isOnLine(other, epsilon) && hasSameDirection(other);
	}


	public boolean isCollinearOpposite (Vector other, double epsilon) {
		return isOnLine(other, epsilon) && hasOppositeDirection(other);
	}

	public boolean isPerpendicular (Vector vector, double epsilon) {
		return MathUtils.isZero(dot(vector), epsilon);
	}

	public boolean hasSameDirection (Vector vector) {
		return dot(vector) > 0;
	}

	public boolean hasOppositeDirection (Vector vector) {
		return dot(vector) < 0;
	}

	// TODO hash and equals

	public boolean epsilonEquals (final Vector other, double epsilon) {
		return other != null && Math.abs(other.x - x) <= epsilon && Math.abs(other.y - y) <= epsilon && Math.abs(other.z - z) <= epsilon;
	}

	public Vector setZero () {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		return this;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public String toString() {
		return "Vector{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}

	public void Transform(Frame frame) {
		this.x = this.x * frame.ex().getX() + this.y * frame.ey().getX() + this.x * frame.ez().getX() + frame.getRotationVector().getX();
		this.y = this.x * frame.ex().getY() + this.y * frame.ey().getY() + this.x * frame.ez().getY() + frame.getRotationVector().getY();
		this.z = this.x * frame.ex().getZ() + this.y * frame.ey().getZ() + this.x * frame.ez().getZ() + frame.getRotationVector().getZ();
	}
}