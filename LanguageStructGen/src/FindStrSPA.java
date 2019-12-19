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

public class FindStrSPA implements FindStr {
	
	private ArrayList<String> fileNames = new ArrayList<>();
	
	public FindStrSPA(){
		File folder = new File("resources/TextData/SPA/");
		File[] listOfFiles = folder.listFiles();
		for (File f : listOfFiles){ 
			if (f.getName().contains("Etiquetado")){
				this.fileNames.add(f.getName());
			}
		}
	}

	private static void findWordData(String fileName) {
		try {
			String procs = "findstr /g:aux_strings.txt "+fileName+" > results.txt";
			ProcessBuilder proBuild = new ProcessBuilder();
			proBuild.directory(new File("resources/TextData/SPA/"));
			proBuild.command("cmd.exe", "/c", procs);
			Process p = proBuild.start();
			p.waitFor();

		} catch (Exception e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	public ArrayList<String> getVerbData(String word){
		ArrayList<String> verbData = new ArrayList<>();
		BufferedReader results = null;
		String line = null, infinitive = word, PresentSingular=word, PresentPlural=word, PastSingular=word, PastPlural= word;
		boolean contPS = true, contPP = true, contSS = true, contSP= true;
		File file = null;
		String queries = word+" VMIP3\n"+word+" VMIS3";
		writeWord(queries);
		/*if(word.equals("")){
			boolean c = true;
		}*/
		for (String name : fileNames){
			findWordData(name);
			try{
				results = new BufferedReader(new FileReader("resources/TextData/SPA/results.txt"));
				file = new File("resources/TextData/SPA/results.txt");
				while((line = results.readLine()) != null){
					String[] splited = line.split("\\s+");
					String word_base = splited[1]; //obtengo la palabra base
					char[] chars = splited[2].toCharArray();  //obtengo los tags
					if(word_base.equals(word)){
						if ((chars[3]=='P')&&(chars[5]=='S')){
							PresentSingular = splited[0];
							contPS=false;
						}
						if ((chars[3]=='P')&&(chars[5]=='P')){
							PresentPlural = splited[0];
							contPP=false;
						}
						if ((chars[3]=='S')&&(chars[5]=='S')){
							PastSingular = splited[0];
							contSS=false;
						}
						if ((chars[3]=='S')&&(chars[5]=='P')){
							PastPlural = splited[0];
							contSP=false;
						}
						
						if((!contPS && !contPP)&&(!contSS && !contSP)){
							break;
						}
					}
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			if((!contPS && !contPP)&&(!contSS && !contSP)){
				break;
			}
		}
		verbData.add(0,infinitive);
		verbData.add(PresentSingular);
		verbData.add(PresentPlural);
		verbData.add(PastSingular);
		verbData.add(PastPlural);
		file.delete();
		return verbData;
	}
	

	public ArrayList<String> getAdjData(String word) {
		ArrayList<String> data = new ArrayList<>();
		BufferedReader results = null;
		String line = null, sing_m = word, plu_m = word, type = "qualificative", plu_f = word, sing_f = word;
		boolean contCS = true, contCP = true, contMS = true, contMP = true, contFS = true, contFP = true;
		File file = null;
		/*
		 * if(word.equals("maldito")){ boolean c = true; }
		 */
		try {
			writeWord(word + " A");
			for (String name : fileNames) {
				findWordData(name);
				results = new BufferedReader(new FileReader("resources/TextData/SPA/results.txt"));
				file = new File("resources/TextData/SPA/results.txt");
				while ((line = results.readLine()) != null) {
					String[] splited = line.split("\\s+");
					String word_base = splited[1];
					char[] chars = splited[2].toCharArray();
					if ((word_base.equals(word)) && ((chars[2] == '0') || (chars[2] == 'V'))) {
						if ((chars[3] == 'C') && (chars[4] == 'S')) {
							sing_m = word;
							sing_f = word;
							contCS = false;
						}

						if ((chars[3] == 'C') && (chars[4] == 'P')) {
							plu_m = splited[0];
							plu_f = splited[0];
							contCP = false;
						}

						if ((chars[3] == 'M') && (chars[4] == 'S')) {
							sing_m = splited[0];
							contMS = false;
						}

						if ((chars[3] == 'M') && (chars[4] == 'P')) {
							plu_m = splited[0];
							contMP = false;
						}

						if ((chars[3] == 'F') && (chars[4] == 'S')) {
							sing_f = splited[0];
							contFS = false;
						}

						if ((chars[3] == 'F') && (chars[4] == 'P')) {
							plu_f = splited[0];
							contFP = false;
						}

						if ((!contCS && !contCP) || ((!contMS && !contMP) && (!contFS && !contFP))) {
							switch (chars[1]) {
							case 'O':
								type = "ordinal";
								break;
							case 'Q':
								type = "qualificative";
								break;
							case 'P':
								type = "possessive";
								break;
							}
							break;
						}
					}
				}
				if ((!contCS && !contCP) || ((!contMS && !contMP) && (!contFS && !contFP))) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(word);
			e.printStackTrace();
		}
		data.add(0, type);
		data.add(sing_m);
		data.add(sing_f);
		data.add(plu_m);
		data.add(plu_f);
		file.delete();
		return data;
	}

	@SuppressWarnings("resource")
	public ArrayList<String> getNounData(String word) {
		ArrayList<String> gen_plu = new ArrayList<>();
		BufferedReader results = null;
		String line = null, genere = "masculine", plu = word;
		boolean cont = true, cont2 = true;
		File file = null;
		try {
			writeWord(word+" N");
			for (String name : fileNames){
				findWordData(name);
				results = new BufferedReader(new FileReader("resources/TextData/SPA/results.txt"));
				file = new File("resources/TextData/SPA/results.txt");
				while ((line = results.readLine()) != null) {
					String[] splited = line.split("\\s+");
					String word_base = splited[1];
					char[] chars = splited[2].toCharArray();
					if ((word_base.equals(word))&&(chars[1]=='C')) {
						if (cont) {
							switch (chars[2]) {
							case 'F':
								genere = "femenine";
								break;
							case 'M':
								genere = "masculine";
								break;
							default:
								genere = "common";
							}
							cont = false;
						}
						if (chars[3] == 'P') {  //TODO esto de aqui no tiene ya razon de ser
							plu = splited[0];	
							cont2 = false;
						}
						if (!cont && !cont2) {
							gen_plu.add(0, genere);
							gen_plu.add(plu);
							file.delete();
							return gen_plu;
						}
					}
					

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		gen_plu.add(0, genere);
		gen_plu.add(plu);
		file.delete();
		return gen_plu;
	}
	
	@SuppressWarnings("resource")
	public String getNumberAdjective(String word, String genere_base) {
		BufferedReader results = null;
		String line = null, plu = word;
		File file = null;
		char gen_cond;
		switch (genere_base) {
		case "masculine":
			gen_cond = 'M';
			break;
		case "femenine":
			gen_cond = 'F';
			break;
		default:
			gen_cond = 'C';
		}
		try {
			writeWord(word + " A");
			for (String name : fileNames) {
				findWordData(name);
				results = new BufferedReader(new FileReader("resources/TextData/SPA/results.txt"));
				file = new File("resources/TextData/SPA/results.txt");
				while ((line = results.readLine()) != null) {
					String[] splited = line.split("\\s+");
					String word_base = splited[1];
					char[] chars = splited[2].toCharArray();
					if ((word_base.equals(word)) && (chars[0] == 'A')) {
						if ((chars[4] == 'P') && (gen_cond == chars[3])) {
							plu = splited[0];
							file.delete();
							return plu;
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		file.delete();
		return plu;
	}



	
	public ArrayList<String> getDataCompoundNoun(String word){
		String[] splited = word.split("_");
		ArrayList<String> list1, gen_plu = new ArrayList<>();
		String plural, adj_plu;
		if (splited.length==2){
			writeWord(splited[0]+" N");
			list1 = getNounData(splited[0]);
			writeWord(splited[1]+" A");
			adj_plu = getNumberAdjective(splited[1], list1.get(0));
			gen_plu.add(0, list1.get(0));
			plural = list1.get(1)+" "+adj_plu;
			gen_plu.add(1,plural);
			return gen_plu;
			
		} else {
			writeWord(splited[0]);
			list1 = getNounData(splited[0]);
			gen_plu.add(0, list1.get(0));
			plural = list1.get(1)+" "+splited[1]+" "+splited[2];
			gen_plu.add(1, plural);
			return gen_plu;
		}
	}
	
	
	private static void writeWord(String s){
		try {
			OutputStream  writer_stream = new FileOutputStream("resources/TextData/SPA/aux_strings.txt", false);
			Writer writer = null;
			writer =  new OutputStreamWriter(writer_stream,StandardCharsets.UTF_8);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}


	@Override
	public ArrayList<String> getDataCompoundVerb(String word) {
		//Not used
		return null;
	}
}
