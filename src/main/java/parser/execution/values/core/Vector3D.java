package parser.execution.values.core;

public class Vector3D {
    public double x;
    public double y;
    public double z;

    public Vector3D() {
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D v) {
        x = v.x;
        y = v.y;
        z = v.z;
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

    public final Vector3D add(Vector3D B) {
        return new Vector3D(B.x + x, B.y + y, B.z + z);
    }

    public final Vector3D sub(Vector3D B) {
        return (new Vector3D(x - B.x, y - B.y, z - B.z));
    }

    public final double dot(Vector3D B) {
        return (x * B.x + y * B.y + z * B.z);
    }

    public final double dot(double Bx, double By, double Bz) {
        return (x * Bx + y * By + z * Bz);
    }

    public static double dot(Vector3D A, Vector3D B) {
        return (A.x * B.x + A.y * B.y + A.z * B.z);
    }

    public final Vector3D cross(Vector3D B) {
        return new Vector3D(y * B.z - z * B.y, z * B.x - x * B.z, x * B.y - y * B.x);
    }

    public final Vector3D cross(double Bx, double By, double Bz) {
        return new Vector3D(y * Bz - z * By, z * Bx - x * Bz, x * By - y * Bx);
    }

    public static Vector3D cross(Vector3D A, Vector3D B) {
        return new Vector3D(A.y * B.z - A.z * B.y, A.z * B.x - A.x * B.z, A.x * B.y - A.y * B.x);
    }

    public final double length() {
        return (double) Math.sqrt(x * x + y * y + z * z);
    }

    public static double length(Vector3D A) {
        return (double) Math.sqrt(A.x * A.x + A.y * A.y + A.z * A.z);
    }

    public final void normalize() {
        double t = x * x + y * y + z * z;
        if (t != 0 && t != 1) t = (double) (1 / Math.sqrt(t));
        x *= t;
        y *= t;
        z *= t;
    }

    public static Vector3D normalize(Vector3D A) {
        double t = A.x * A.x + A.y * A.y + A.z * A.z;
        if (t != 0 && t != 1) t = (double) (1 / Math.sqrt(t));
        return new Vector3D(A.x * t, A.y * t, A.z * t);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3D vector3D = (Vector3D) o;

        if (aboutEqual(vector3D.getX(), getX())) return false;
        if (aboutEqual(vector3D.getY(), getY())) return false;
        return aboutEqual(vector3D.getZ(), getZ());

    }

    // TODO-1 is this implementation of hash and equals good?
    public static Boolean aboutEqual(double x, double y) {
        double epsilon = Math.max(Math.abs(x), Math.abs(y)) * 1E-15;
        return Math.abs(x - y) <= epsilon;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getX());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getZ());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}