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

public class JsonCreatorSPA implements JsonCreator {

	private final static int spacesToIndentEachLevel = 3;

	public JsonCreatorSPA() {

	}

	public void nounJson() {
		String iliOffset;
		FindStr finder = new FindStrSPA();
		TextChecker checker = new TextChecker();
		ArrayList<String> gen_plu = new ArrayList<>();
		JSONObject data= new JSONObject();
		JSONObject word_data = new JSONObject();
		JSONObject aux_spa = new JSONObject();
		ArrayList<String> results_spa = new ArrayList<>();
		MySQLAccess access = new MySQLAccess();
		try {
			File file = new File("resources/ILI data/ILINouns.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				word_data = new JSONObject();
				iliOffset = tomJsonObject.getString(key);
				results_spa = access.wordnetQuery(iliOffset, true);
				for (String s : results_spa) {
					if (checker.compoundWord(s)) {
						gen_plu = finder.getDataCompoundNoun(s);
					} else {
						gen_plu = finder.getNounData(s);
					}
					data = new JSONObject();
					data.put("genere", gen_plu.get(0));
					data.put("plural", gen_plu.get(1));
					word_data.put(s, data);
					gen_plu.clear();

				}
				aux_spa.put(key,word_data);
			}
			OutputStream outputStream = new FileOutputStream("resources/SPA_Synsets/SPA_names.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(aux_spa.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adjJson() {
		String iliOffset;
		FindStr finder = new FindStrSPA();
		ArrayList<String> data = new ArrayList<>();
		JSONObject result = new JSONObject();
		JSONObject data_final = new JSONObject();
		JSONObject aux_spa = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		try {
			File file = new File("resources/ILI data/ILIAdjectives.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				aux_spa = new JSONObject();
				iliOffset = tomJsonObject.getString(key);
				ArrayList<String> results_spa = access.wordnetQuery(iliOffset, true);
				for (String s : results_spa) {
					data = finder.getAdjData(s);
					data_final= new JSONObject();
					data_final.put("type", data.get(0));
					data_final.put("sing_m", data.get(1));
					data_final.put("sing_f", data.get(2));
					data_final.put("plu_m", data.get(3));
					data_final.put("plu_f", data.get(4));

					aux_spa.put(s, data_final);
					data.clear();
				}
				result.put(key, aux_spa);
			}
			OutputStream outputStream = new FileOutputStream("resources/SPA_Synsets/SPA_adjectives.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(result.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void advJson() {
		ArrayList<String> data = new ArrayList<>();
		JSONObject aux_spa = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		String iliOffset;
		ArrayList<String> results_spa = new ArrayList<>();
		try {
			File file = new File("resources/ILI data/ILIAdverbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);		
			for (String key : list) {
				iliOffset = tomJsonObject.getString(key);
				results_spa = access.wordnetQuery(iliOffset, true);
				for (String s : results_spa) {
					aux_spa.append(key, s);
					data.clear();
				}
			}
			OutputStream outputStream = new FileOutputStream("resources/SPA_Synsets/SPA_adverbs.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(aux_spa.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verbJson() {
		FindStr finder = new FindStrSPA();
		ArrayList<String> data = new ArrayList<>();
		String iliOffset;
		JSONObject aux_spa = new JSONObject();
		JSONObject result = new JSONObject();
		JSONObject data_final = new JSONObject();
		MySQLAccess access = new MySQLAccess();
		ArrayList<String> results_spa = new ArrayList<>();
		try {
			File file = new File("resources/ILI data/ILIVerbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject tomJsonObject = new JSONObject(content);
			Collection<String> keys = tomJsonObject.keySet();
			ArrayList<String> list = new ArrayList<String>(keys);
			for (String key : list) {
				aux_spa= new JSONObject();
				iliOffset = tomJsonObject.getString(key);
				results_spa = access.wordnetQuery(iliOffset, true);
				for (String s : results_spa) {
					data = finder.getVerbData(s);
					data_final = new JSONObject();
					data_final.put("infinitive", data.get(0));
					data_final.put("3PS", data.get(1));
					data_final.put("3PP", data.get(2));
					data_final.put("3SS", data.get(3));
					data_final.put("3SP", data.get(4));

					aux_spa.put(s, data_final);
					data.clear();
				}
				result.put(key, aux_spa);
			}
			OutputStream outputStream = new FileOutputStream("resources/SPA_Synsets/SPA_verbs.json");
			Writer outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			outputStreamWriter.write(result.toString(spacesToIndentEachLevel));
			outputStreamWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
