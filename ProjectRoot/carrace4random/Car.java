
package carrace4random;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author Zahin
 */
public class Car {
    public double posX;    // Distance between origin and center position of automobile, along X-axis (in cm)
    public double posY;    // Distance between origin and center position of automobile, along Y-axis (in cm)
    public double directionX; // Direction vector's X coefficient
    public double directionY; // Direction vector's Y coefficient
    public double speed;   // Stores speed of the automobile
    public double fuel;
    public double angle;
    public double UPx;
    public double UPy;
    public Car()
    {
     angle=3.1416/2;

        speed=0.0;
        fuel=30.0;
    }
   public void TurnLeft()
   {
    double i,j,a,b,p;
    double turnAngle;
    a=acos(directionX);
    turnAngle=5.0;

    p=(turnAngle)*3.1416/180.0;
    directionX=cos(p+angle);
    directionY=sin(p+angle);
    angle=p+angle;

   
}
   public void TurnRight()
{
    double i,j,a,b,p;
    double turnAngle;
    
    a=acos(directionX);
    turnAngle=5.0;

    p=(turnAngle)*3.1416/180.0;
    directionX=cos(angle-p);
    directionY=sin(angle-p);
    angle=angle-p;
    SetInitialDirection(directionX,directionY);

}
 public  void SetInitialPosition(double x, double y)
    {
        posX = x;
        posY = y;
    }

 public   void SetInitialDirection(double x, double y)
    {
        directionX = x;
        directionY = y;
    }
  public   void SetUPxUPy(double x, double y)
    {
        UPx = x;
        UPy = y;
    }
 public void Move()
{
    
        double i,j,k,r,newposX,newposY;
        r=(speed)/(60*60);
        i=r*directionX*100;
        j=r*directionY*100;
        newposX=i+ posX;
        newposY=j+posY;
        posX=newposX;
        posY=newposY;

       
 
}
public void IncreaseSpeed()
{
    double changeInSpeed, maxSpeed;
    changeInSpeed=10;

    maxSpeed=130;
    if((speed +changeInSpeed) <= maxSpeed)
        speed+=changeInSpeed;
    else speed=maxSpeed;


}
public void DecreaseSpeed()
{
    double changeInSpeed, maxSpeed;
   changeInSpeed=10;

    maxSpeed=130;


    if((speed -changeInSpeed) >= 0)
        speed-=changeInSpeed;
    else speed=0;


}
}

