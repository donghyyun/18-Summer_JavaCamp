
public class Calculate {
	
	public boolean isInteger(String value) {
		if (value.contains("-"))
			value = value.substring(1);
		
		if (value.endsWith("0"))
			return true;
		return false;
	}
	
	public String add(String a, String b) {
		double x = 0, y = 0;
		double sum = 0;
		x = Double.valueOf(a);
		y = Double.valueOf(b);
		sum = x + y;
		if (!isInteger(String.valueOf(sum)))
			return String.valueOf(sum);
		else
			return String.valueOf((int)sum);
	}
	
	public String subtract(String a, String b) {
		if (a.contains(".") || b.contains(".")) {
			if (!isInteger(String.valueOf(Double.valueOf(a) - Double.valueOf(b))))
				return String.valueOf(Double.valueOf(a) - Double.valueOf(b));
			return String.valueOf((int)(Double.valueOf(a) - Double.valueOf(b)));
		}
		return String.valueOf((Integer.valueOf(a) - Integer.valueOf(b)));
	}
	
	public String times(String a, String b) {
		double x = 0, y = 0;
		double time = 0;
		x = Double.valueOf(a);
		y = Double.valueOf(b);
		time = x * y;
		if (!isInteger(String.valueOf(time)))
			return String.valueOf(time);
		else
			return String.valueOf((int)time);
	}
	
	public String divide(String a, String b) {
		double x = 0, y = 0;
		double div = 0;
		x = Double.valueOf(a);
		y = Double.valueOf(b);
		div = x / y;
		if (!isInteger(String.valueOf(div)))
			return String.valueOf(div);
		else
			return String.valueOf((int)div);
	}
	
	public String modulate(String a, String b) {
		return String.valueOf((int)(Double.valueOf(a) % Double.valueOf(b)));
	}
}