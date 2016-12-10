package parser.execution.values.core;


public class Frame {
    
    private Matrix4 m;

    public Frame() {
        m = new Matrix4();
    }

    public Frame(Matrix4 m) {
        this.m = m;
    }

    public Frame(Frame f)
    {
        m = f.m;
    }

    public Matrix4 getMatrix() {
        return m;
    }

    public void setMatrix(Matrix4 m) {
        this.m = m;
    }

    public Frame Inverse() {
        Frame f = new Frame(this);
        f.m.inverse();
        return f;
    }

    public Frame Transform(Frame ft) {
        Frame f = new Frame(this);
        f.m = ft.m.mul(this.m);
        return f;
    }

    public Frame Translate(Vector vt)
    {
        Frame f = new Frame(this); // copy constructor
        return new Frame(f.m.setTranslation(vt));
    }

    //rotate matrix about arbitrary axis
    public Frame Rotate(Vector vr, double angleDeg)
    {
        Frame f = new Frame(this); // copy constructor
        return new Frame(f.m.rotate(vr, angleDeg));
    }

    public Vector getOrigin() {
        return m.getOrigin();
    }

    // vraca jedinicni vektor za x os
    public Vector ex() {
        return m.getxAxis();
    }

    // vraca jedinicni vektor za y os
    public Vector ey() {
        return m.getyAxis();
    }
    // vraca jedinicni vektor za z os
    public Vector ez() {
        return m.getzAxis();
    }

    public void setEx(Vector v) {
        m.setxAxis(v);
    }

    // vraca jedinicni vektor za y os
    public void setEy(Vector v) {
        m.setyAxis(v);
    }
    // vraca jedinicni vektor za z os
    public void setEz(Vector v) {
        m.setzAxis(v);
    }

    public Vector getRotationVector() {
        return m.getRotation();
    }

    public double getAngle() {
        return m.getAngelDeg(); // u stupnjevima
    }

}