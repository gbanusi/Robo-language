package parser.execution.values.core;

public class Point {

    private Frame frame;
    private Vector originV;

    private Vector ex;
    private Vector ey;
    private Vector ez;

    private void init(Vector origin)
    {
        ex = new Vector(1.0, 0.0, 0.0);
        ey = new Vector(0.0, 1.0, 0.0);
        ez = new Vector(0.0, 0.0, 1.0);

        originV = origin;

        frame = new Frame();
        frame.Translate(originV);
    }

    public Point(Vector origin) {
        init(origin);
    }

    public void Transform(Frame ft) {
        frame = frame.Transform(ft);
        originV = frame.getOrigin();        
    }

    public void Translate(Vector vt) {
        Transform(frame.Translate(vt));
    }

    public void Rotate(Vector vr, double angleDeg) {
        Transform(frame.Rotate(vr, angleDeg));
    }

    public void AlignOrientation(Frame af)
    {
        frame.setEx(af.ex());
        frame.setEy(af.ey());
        frame.setEz(af.ez());
    }
}