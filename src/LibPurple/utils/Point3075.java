package LibPurple.utils;


public class Point3075 {
	public double x;
	public double y;

	public Point3075(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point3075()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * leftest = http://www.urbandictionary.com/define.php?term=leftest
	 * @param points
	 * @return the leftest point
	 */
	public static Point3075 leftestPoint(Point3075[] points)
	{
		Point3075 highest = points[0];
		for(Point3075 p : points)
		{
			if(p.getX() < highest.getX())
				highest = p;
		}
		return highest;
	}
	
	public static double distance(Point3075 p1, Point3075 p2)
	{
		return Math.sqrt(Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getX() - p2.getX(), 2));
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public static double getSlope(Point3075 p1, Point3075 p2)
	{
		double dy = p1.getY() - p2.getY();
		double dx = p1.getX() - p2.getX();
		return  dy / dx;
	}
	
	public static double getLine(Point3075 p1, Point3075 p2, double x)
	{
		double m = getSlope(p1, p2);
		return (m * x) - (m * p2.getX()) + p2.getY();
	}
	
}
