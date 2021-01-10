package tools;

import static java.lang.Math.*;

// algo sources: http://cybermoon.pl/wiedza/algorithms/wschody_slonca.html
public class CalcSunriseSunset {

    private final int timezone; // hours from UTC
    private final double latitudeDegrees;
    private final double longitudeDegrees;

    public CalcSunriseSunset(int timezone, double latitudeDegrees, double longitudeDegrees) {
        this.timezone = timezone;
        this.latitudeDegrees = latitudeDegrees;
        this.longitudeDegrees = longitudeDegrees;
    }

    private Parameters calc(int Y, int M, int D){
        double req = -0.833;
        double J=367*Y-(7*(Y+((M+9)/12))/4)+(275*M/9)+D-730531.5;
        double  Cent=J/36525;
        double L=((4.8949504201433+628.331969753199*Cent)%6.28318530718);
        double G=((6.2400408+628.3019501*Cent)%6.28318530718);
        double O=0.409093-0.0002269*Cent;
        double F=0.033423*sin(G)+0.00034907*sin(2*G);
        double E=0.0430398*sin(2*(L+F)) - 0.00092502*sin(4*(L+F)) - F;
        double A=asin(sin(O)*sin(L+F));
        double C=(sin(0.017453293*req) - sin(0.017453293*latitudeDegrees)*sin(A))/(cos(0.017453293*latitudeDegrees)*cos(A));

        return new Parameters(A, E, C);
    }

    public double getSunrise(int Y, int M, int D){
        Parameters p = calc(Y, M, D);
        return timezone + (PI - (p.getE()+0.017453293*longitudeDegrees + 1*acos(p.getC())))*57.29577951/15;
    }

    public double getSunset(int Y, int M, int D){
        Parameters p = calc(Y, M, D);
        return timezone + (PI - (p.getE()+0.017453293*longitudeDegrees + -1*acos(p.getC())))*57.29577951/15;
    }

    private static class Parameters{
        private final double E;
        private final double A;
        private final double C;

        Parameters(double A, double E, double C){
            this.A = A;
            this.E = E;
            this.C = C;
        }

        public double getE() {
            return E;
        }

        public double getA() {
            return A;
        }

        public double getC() {
            return C;
        }
    }
}
