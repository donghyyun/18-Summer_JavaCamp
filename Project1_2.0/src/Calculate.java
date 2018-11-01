
public class Calculate {
	
	public String add(String a, String b) {
		if (a.contains(".") || b.contains("."))
			return String.valueOf(Float.valueOf(a) + Float.valueOf(b));
		else
			return String.valueOf(Integer.valueOf(a) + Integer.valueOf(b));
	}
	
	public String subtract(String a, String b) {
		if (a.contains(".") || b.contains("."))
			return String.valueOf(Float.valueOf(a) - Float.valueOf(b));
		else
			return String.valueOf(Integer.valueOf(a) - Integer.valueOf(b));
	}
	
	public String times(String a, String b) {
		if (a.contains(".") || b.contains("."))
			return String.valueOf(Float.valueOf(a) * Float.valueOf(b));
		else
			return String.valueOf(Integer.valueOf(a) * Integer.valueOf(b));
	}
	
	public String divide(String a, String b) {
		if (String.valueOf(Float.valueOf(a) / Float.valueOf(b)).split(".")[1] == "0")
			return String.valueOf((int)(Float.valueOf(a) / Float.valueOf(b)));
		return String.valueOf(Float.valueOf(a) / Float.valueOf(b));
	}
	
	public String modulate(String a, String b) {
		return String.valueOf((int)(Float.valueOf(a) % Float.valueOf(b)));
	}
}