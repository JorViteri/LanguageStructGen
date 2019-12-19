
import java.util.ArrayList;

public interface FindStr {

	public ArrayList<String> getVerbData(String word);

	public ArrayList<String> getAdjData(String word);

	public ArrayList<String> getNounData(String word);

	public String getNumberAdjective(String word, String genere_base);

	public ArrayList<String> getDataCompoundNoun(String word);
	
	public ArrayList<String> getDataCompoundVerb(String word);

}
