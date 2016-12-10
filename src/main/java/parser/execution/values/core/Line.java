package parser.execution.values.core;

import parser.execution.ExecutionException;

/**
 * Created by gregor on 05.11.16..
 */
public class Line {
    
    private Frame frame; 

    private Vector startV;
    private Vector endV;
    private Vector ex;
    private Vector ey;
    private Vector ez;


    private void initLine(Vector startV, Vector endV) {
        this.startV = startV;
        this.endV = endV;
        // kreiraj frame
        // linija lezi u ravni xy
        // jedinicni vektor frame.ex je jednak jedinicnom vektoru dv = (endPt - startPt).normalize()
        frame = new Frame();

        Vector dv = endV.sub(startV);
        if(dv.len() != 0.0)
        {
            dv = dv.normalize();
            Vector dvXY = new Vector(dv.getX(), dv.getY(), 0.0);
            double a = 0.0;
            double b = 0.0;
            if(dvXY.isZero(10e-7))
            {
                b = -90.0;
                if (startV.getZ() > endV.getZ()) b = -b;
                frame.Rotate(ey, b);
            }
            else
            {
                a = Math.toDegrees(Math.atan2(dv.getY(), dv.getX()));
                b = -dvXY.dot(dv);
                if (startV.getZ() > endV.getZ()) b = -b;
                frame.Rotate(ez, a);
                frame.Rotate(ey, b);
            }
            frame.Translate(startV);
        }
        else {
            throw new ExecutionException("Call the dealer");
        }
    }

    public Line(Vector startPt, Vector endPt) {
        initLine(startPt, endPt);
    }

    public void transform(Frame ft) {
        frame = frame.Transform(ft);
        startV.Transform(frame);
        endV.Transform(frame);
    }

    public void translate(Vector vt) {
        transform(frame.Translate(vt));
    }

    public void rotate(Vector vr, double angleDeg) {
        transform(frame.Rotate(vr, angleDeg));
    }

    public Vector getPointOnLine(double t) {
        Vector e = endV.sub(startV);
        return (startV.add(e.scl(t)));
    }
}
