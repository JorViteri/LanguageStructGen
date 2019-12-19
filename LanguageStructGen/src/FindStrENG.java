import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FindStrENG implements FindStr{
	
	private ArrayList<String> fileNames = new ArrayList<>();
	
	public FindStrENG(){
		File folder = new File("resources/TextData/ENG/");
		File[] listOfFiles = folder.listFiles();
		for (File f : listOfFiles){ 
			if (f.getName().contains("Etiquetado")){
				this.fileNames.add(f.getName());
			}
		}
	}

	private static void findWordData(String filename) {
		try {
			String procs = "findstr /g:aux_strings.txt "+filename+" > results.txt";
			ProcessBuilder proBuild = new ProcessBuilder();
			proBuild.directory(new File("resources/TextData/ENG/"));
			proBuild.command("cmd.exe", "/c", procs);
			Process p = proBuild.start();
			p.waitFor(); 

		} catch (Exception e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	@SuppressWarnings("resource")
	@Override
	public ArrayList<String> getVerbData(String word) {
		boolean cont1 = false;
		boolean cont2 = false;
		ArrayList<String> sing_plu = new ArrayList<>();
		BufferedReader results = null;
		String line = null, past=word, third_person= word;
		File file = null;
		String queries = word +" VBD\n"+word+" VBZ";
		sing_plu.add(0,word);
		try {
			writeWord(queries);
			for (String name : fileNames){
				findWordData(name);
				results = new BufferedReader(new FileReader("resources/TextData/ENG/results.txt"));
				file = new File("resources/TextData/ENG/results.txt");
				while ((line = results.readLine()) != null) {
					String[] splited = line.split("\\s+");	
					if ((!cont1)&&(splited[2].equals("VBD"))&&(splited[1].equals(word))){
						past = splited[0];
						cont1 = true;
					}
					if((!cont2)&&(splited[2].equals("VBZ"))&&(splited[1].equals(word))){
						third_person = splited[0];
						cont2 = true;
					}
					if(cont1&&cont2){
						sing_plu.add(1, third_person);
						sing_plu.add(2, past);
						file.delete();
						return sing_plu;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		sing_plu.add(1, past);
		sing_plu.add(1,third_person);
		file.delete();
		return sing_plu;
	}

	@Override
	public ArrayList<String> getAdjData(String word) {
	 return null; //It's not used
	}

	@SuppressWarnings("resource")
	@Override
	public ArrayList<String> getNounData(String word) {
		File folder = new File("resources/TextData/ENG/");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> fileNames = new ArrayList<>();
		for (File f : listOfFiles){
			if (f.getName().contains("Etiquetado")){
				fileNames.add(f.getName());
			}
		}
		ArrayList<String> sing_plu = new ArrayList<>();
		BufferedReader results = null;
		String line = null, sing=word, plu = word;
		File file = null;
		sing_plu.add(0,sing);
		try {
			writeWord(word+" NNS");
			for (String name : fileNames){
				findWordData(name);
				results = new BufferedReader(new FileReader("resources/TextData/ENG/results.txt"));
				file = new File("resources/TextData/ENG/results.txt");
				while ((line = results.readLine()) != null) {
					String[] splited = line.split("\\s+");	
					if ((splited[2].equals("NNS"))&&(splited[1].equals(word))){
						sing_plu.add(1,splited[0]);
						file.delete();
						return sing_plu;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		sing_plu.add(1,plu);
		file.delete();
		return sing_plu;
	}

	@Override
	public String getNumberAdjective(String word, String genere_base) {
		// Not Used
		return null;
	}

	@Override
	public ArrayList<String> getDataCompoundNoun(String word) {
		String[] splited = word.split("_");
		ArrayList<String> result = new ArrayList<>();
		ArrayList<String> aux = new ArrayList<>();
		if (splited.length==2){
			aux=getNounData(splited[1]);
			result.add(0,splited[0]+" "+aux.get(0));
			result.add(1,splited[0]+" "+aux.get(1));
			return result;
		} else{
			return null;
		}
	}
	
	@Override
	public ArrayList<String> getDataCompoundVerb(String word){
		String[] splited = word.split("_");
		ArrayList<String> result = new ArrayList<>();
		ArrayList<String> aux = new ArrayList<>();
		if (splited.length==2){
			aux=getVerbData(splited[0]);
			result.add(0, aux.get(0)+" "+splited[1]);
			result.add(1, aux.get(1)+" "+splited[1]);
			result.add(2,aux.get(2)+" "+splited[1]);
			return result;
		} else{
			return null;
		}
	}

	private static void writeWord(String s){
		try {
			OutputStream  writer_stream = new FileOutputStream("resources/TextData/ENG/aux_strings.txt", false);
			Writer writer = null;
			writer =  new OutputStreamWriter(writer_stream,StandardCharsets.UTF_8);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
}
