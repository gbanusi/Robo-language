package parser.execution.values.core;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import java.io.Serializable;

/**
 * A simple quaternion class.
 *
 * @author badlogicgames@gmail.com
 * @author vesuvio
 * @author xoppa
 * @see <a href="http://en.wikipedia.org/wiki/Quaternion">http://en.wikipedia.org/wiki/Quaternion</a>
 */
public class Quaternion implements Serializable {
    private static final long serialVersionUID = -7661875440774897168L;
    private static Quaternion tmp1 = new Quaternion(0, 0, 0, 0);
    private static Quaternion tmp2 = new Quaternion(0, 0, 0, 0);

    public double x;
    public double y;
    public double z;
    public double w;

    /**
     * Constructor, sets the four components of the quaternion.
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     * @param w The w-component
     */
    public Quaternion(double x, double y, double z, double w) {
        this.set(x, y, z, w);
    }

    public Quaternion() {
        idt();
    }

    /**
     * Constructor, sets the quaternion components from the given quaternion.
     *
     * @param quaternion The quaternion to copy.
     */
    public Quaternion(Quaternion quaternion) {
        this.set(quaternion);
    }

    /**
     * Constructor, sets the quaternion from the given axis vector and the angle around that axis in degrees.
     *
     * @param axis  The axis
     * @param angle The angle in degrees.
     */
    public Quaternion(Vector3 axis, double angle) {
        this.set(axis, angle);
    }

