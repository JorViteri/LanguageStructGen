import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class JsonCreatorENG implements JsonCreator {
	private final static int spacesToIndentEachLevel = 3;

	@Override
	public void nounJson() {  
		FindStr finder = new FindStrENG();
		ArrayList<String> sing_plu = new ArrayList<>();
		TextChecker checker = new TextChecker();
		String iliOffset;
		JSONObject aux_spa = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject word_data = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		ArrayList<String> results_eng = new ArrayList<>();
		try {
			File file = new File("resources/ILI data/ILINouns.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				word_data = new JSONObject();
				iliOffset = tomJsonObject.getString(key);
				results_eng = access.wordnetQuery(iliOffset, false);
				for (String s : results_eng) {
					if (checker.compoundWord(s)){
						sing_plu = finder.getDataCompoundNoun(s);
					} else{
						sing_plu = finder.getNounData(s);
					}
					data = new JSONObject();
					data.put("singular", sing_plu.get(0));
					data.put("plural", sing_plu.get(1));
					word_data.put(s, data);
					sing_plu.clear();
				}
				aux_spa.put(key, word_data);
			}
			OutputStream outputStream = new FileOutputStream("resources/ENG_Synsets/ENG_names.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(aux_spa.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void adjJson() {
		ArrayList<String> data = new ArrayList<>();
		String iliOffset;
		JSONObject aux_spa = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		ArrayList<String> results_spa = new ArrayList<>();
		try {
			File file = new File("resources/ILI data/ILIAdjectives.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				iliOffset = tomJsonObject.getString(key);
				results_spa = access.wordnetQuery(iliOffset, false);
				for (String s : results_spa) {
					aux_spa.append(key, s);
					data.clear();
				}
			}
			OutputStream outputStream = new FileOutputStream("resources/ENG_Synsets/ENG_adjectives.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(aux_spa.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void verbJson() {
		TextChecker checker = new TextChecker();
		FindStr finder = new FindStrENG();
		ArrayList<String> data = new ArrayList<>();
		String iliOffset;
		JSONObject aux = new JSONObject();
		JSONObject result = new JSONObject();
		JSONObject vdata = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		try {
			File file = new File("resources/ILI data/ILIVerbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				aux =new JSONObject();
				iliOffset = tomJsonObject.getString(key);
				ArrayList<String> results_spa = access.wordnetQuery(iliOffset, false);
				for (String s : results_spa) {
					if (checker.compoundWord(s)){
						data = finder.getDataCompoundVerb(s);
					} else{
						data = finder.getVerbData(s);
					}
					vdata = new JSONObject();
					vdata.put("Present", data.get(0));
					vdata.put("Singular", data.get(1));
					vdata.put("Past", data.get(2));
					aux.put(s, vdata);
					data.clear();
				}
				result.put(key, aux);
			}
			OutputStream outputStream = new FileOutputStream("resources/ENG_Synsets/ENG_verbs.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(result.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void advJson() {
		ArrayList<String> data = new ArrayList<>();
		String iliOffset;
		JSONObject aux_spa = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		ArrayList<String> results_spa = new ArrayList<>();
		try {
			File file = new File("resources/ILI data/ILIAdverbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				iliOffset = tomJsonObject.getString(key);
				results_spa = access.wordnetQuery(iliOffset, false);
				for (String s : results_spa) {
					aux_spa.append(key, s);
					data.clear();
				}
			}
			OutputStream outputStream = new FileOutputStream("resources/ENG_Synsets/ENG_adverbs.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(aux_spa.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
