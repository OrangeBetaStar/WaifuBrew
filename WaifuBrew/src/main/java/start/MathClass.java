package start;

public class MathClass {

    // ALL OF THE CALCULATIONS ARE BY 60FPS.

    // TIME IS SECONDS

//    public static double Reverse(Func<double, double> function, double value) => 1 - function(1 - value);
//    public static double ToInOut(Func<double, double> function, double value) => .5 * (value < .5 ? function(2 * value) : (2 - function(2 - 2 * value)));

    public MathClass() {

    }

    public int[] easeOut (int startTime, int endTime, int startValue, int endValue) {
        int frames = (endTime - startTime) * 60; // Second * Frames (60FPS)
        int relativeDist = endValue - startValue;

        SplineInterpolator si = new SplineInterpolator(1, 0, 0, 1);
        for (double t = 0; t <= 1; t += (1/(double)frames)) {
            System.out.println((relativeDist * si.interpolate(t)) + startValue);
        }

        return new int[10];
    }

    // public static Func<double, double> CubicOut = x => Reverse(CubicIn, x);
}