    /**
     * Sets the components of the quaternion
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     * @param w The w-component
     * @return This quaternion for chaining
     */
    public Quaternion set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Sets the quaternion components from the given quaternion.
     *
     * @param quaternion The quaternion.
     * @return This quaternion for chaining.
     */
    public Quaternion set(Quaternion quaternion) {
        return this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    /**
     * Sets the quaternion components from the given axis and angle around that axis.
     *
     * @param axis  The axis
     * @param angle The angle in degrees
     * @return This quaternion for chaining.
     */
    public Quaternion set(Vector3 axis, double angle) {
        return setFromAxis(new Vector3(axis), angle);
    }

    /**
     * @return a copy of this quaternion
     */
    public Quaternion cpy() {
        return new Quaternion(this);
    }

    /**
     * @return the euclidean length of this quaternion
     */
    public double len() {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * Sets the quaternion to the given euler angles in degrees.
     *
     * @param yaw   the rotation around the y axis in degrees
     * @param pitch the rotation around the x axis in degrees
     * @param roll  the rotation around the z axis degrees
     * @return this quaternion
     */
    public Quaternion setEulerAngles(double yaw, double pitch, double roll) {
        return setEulerAnglesRad(yaw * MathUtils.degreesToRadians, pitch * MathUtils.degreesToRadians, roll
                * MathUtils.degreesToRadians);
    }

    /**
     * Sets the quaternion to the given euler angles in radians.
     *
     * @param yaw   the rotation around the y axis in radians
     * @param pitch the rotation around the x axis in radians
     * @param roll  the rotation around the z axis in radians
     * @return this quaternion
     */
    public Quaternion setEulerAnglesRad(double yaw, double pitch, double roll) {
        final double hr = roll * 0.5f;
        final double shr = Math.sin(hr);
        final double chr = Math.cos(hr);
        final double hp = pitch * 0.5f;
        final double shp = Math.sin(hp);
        final double chp = Math.cos(hp);
        final double hy = yaw * 0.5f;
        final double shy = Math.sin(hy);
        final double chy = Math.cos(hy);
        final double chy_shp = chy * shp;
        final double shy_chp = shy * chp;
        final double chy_chp = chy * chp;
        final double shy_shp = shy * shp;

        x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
        y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
        z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
        w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
        return this;
    }

    /**
     * Get the pole of the gimbal lock, if any.
     *
     * @return positive (+1) for north pole, negative (-1) for south pole, zero (0) when no gimbal lock
     */
    public int getGimbalPole() {
        final double t = y * x + z * w;
        return t > 0.499f ? 1 : (t < -0.499f ? -1 : 0);
    }

    /**
     * Get the roll euler angle in radians, which is the rotation around the z axis. Requires that this quaternion is normalized.
     *
     * @return the rotation around the z axis in radians (between -PI and +PI)
     */
    public double getRollRad() {
        final int pole = getGimbalPole();
        return pole == 0 ? MathUtils.atan2(2f * (w * z + y * x), 1f - 2f * (x * x + z * z)) : pole * 2f
                * MathUtils.atan2(y, w);
    }

    /**
     * Get the roll euler angle in degrees, which is the rotation around the z axis. Requires that this quaternion is normalized.
     *
     * @return the rotation around the z axis in degrees (between -180 and +180)
     */
    public double getRoll() {
        return getRollRad() * MathUtils.radiansToDegrees;
    }

    /**
     * Get the pitch euler angle in radians, which is the rotation around the x axis. Requires that this quaternion is normalized.
     *
     * @return the rotation around the x axis in radians (between -(PI/2) and +(PI/2))
     */
    public double getPitchRad() {
        final int pole = getGimbalPole();
        return pole == 0 ? Math.asin(MathUtils.clamp(2f * (w * x - z * y), -1f, 1f)) : pole * MathUtils.PI * 0.5f;
    }

    /**
     * Get the pitch euler angle in degrees, which is the rotation around the x axis. Requires that this quaternion is normalized.
     *
     * @return the rotation around the x axis in degrees (between -90 and +90)
     */
    public double getPitch() {
        return getPitchRad() * MathUtils.radiansToDegrees;
    }

    /**
     * Get the yaw euler angle in radians, which is the rotation around the y axis. Requires that this quaternion is normalized.
     *
     * @return the rotation around the y axis in radians (between -PI and +PI)
     */
    public double getYawRad() {
        return getGimbalPole() == 0 ? MathUtils.atan2(2f * (y * w + x * z), 1f - 2f * (y * y + x * x)) : 0f;
    }

    /**
     * Get the yaw euler angle in degrees, which is the rotation around the y axis. Requires that this quaternion is normalized.
     *
     * @return the rotation around the y axis in degrees (between -180 and +180)
     */
    public double getYaw() {
        return getYawRad() * MathUtils.radiansToDegrees;
    }

    /**
     * @return the length of this quaternion without square root
     */
    public double len2() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Normalizes this quaternion to unit length
     *
     * @return the quaternion for chaining
     */
    public Quaternion nor() {
        double len = len2();
        if (len != 0.f && !MathUtils.isEqual(len, 1f)) {
            len = Math.sqrt(len);
            w /= len;
            x /= len;
            y /= len;
            z /= len;
        }
        return this;
    }

    /**
     * Conjugate the quaternion.
     *
     * @return This quaternion for chaining
     */
    public Quaternion conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    // TODO : this would better fit into the vector3 class

    /**
     * Transforms the given vector using this quaternion
     *
     * @param v Vector to transform
     */
    public Vector3 transform(Vector3 v) {
        tmp2.set(this);
        tmp2.conjugate();
        tmp2.mulLeft(tmp1.set(v.x, v.y, v.z, 0)).mulLeft(this);

        v.x = tmp2.x;
        v.y = tmp2.y;
        v.z = tmp2.z;
        return v;
    }

    /**
     * Multiplies this quaternion with another one in the form of this = this * other
     *
     * @param other Quaternion to multiply with
     * @return This quaternion for chaining
     */
    public Quaternion mul(final Quaternion other) {
        final double newX = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
        final double newY = this.w * other.y + this.y * other.w + this.z * other.x - this.x * other.z;
        final double newZ = this.w * other.z + this.z * other.w + this.x * other.y - this.y * other.x;
        final double newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    /**
     * Multiplies this quaternion with another one in the form of this = other * this
     *
     * @param other Quaternion to multiply with
     * @return This quaternion for chaining
     */
    public Quaternion mulLeft(Quaternion other) {
        final double newX = other.w * this.x + other.x * this.w + other.y * this.z - other.z * y;
        final double newY = other.w * this.y + other.y * this.w + other.z * this.x - other.x * z;
        final double newZ = other.w * this.z + other.z * this.w + other.x * this.y - other.y * x;
        final double newW = other.w * this.w - other.x * this.x - other.y * this.y - other.z * z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    /**
     * Add the x,y,z,w components of the passed in quaternion to the ones of this quaternion
     */
    public Quaternion add(Quaternion quaternion) {
        this.x += quaternion.x;
        this.y += quaternion.y;
        this.z += quaternion.z;
        this.w += quaternion.w;
        return this;
    }

    public void toMatrix(final double[] matrix) {
        final double xx = x * x;
        final double xy = x * y;
        final double xz = x * z;
        final double xw = x * w;
        final double yy = y * y;
        final double yz = y * z;
        final double yw = y * w;
        final double zz = z * z;
        final double zw = z * w;
        // Set matrix from quaternion
        matrix[Matrix4.M00] = 1 - 2 * (yy + zz);
        matrix[Matrix4.M01] = 2 * (xy - zw);
        matrix[Matrix4.M02] = 2 * (xz + yw);
        matrix[Matrix4.M03] = 0;
        matrix[Matrix4.M10] = 2 * (xy + zw);
        matrix[Matrix4.M11] = 1 - 2 * (xx + zz);
        matrix[Matrix4.M12] = 2 * (yz - xw);
        matrix[Matrix4.M13] = 0;
        matrix[Matrix4.M20] = 2 * (xz - yw);
        matrix[Matrix4.M21] = 2 * (yz + xw);
        matrix[Matrix4.M22] = 1 - 2 * (xx + yy);
        matrix[Matrix4.M23] = 0;
        matrix[Matrix4.M30] = 0;
        matrix[Matrix4.M31] = 0;
        matrix[Matrix4.M32] = 0;
        matrix[Matrix4.M33] = 1;
    }

    public Matrix4 toMatrix() {
        final double[] matrix = new double[16];
        toMatrix(matrix);
        return new Matrix4(matrix);
    }

    /**
     * Set this quaternion to the rotation between two vectors.
     *
     * @param v1 The base vector, which should be normalized.
     * @param v2 The target vector, which should be normalized.
     * @return This quaternion for chaining
     */
    public Quaternion setFromCross(final Vector3 v1, final Vector3 v2) {
        final double dot = MathUtils.clamp(v1.dot(v2), -1f, 1f);
        final double angle = Math.acos(dot);
        return setFromAxisRad(new Vector3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x), angle);
    }

    public Quaternion setFromAxisRad(final Vector3 axis, final double radians) {
        return setFromAxisRad(axis.x, axis.y, axis.z, radians);
    }

    /**
     * Sets the quaternion components from the given axis and angle around that axis.
     *
     * @param x       X direction of the axis
     * @param y       Y direction of the axis
     * @param z       Z direction of the axis
     * @param radians The angle in radians
     * @return This quaternion for chaining.
     */
    public Quaternion setFromAxisRad(final double x, final double y, final double z, final double radians) {
        double d = Vector3.len(x, y, z);
        if (d == 0f) return idt();
        d = 1f / d;
        double l_ang = radians < 0 ? MathUtils.PI2 - (-radians % MathUtils.PI2) : radians % MathUtils.PI2;
        double l_sin = Math.sin(l_ang / 2);
        double l_cos = Math.cos(l_ang / 2);
        return this.set(d * x * l_sin, d * y * l_sin, d * z * l_sin, l_cos).nor();
    }

    /**
     * Sets the quaternion to an identity Quaternion
     *
     * @return this quaternion for chaining
     */
    public Quaternion idt() {
        return this.set(0, 0, 0, 1);
    }

    /**
     * @return If this quaternion is an identity Quaternion
     */
    public boolean isIdentity(final double tolerance) {
        return MathUtils.isZero(x, tolerance) && MathUtils.isZero(y, tolerance) && MathUtils.isZero(z, tolerance)
                && MathUtils.isEqual(w, 1f, tolerance);
    }

    // todo : the setFromAxis(v3,double) method should replace the set(v3,double) method

    /**
     * Sets the quaternion components from the given axis and angle around that axis.
     *
     * @param axis    The axis
     * @param degrees The angle in degrees
     * @return This quaternion for chaining.
     */
    public Quaternion setFromAxis(final Vector3 axis, final double degrees) {
        double d = Vector3.len(axis.x, axis.y, axis.z);
        if (d == 0f) return idt();
        d = 1f / d;
        double radians = degrees * MathUtils.degreesToRadians;
        double l_ang = radians < 0 ? MathUtils.PI2 - (-radians % MathUtils.PI2) : radians % MathUtils.PI2;
        double l_sin = Math.sin(l_ang / 2);
        double l_cos = Math.cos(l_ang / 2);
        return this.set(d * axis.x * l_sin, d * axis.y * l_sin, d * axis.z * l_sin, l_cos).nor();
    }

    /**
     * Sets the Quaternion from the given matrix, optionally removing any scaling.
     */
    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix4 matrix) {
        return setFromAxes(normalizeAxes, matrix.val[Matrix4.M00], matrix.val[Matrix4.M01], matrix.val[Matrix4.M02],
                matrix.val[Matrix4.M10], matrix.val[Matrix4.M11], matrix.val[Matrix4.M12], matrix.val[Matrix4.M20],
                matrix.val[Matrix4.M21], matrix.val[Matrix4.M22]);
    }

    /**
     * Sets the Quaternion from the given rotation matrix, which must not contain scaling.
     */
    public Quaternion setFromMatrix(Matrix4 matrix) {
        return setFromMatrix(false, matrix);
    }

    /**
     * <p>
     * Sets the Quaternion from the given x-, y- and z-axis.
     * </p>
     * <p>
     * <p>
     * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/ which in turn took it from Graphics Gem code at
     * ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z.
     * </p>
     *
     * @param normalizeAxes whether to normalize the axes (necessary when they contain scaling)
     * @param xx            x-axis x-coordinate
     * @param xy            x-axis y-coordinate
     * @param xz            x-axis z-coordinate
     * @param yx            y-axis x-coordinate
     * @param yy            y-axis y-coordinate
     * @param yz            y-axis z-coordinate
     * @param zx            z-axis x-coordinate
     * @param zy            z-axis y-coordinate
     * @param zz            z-axis z-coordinate
     */
    public Quaternion setFromAxes(boolean normalizeAxes, double xx, double xy, double xz, double yx, double yy, double yz, double zx,
                                  double zy, double zz) {
        if (normalizeAxes) {
            final double lx = 1f / Vector3.len(xx, xy, xz);
            final double ly = 1f / Vector3.len(yx, yy, yz);
            final double lz = 1f / Vector3.len(zx, zy, zz);
            xx *= lx;
            xy *= lx;
            xz *= lx;
            yx *= ly;
            yy *= ly;
            yz *= ly;
            zx *= lz;
            zy *= lz;
            zz *= lz;
        }
        // the trace is the sum of the diagonal elements; see
        // http://mathworld.wolfram.com/MatrixTrace.html
        final double t = xx + yy + zz;

        // we protect the division by s by ensuring that s>=1
        if (t >= 0) { // |w| >= .5
            double s = Math.sqrt(t + 1); // |s|>=1 ...
            w = 0.5f * s;
            s = 0.5f / s; // so this division isn't bad
            x = (zy - yz) * s;
            y = (xz - zx) * s;
            z = (yx - xy) * s;
        } else if ((xx > yy) && (xx > zz)) {
            double s = Math.sqrt(1.0 + xx - yy - zz); // |s|>=1
            x = s * 0.5f; // |x| >= .5
            s = 0.5f / s;
            y = (yx + xy) * s;
            z = (xz + zx) * s;
            w = (zy - yz) * s;
        } else if (yy > zz) {
            double s = Math.sqrt(1.0 + yy - xx - zz); // |s|>=1
            y = s * 0.5f; // |y| >= .5
            s = 0.5f / s;
            x = (yx + xy) * s;
            z = (zy + yz) * s;
            w = (xz - zx) * s;
        } else {
            double s = Math.sqrt(1.0 + zz - xx - yy); // |s|>=1
            z = s * 0.5f; // |z| >= .5
            s = 0.5f / s;
            x = (xz + zx) * s;
            y = (zy + yz) * s;
            w = (yx - xy) * s;
        }

        return this;
    }


    /**
     * Get the axis angle representation of the rotation in degrees. The supplied vector will receive the axis (x, y and z values)
     * of the rotation and the value returned is the angle in degrees around that axis. Note that this method will alter the
     * supplied vector, the existing value of the vector is ignored. </p> This will normalize this quaternion if needed. The
     * received axis is a unit vector. However, if this is an identity quaternion (no rotation), then the length of the axis may be
     * zero.
     *
     * @param axis vector which will receive the axis
     * @return the angle in degrees
     * @see <a href="http://en.wikipedia.org/wiki/Axis%E2%80%93angle_representation">wikipedia</a>
     * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle">calculation</a>
     */
    public double getAxisAngle(Vector3 axis) {
        return getAxisAngleRad(axis) * MathUtils.radiansToDegrees;
    }

    /**
     * Get the axis-angle representation of the rotation in radians. The supplied vector will receive the axis (x, y and z values)
     * of the rotation and the value returned is the angle in radians around that axis. Note that this method will alter the
     * supplied vector, the existing value of the vector is ignored. </p> This will normalize this quaternion if needed. The
     * received axis is a unit vector. However, if this is an identity quaternion (no rotation), then the length of the axis may be
     * zero.
     *
     * @param axis vector which will receive the axis
     * @return the angle in radians
     * @see <a href="http://en.wikipedia.org/wiki/Axis%E2%80%93angle_representation">wikipedia</a>
     * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle">calculation</a>
     */
    public double getAxisAngleRad(Vector3 axis) {
        if (this.w > 1)
            this.nor(); // if w>1 acos and sqrt will produce errors, this cant happen if quaternion is normalised
        double angle = (2.0 * Math.acos(this.w));
        double s = Math.sqrt(1 - this.w * this.w); // assuming quaternion normalised then w is less than 1, so term always positive.
        if (s < MathUtils.double_ROUNDING_ERROR) { // test to avoid divide by zero, s is always positive due to sqrt
            // if s close to zero then direction of axis not important
            axis.x = this.x; // if it is important that axis is normalised then replace with x=1; y=z=0;
            axis.y = this.y;
            axis.z = this.z;
        } else {
            axis.x = (this.x / s); // normalise axis
            axis.y = (this.y / s);
            axis.z = (this.z / s);
        }

        return angle;
    }

    /**
     * Get the angle in radians of the rotation this quaternion represents. Does not normalize the quaternion. Use
     * {@link #getAxisAngleRad(Vector3)} to get both the axis and the angle of this rotation. Use
     *
     * @return the angle in radians of the rotation
     */
    public double getAngleRad() {
        return (2.0 * Math.acos((this.w > 1) ? (this.w / len()) : this.w));
    }

    /**
     * Get the angle in degrees of the rotation this quaternion represents. Use {@link #getAxisAngle(Vector3)} to get both the axis
     * and the angle of this rotation. to get the angle around a specific axis.
     *
     * @return the angle in degrees of the rotation
     */
    public double getAngle() {
        return getAngleRad() * MathUtils.radiansToDegrees;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quaternion that = (Quaternion) o;

        return !MathUtils.aboutEqual(that.x, x) && !MathUtils.aboutEqual(that.y, y) && !MathUtils.aboutEqual(that.z, z) && Double.compare(that.w, w) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(w);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Quaternion{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}