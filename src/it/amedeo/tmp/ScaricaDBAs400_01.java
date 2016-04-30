// Scarica SARAN E SARSYC
package it.amedeo.tmp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import it.amedeo.mybatis.javaclient.ParoleMapper;
import it.amedeo.mybatis.javaclient.SaranMapper;
import it.amedeo.mybatis.javaclient.SarsycMapper;
import it.amedeo.mybatis.javaclient.ext.AnagraficheMapperExt;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.Parole;
import it.amedeo.mybatis.javamodel.Saran;
import it.amedeo.mybatis.javamodel.Sarsyc;
import it.amedeo.utils.LeftZero;
import it.amedeo.utils.MyBatisConnectionFactory;
import it.amedeo.utils.PadString;


public class ScaricaDBAs400_01 {
	
	private static final String regioni[] = {"00","01","02","03","04","05","06","07","08","09",
						"10","11","12","13","14","15","16","17","18","19",};
	private static File fileOutAna = null;
	private static Writer recOutAna = null;
	private static String outRigaAna = null;
	private static File fileOutSyc = null;
	private static Writer recOutSyc = null;
	private static String outRigaSyc = null;

	
	private static DecimalFormat decimalFormat = new DecimalFormat( "000000000000.000" );
	private static Anagrafiche anagrafiche = null;
	private static Sarsyc sarsyc = null;
	private static Saran saran = null;
	private static AnagraficheMapperExt anagraficheMapperExt = null;
	private static SarsycMapper sarsycMapper = null;
	private static SaranMapper saranMapper = null;
	private static ParoleMapper paroleMapper = null;
	private static String regionePrec, istatPrec, prvPrec, prgLocMin, prgLocMax, prgPrvMin, prgPrvMax, prgRegMin, PrgRegMax;
	private static String prgAna;
	private static String istat;
	private static String prv;

	private static String alberto = null;
	
	public static void main(String[] args) throws IOException {
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
		
		Long startQuery = new Long(0);
		Long endQuery = new Long(0);
		Map<String, String> paramQuery1 = new HashMap<String, String>();
		Map<String, Long> paramQuery2 = new HashMap<String, Long>();
		List<Anagrafiche> listKanagra = new ArrayList<Anagrafiche>();
		anagrafiche = new Anagrafiche();
		sarsyc = new Sarsyc();
		saran = new Saran();

		anagraficheMapperExt = sqlSession.getMapper(AnagraficheMapperExt.class);
		sarsycMapper = sqlSession.getMapper(SarsycMapper.class);
		saranMapper = sqlSession.getMapper(SaranMapper.class);
		paroleMapper = sqlSession.getMapper(ParoleMapper.class);
//		for (int i = 0; i < regioni.length; i++) {
		for (int i = 0; i < 4; i++) {		
//		for (int i = 6; i < 11; i++) {
			fileOutAna = new File("D:/FileInfoCam/SARAN" + regioni[i] + ".txt");
			recOutAna = new BufferedWriter(new FileWriter(fileOutAna,false));
			fileOutSyc = new File("D:/FileInfoCam/SARSYC" + regioni[i] + ".txt");
			recOutSyc = new BufferedWriter(new FileWriter(fileOutSyc,false));
			
			paramQuery1.put("codregioneres", regioni[i]);
			startQuery = System.currentTimeMillis();
			listKanagra = anagraficheMapperExt.selectKanagra(paramQuery1);
			endQuery = System.currentTimeMillis();
			String durationQuery = String.valueOf((endQuery - startQuery) / 1000) + "," + String.valueOf((endQuery - startQuery) % 1000);
			System.out.println("Regione " + regioni[i] + " Durata Query 1: "  + durationQuery + " Kanagra estratti: " + listKanagra.size());
			
			startQuery = System.currentTimeMillis();
			for (int j = 0; j < listKanagra.size(); j++) {
				paramQuery2.put("kanagra", listKanagra.get(j).getKanagra());
				anagrafiche = anagraficheMapperExt.selectAnagra(paramQuery2);
				System.out.println("Regione " + regioni[i] + " Tot.anagrafiche " + listKanagra.size() + " - " + j);
				creaSARAN(j, regioni[i]);
				creaSARSYC(j, regioni[i]);
				creaParole(j, regioni[i]);
			}
			endQuery = System.currentTimeMillis();
			durationQuery = String.valueOf((endQuery - startQuery) / 1000) + "," + String.valueOf((endQuery - startQuery) % 1000);
			System.out.println("Regione " + regioni[i] + " Durata Query 2: "  + durationQuery + " righe estratte: " + listKanagra.size());
			recOutAna.close();
			creaSARSYCLoc();
			creaSARSYCPrv();
			creaSARSYCReg();
			recOutSyc.close();
		}
	}
	
	
	
