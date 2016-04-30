package it.amedeo.load.into.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.sqlquery.SqlInfAnagrafiche;
import it.amedeo.utils.CreaLogElab;
import it.amedeo.utils.MyBatisConnectionFactory;

public class Am01_CaricaAnagraficheIntoDB {

	public static void main(String[] args) throws IOException {
		args = new String[1];		
//		args[0] = "D:" + File.separator + "FileInfoCam" + File.separator + "INFOCAMERE1308" + File.separator + "INFANA01";
		args[0] = "D:\\Google Drive\\FileInfoCam\\INFOCAMERE1603\\INFANA01";
		if (args.length<1){
			System.out.println("\nParametri in input mancanti.\nEsecuzione interrotta.");
			System.exit(1);
		}
		Integer lettiTot=0;
		Integer scrittiTot=0;
		
    	new CreaLogElab(" ----- INIZIO ELABORAZIONE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab(" ----- INIZIO ELABORAZIONE CARICAMENTO ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	
		String riga = null;;
		String idFlusso = null;
		Infanagrafiche infanagrafiche = new Infanagrafiche();
    	try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			while ((riga = br.readLine()) != null) {
				lettiTot++;
				if (riga != null) {
		            if (riga.startsWith("000000001TS")) {
		            	new CreaLogElab("File Anagrafiche in caricamento:" + riga, new Object(){}.getClass().getEnclosingClass().getSimpleName());
		            	idFlusso = riga.substring(0, 150).trim();
					}
		            infanagrafiche.setDateflusso(idFlusso.substring(37, 43) + idFlusso.substring(45, 51));
	            	infanagrafiche.setIdflusso(idFlusso);
//	            	infanagrafiche.setRiga(riga.trim());
	            	infanagrafiche.setRiga(riga);
	            	new SqlInfAnagrafiche().insert(infanagrafiche);
	            	scrittiTot++;
	            	if (((scrittiTot / 10000) * 10000) == scrittiTot) {
	                	new CreaLogElab("Righe caricate............: " + scrittiTot, new Object(){}.getClass().getEnclosingClass().getSimpleName());		                    
	            	}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	new CreaLogElab(" ----- FINE ELABORAZIONE CARICAMENTO ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Righe lette.....................: " + lettiTot, new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Righe scritte...................: " + scrittiTot, new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	MyBatisConnectionFactory.getSqlSession().commit();
    	MyBatisConnectionFactory.getSqlSession().close();
	}
		
}
