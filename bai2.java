import java.util.Random;
import java.util.Scanner;

public class bai2 {
  public static double estimateCircleArea(double numPoints){
    Random rand = new Random();
    int pointsInside = 0;

    for (int i = 0; i < numPoints; i++){
      double x = (rand.nextDouble() * 2 - 1 ) * 1;
      double y = (rand.nextDouble() * 2 - 1 ) * 1;

      if (x * x + y * y <= 1 * 1){
        pointsInside++;
      }
    }
    return 4.0 * pointsInside / numPoints;
  }

  public static void main(String[] args){
    double totalSample = 100000000;

    double s = estimateCircleArea(totalSample);
    System.out.println("Bán kính ước tính: " + s);
  }
}
