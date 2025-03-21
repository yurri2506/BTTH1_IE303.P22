import java.util.Random;
import java.util.Scanner;

public class bai1 {
  public static double estimateCircleArea(double radius, int numPoints){
    Random rand = new Random();
    int pointsInside = 0;

    for (int i = 0; i < numPoints; i++){
      double x = (rand.nextDouble() * 2 - 1 ) * radius;
      double y = (rand.nextDouble() * 2 - 1 ) * radius;

      if (x * x + y * y <= radius * radius){
        pointsInside++;
      }
    }
    return 4.0 * radius * radius * pointsInside / numPoints;
  }

  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    System.out.print("Nhập bán kính của hình tròn: ");
    double r = input.nextDouble();
    int totalSample = 1000000;

    double s = estimateCircleArea(r, totalSample);
    System.out.println("Diện tích ước tính: " + s);
  }
}
