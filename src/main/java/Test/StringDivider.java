package Test;

public class StringDivider {

	public static void main(String[] args) {
		String s = "bcdbcd";
		String t = "bcdbcd";
		System.out.println(divideString(s, t));
	}

	public static Integer divideString(String s, String t) {
		Integer s_count=0;
		Integer u=0;
		if(s.contains(t)) {
			s=s.replace(t, "*");
			System.out.println(s);
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i)=='*') {
					s_count++;
				} else {
					s_count=0;
				}
			}
		} else {
			u = -1;
		}
		System.out.println(s_count);
		String u_word = "";
		String u_rev = "";
		if(s_count > 0) {
			for (int i = 0; i < t.length(); i++) {
				u_word = u_word.concat(String.valueOf(t.charAt(i)));
				System.out.println(u_word);
				if(t.endsWith(u_word)) {
					u = u_word.length();
					break;
				} else {
					u = -1;
				}
			}
		} else {
			u = -1;
		}
		return u;
	}
}
