import java.awt.Point;
import java.util.ArrayList;

//save the location
public class ValidLocation {
	public ArrayList<Integer> x = new ArrayList<Integer>();
	public ArrayList<Integer> y = new ArrayList<Integer>();
	private ArrayList<Point> point = new ArrayList<Point>();
	
	
	public void createValidpoint() {
		for (Integer i : x) {
			for (Integer j : y)
				point.add(new Point(i,j));
		}
	}
	
	//variable 'p' is mouse's location, this method return closest exact location
	public Point trimpoint(Point p) {
		for (Point p1 : point) {
			if (p1.distance(p) < 28)
				return p1;
		}
		return null;
	}
}
