package Test;

import java.util.ArrayList;
import java.util.List;

public class StringCompare {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(compareString("company", "ompcany"));
	}
	
	public static Boolean compareString (String str1, String str2) {
		Boolean match = false;
		List<Boolean> matches = new ArrayList<Boolean>();
		for (int i = 0; i < str1.length(); i++) {
			for (int j = 0; j < str2.length(); j++) {
				if(str1.charAt(i)==str2.charAt(j)) {
					match = true;
					break;
				} else {
					match = false;
				}
			}
			matches.add(match);
		}
		if(matches.contains(false)) {
			return false;
		} else {
			return true;
		}
	}

}
