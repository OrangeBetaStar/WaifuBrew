package start.Calculation;

public class MathClass {

    // ALL OF THE CALCULATIONS ARE BY 60FPS.

    // TIME IS SECONDS

//    public static double Reverse(Func<double, double> function, double value) => 1 - function(1 - value);
//    public static double ToInOut(Func<double, double> function, double value) => .5 * (value < .5 ? function(2 * value) : (2 - function(2 - 2 * value)));


    public MathClass() {
    }

    public int[] easeOut (double startTime, double endTime, int startValue, int endValue) {
        int[] movement;
        int counter = 0;

        int frames = (int)(Math.ceil((endTime - startTime) * 60.0)); // Second * Frames (60FPS)
        int relativeDist = endValue - startValue;
        movement = new int[frames];

        SplineInterpolator si = new SplineInterpolator(1, 0, 0, 1);
        for (double t = 0; t <= 1; t += (1/(double)frames)) {
            try {
                movement[counter] = (int) ((relativeDist * si.interpolate(t)) + startValue);
            } catch(ArrayIndexOutOfBoundsException e) {
                break;
            }
            counter++;
        }
        return movement;
    }

    // public static Func<double, double> CubicOut = x => Reverse(CubicIn, x);
}