	private static void creaSARAN(Integer j, String regione) {
		String nome = anagrafiche.getNominativo().replace("*", " ");
		String indir = anagrafiche.getIndirizzoresidenza().replace("*", " ");
		outRigaAna = " " + 
				regione + 
				LeftZero.LeftZero(Integer.toString(j + 1), 7, "0") +
				"I" +
				LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0") +
				PadString.padRight(anagrafiche.getCodicestatoresidenza(), 3) +
				PadString.padRight(anagrafiche.getCodistatprvres(), 3) + 
				PadString.padRight(anagrafiche.getCodistatcomres(), 3) +
				PadString.padRight(anagrafiche.getCodicestatonascita(), 3) +
				PadString.padRight(anagrafiche.getCodistatprvnas(), 3) + 
				PadString.padRight(anagrafiche.getCodistatcomnas(), 3) +
				PadString.padRight(anagrafiche.getDatanascita(), 8) +
				PadString.padRight(anagrafiche.getCodicefiscale(), 16) + 
				LeftZero.LeftZero(Integer.toString(anagrafiche.getNumtoteff()), 4, "0") +
				String.valueOf(decimalFormat.format(anagrafiche.getImptoteff())).replace(",", "") +
				PadString.padRight(anagrafiche.getDataulteff(), 8) + 
				anagrafiche.getDatatimeins().substring(0,8) + 
				LeftZero.LeftZero(Integer.toString(nome.length() + indir.length() + 1), 6, "0") +
				nome.substring(0, nome.length()) + 
				"*" + 
				indir.substring(0, indir.length());
		
		try {
			recOutAna.write(PadString.padRight(outRigaAna, 450) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (j == 0) {
			if (regione == "00") {
				saranMapper.deleteByExample(null);
			}
		}
		
		saran.setFlgcan(" ");
		saran.setK1saran(regione + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0"));
		saran.setKanagra("I" + LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0"));
		saran.setStares(anagrafiche.getCodicestatoresidenza());
		saran.setIstres(anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres());
		saran.setStanas(anagrafiche.getCodicestatonascita());
		saran.setIstnas(anagrafiche.getCodistatprvnas() + anagrafiche.getCodistatcomnas());
		saran.setDatnas(anagrafiche.getDatanascita());
		saran.setCodfis(anagrafiche.getCodicefiscale());
		saran.setMaxpro(LeftZero.LeftZero(Integer.toString(anagrafiche.getNumtoteff()), 4, "0"));
		saran.setTimpro(String.valueOf(decimalFormat.format(anagrafiche.getImptoteff())).replace(",", ""));
		saran.setDtulpro(anagrafiche.getDataulteff());
		saran.setDatins(anagrafiche.getDatatimeins().substring(0,8));
		String nomeIndir = nome.substring(0, nome.length()) + "*" + indir.substring(0, indir.length());
		if (nomeIndir.length() > 200) {
			saran.setLnomin("0200");
			saran.setNominat(nomeIndir.substring(0, 200));
		} else {
			saran.setLnomin(LeftZero.LeftZero(Integer.toString(nome.length() + indir.length() + 1), 4, "0"));
			saran.setNominat(nome.substring(0, nome.length()) + "*" + indir.substring(0, indir.length()));			
		}
		saranMapper.insert(saran);
	}
	
	private static void creaSARSYC(Integer j, String regione) {
		prgAna = regione + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0");
		istat = anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres();
		prv = anagrafiche.getCodistatprvres();
		if (j == 0) {
			regionePrec = regione;
			istatPrec = istat;
			prvPrec = prv;
			prgLocMin = prgAna;
			prgLocMax = prgAna;
			prgPrvMin = prgAna;
			prgPrvMax = prgAna;
			prgRegMin = prgAna;
			PrgRegMax = prgAna;
			if (regione == "00") {
				sarsycMapper.deleteByExample(null);
			}
		}
		if (!(prv.equals(prvPrec))) {
			creaSARSYCLoc();
			creaSARSYCPrv();
		} else {
			if (!(istat.equals(istatPrec))) {
				creaSARSYCLoc();
			}
		}
		prgLocMax = prgAna;
		prgPrvMax = prgAna;
		PrgRegMax = prgAna;
	}
	
	
	private static void creaSARSYCPrv() {
		sarsyc.setRegione(regionePrec);
		sarsyc.setIstat(prvPrec + "999");
		sarsyc.setProgrmin(LeftZero.LeftZero(prgPrvMin, 9, "0"));
		sarsyc.setProgrmax(LeftZero.LeftZero(prgPrvMax, 9, "0"));
		sarsycMapper.insert(sarsyc);
		prgPrvMin = prgAna;
		prgPrvMax = prgAna;
		prvPrec = prv;
		
		outRigaSyc = regionePrec + prvPrec + "999" + LeftZero.LeftZero(prgPrvMin, 9, "0") + LeftZero.LeftZero(prgPrvMax, 9, "0");
//		System.out.println(outRigaSyc);
		prgPrvMin = prgAna;
		prgPrvMax = prgAna;
		prvPrec = prv;
		try {
			recOutSyc.write(PadString.padRight(outRigaSyc, 26) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	private static void creaSARSYCLoc() {
		sarsyc.setRegione(regionePrec);
		sarsyc.setIstat(istatPrec);
		sarsyc.setProgrmin(LeftZero.LeftZero(prgLocMin, 9, "0"));
		sarsyc.setProgrmax(LeftZero.LeftZero(prgLocMax, 9, "0"));
		sarsycMapper.insert(sarsyc);
		prgLocMin = prgAna;
		prgLocMax = prgAna;
		istatPrec = istat;

		outRigaSyc = regionePrec +  istatPrec + LeftZero.LeftZero(prgLocMin, 9, "0") + LeftZero.LeftZero(prgLocMax, 9, "0");
//		System.out.println(outRigaSyc);
		prgLocMin = prgAna;
		prgLocMax = prgAna;
		istatPrec = istat;
		try {
			recOutSyc.write(PadString.padRight(outRigaSyc, 26) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	private static void creaSARSYCReg() {
		sarsyc.setRegione(regionePrec);
		sarsyc.setIstat("999999");
		sarsyc.setProgrmin(LeftZero.LeftZero(prgRegMin, 9, "0"));
		sarsyc.setProgrmax(LeftZero.LeftZero(PrgRegMax, 9, "0"));
		sarsycMapper.insert(sarsyc);
		
		
		outRigaSyc = regionePrec + "999999" + LeftZero.LeftZero(prgRegMin, 9, "0") + LeftZero.LeftZero(PrgRegMax, 9, "0");
//		System.out.println(outRigaSyc);
		try {
			recOutSyc.write(PadString.padRight(outRigaSyc, 26) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	
	private static void creaParole(Integer j, String regione) {
		if (j == 0) {
			if (regione == "00") {
				paroleMapper.deleteByExample(null);
			}
		}
		String[] arrNome = anagrafiche.getNominativo().trim().split(" ");
		// elimino eventuali parole doppie (es. DI MAURO MAURO)
		Map<String, String> mapNome = new HashMap<String, String>();
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), arrNome[i].trim());
		}
//		if (anagrafiche.getKanagra() == 7669430 ) {
//			alberto = null;
//		}
		for (Map.Entry<String, String> entry : mapNome.entrySet()) {
			Parole parole = new Parole();
			if (entry.getKey().trim().length() > 1) {
	            if (entry.getKey().length() < 11) {
		            parole.setTipoparola("A");					
				} else {
		            parole.setTipoparola("B");
				}
		        parole.setParola(entry.getKey().trim().toString());
		        parole.setCodiceregione(anagrafiche.getCodregioneres());
		        parole.setProgressivo(anagrafiche.getCodregioneres() + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0"));
				parole.setKanagra("I" + LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0"));
				parole.setIstatprvres(anagrafiche.getCodistatprvres());
				parole.setIstatres(anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres());
		    	paroleMapper.insert(parole);
			}
		}
		
		
		String[] arrIndir = anagrafiche.getIndirizzoresidenza().trim().split(" ");
		// elimino eventuali parole doppie (es. VIA GIOVANNI DE GIOVANNI)
		Map<String, String> mapIndir = new HashMap<String, String>();
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 20) {
				arrIndir[i] = arrIndir[i].trim().substring(0, 20);
			}
			mapIndir.put(arrIndir[i].trim(), arrIndir[i].trim());
		}
		for (Map.Entry<String, String> entry : mapIndir.entrySet()) {
			Parole parole = new Parole();
			if (entry.getKey().trim().length() > 1) {
	            if (entry.getKey().length() < 11) {
		            parole.setTipoparola("C");					
				} else {
		            parole.setTipoparola("D");
				}
		        parole.setParola(entry.getKey().trim().toString());
		        parole.setCodiceregione(anagrafiche.getCodregioneres());
		        parole.setProgressivo(anagrafiche.getCodregioneres() + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0"));
				parole.setKanagra("I" + LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0"));
				parole.setIstatprvres(anagrafiche.getCodistatprvres());
				parole.setIstatres(anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres());
		    	paroleMapper.insert(parole);
			}
		}
        if (anagrafiche.getCodicefiscale().trim().length() > 0) {
			Parole parole = new Parole();
            parole.setTipoparola("E");
	        parole.setParola(anagrafiche.getCodicefiscale().trim());
	        parole.setCodiceregione(anagrafiche.getCodregioneres());
	        parole.setProgressivo(anagrafiche.getCodregioneres() + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0"));
			parole.setKanagra("I" + LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0"));
			parole.setIstatprvres(anagrafiche.getCodistatprvres());
			parole.setIstatres(anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres());
	    	paroleMapper.insert(parole);
		}
    	if (anagrafiche.getDatanascita().trim().length() > 0) {
			Parole parole = new Parole();
            parole.setTipoparola("F");
	        parole.setParola(anagrafiche.getDatanascita().trim());
	        parole.setCodiceregione(anagrafiche.getCodregioneres());
	        parole.setProgressivo(anagrafiche.getCodregioneres() + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0"));
			parole.setKanagra("I" + LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0"));
			parole.setIstatprvres(anagrafiche.getCodistatprvres());
			parole.setIstatres(anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres());
	    	paroleMapper.insert(parole);
		}
    	if ((anagrafiche.getCodistatprvnas().trim() + anagrafiche.getCodistatcomnas().trim()).length() > 0) {
    		if (!(anagrafiche.getCodistatprvnas().trim() + anagrafiche.getCodistatcomnas().trim()).equals("000000")) {
				Parole parole = new Parole();
	            parole.setTipoparola("G");
		        parole.setParola((anagrafiche.getCodistatprvnas().trim() + anagrafiche.getCodistatcomnas().trim()).trim());
		        parole.setCodiceregione(anagrafiche.getCodregioneres());
		        parole.setProgressivo(anagrafiche.getCodregioneres() + LeftZero.LeftZero(Integer.toString(j + 1), 7, "0"));
				parole.setKanagra("I" + LeftZero.LeftZero(Long.toString(anagrafiche.getKanagra()), 9, "0"));
				parole.setIstatprvres(anagrafiche.getCodistatprvres());
				parole.setIstatres(anagrafiche.getCodistatprvres() + anagrafiche.getCodistatcomres());
		    	paroleMapper.insert(parole);
    		}
		}
	}
}