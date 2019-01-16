package start.Calculation;

import java.util.ArrayList;

public class MathClass {

    // ALL OF THE CALCULATIONS ARE BY 60FPS.

    // TIME IS SECONDS

    //    public static double Reverse(Func<double, double> function, double value) => 1 - function(1 - value);
//    public static double ToInOut(Func<double, double> function, double value) => .5 * (value < .5 ? function(2 * value) : (2 - function(2 - 2 * value)));
    ArrayList<double[]> easingArray;
    double duration;

    public MathClass() {
        easingArray = new ArrayList<>();
        duration = 0.5;
        generateEasing();
    }

    /*
    For "fast in/slow out", you can use 0, 0, 1, 1
    For "slow in/fast out", you can use 0, 1, 0, 0
    For "slow in", you can use 1, 0, 1, 1
    For "slow out", you can use 0, 0, 0, 1

    0, 0, 1, 0 - Fast Out -> Pause -> Fast In
    0, 1, 1, 1 - Quite Linear until End (Ease Out)
    0, 1, 0, 1 - Better Ease Out
    1, 1, 0, 1 - Slow -> Fast

     */

    public ArrayList<double[]> getArrayList() {
        return easingArray;
    }


    public void generateEasing() {
        easingArray.add(easeCalculation());
        easingArray.add(easeCalculation(1, 0, 1, 1));
        easingArray.add(easeCalculation(1, 1, 0, 1));
        easingArray.add(easeCalculation(0, 0, 1, 0));
    }

    private double[] easeCalculation() {
        double[] movement;
        int counter = 0;

        int frames = (int) (Math.ceil((duration) * 60.0)); // Second * Frames (60FPS)
        movement = new double[frames];

        for (double t = 0; t <= 1; t += (1 / (double) frames)) {
            try {
                movement[counter] = t;
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            counter++;
        }
        return movement;
    }

    private double[] easeCalculation(int x1, int y1, int x2, int y2) {
        double[] movement;
        int counter = 0;

        int frames = (int) (Math.ceil((duration) * 60.0)); // Second * Frames (60FPS)
        movement = new double[frames];

        SplineInterpolator si = new SplineInterpolator(x1, y1, x2, y2);
        for (double t = 0; t <= 1; t += (1 / (double) frames)) {
            try {
                movement[counter] = si.interpolate(t);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            counter++;
        }
        return movement;
    }

    // public static Func<double, double> CubicOut = x => Reverse(CubicIn, x);
}
