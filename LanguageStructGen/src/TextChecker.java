import java.util.regex.Pattern;

public class TextChecker {
	public TextChecker() {

	}

	public boolean checkWordSpanish(String word) {
		if (Pattern.matches(".*[áéíóúñ].*", word)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean checkWordWeird(String word){
		if (Pattern.matches(".*[�].*", word)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean compoundWord(String word){
		String[] splited = word.split("_");
		if (splited.length>1){
			return true;
		} else {
			return false;
		}
	}
}
