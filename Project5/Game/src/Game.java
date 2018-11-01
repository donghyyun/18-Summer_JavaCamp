import java.awt.Point;
import java.util.ArrayList;


public class Game {
	//the point information which saves stone's location respectively  
	private ArrayList<Point> bsp = new ArrayList<Point>();
	private ArrayList<Point> wsp = new ArrayList<Point>();
	
	public Game() {bsp.add(new Point(400,400));}
	
	//method that add stone's location
	public void addBlackStone(Point p) {bsp.add(p);}
	public void addWhiteStone(Point p) {wsp.add(p);}
	
	//return information about stone's location respectively
	public ArrayList<Point> getBsp(){return bsp;}
	public ArrayList<Point> getWsp(){return wsp;}

	//include given point, judge the winner & variable 'times' means which stone's color should be judged
	public String judge(Point p, int times) {
		ArrayList<Point> judgelist;
		
		if (times % 4 <= 2) {
			judgelist = wsp;
			if (judgeUp(p, judgelist) + judgeDown(p, judgelist) >= 7					//because in recursive function, oneself is overlap
					|| judgeLeft(p, judgelist)+ judgeRight(p, judgelist) >= 7
					|| judgeLeftUp(p, judgelist) + judgeRightDown(p, judgelist) >= 7
					|| judgeLeftDown(p, judgelist) + judgeRightUp(p, judgelist) >= 7)
				return "White";
		}
		else {
			judgelist = bsp;
			if (judgeUp(p, judgelist) + judgeDown(p, judgelist) >= 7
					|| judgeLeft(p, judgelist)+ judgeRight(p, judgelist) >= 7
					|| judgeLeftUp(p, judgelist) + judgeRightDown(p, judgelist) >= 7
					|| judgeLeftDown(p, judgelist) + judgeRightUp(p, judgelist) >= 7)
				return "Black";
		}
		return null;
	}
	
	//include itself, count the number of same color stones for every direction
	public int judgeUp(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeUp(new Point(p.x, p.y - 40), list);
	}
	
	public int judgeDown(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeDown(new Point(p.x, p.y + 40), list);
	}
	
	public int judgeLeft(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeLeft(new Point(p.x - 40, p.y), list); 
	}
	
	public int judgeRight(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeRight(new Point(p.x + 40, p.y), list); 
	}
	
	public int judgeRightUp(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeRightUp(new Point(p.x + 40, p.y - 40), list); 
	}
	
	public int judgeRightDown(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeRightDown(new Point(p.x + 40, p.y + 40), list); 
	}
	
	public int judgeLeftUp(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeLeftUp(new Point(p.x - 40, p.y - 40), list); 
	}
	
	public int judgeLeftDown(Point p, ArrayList<Point> list) {
		if (!list.contains(p))
			return 0;
		
		return 1 + judgeLeftDown(new Point(p.x - 40, p.y + 40), list); 
	}
}
