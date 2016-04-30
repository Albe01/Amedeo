// Legge Dump ANAGRAFICHE e Scarica SU DB: SARAN, SARSYC, PAROLE, prima parte di SARSYA
//        			       in formato TXT: SARANTXT, SARSYCTXT, INFPARTXT, AMDANAGTXT
package it.amedeo;

import it.amedeo.mybatis.javaclient.ParoleMapper;
import it.amedeo.mybatis.javaclient.SaranMapper;
import it.amedeo.mybatis.javaclient.SarsyaMapper;
import it.amedeo.mybatis.javaclient.SarsycMapper;
import it.amedeo.mybatis.javamodel.Parole;
import it.amedeo.mybatis.javamodel.Saran;
import it.amedeo.mybatis.javamodel.Sarsya;
import it.amedeo.mybatis.javamodel.Sarsyc;
import it.amedeo.utils.LeftZero;
import it.amedeo.utils.MyBatisConnectionFactory;
import it.amedeo.utils.PadString;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class Sc01_SARAN_SARSYC {
	private static File fileOutSaran = null;
	private static Writer recOutSaran = null;
	private static String outRigaSaran = null;
	private static File fileOutSarsyc = null;
	private static Writer recOutSarsyc = null;
	private static String outRigaSarsyc = null;
	private static File fileOutInfpartxt = null;
	private static Writer recOutInfpartxt = null;
	private static String outRigaInfpartxt = null;
	private static File fileOutAmdAnagtxt = null;
	private static Writer recOutAmdAnagtxt = null;
	private static String outRigaAmdAnagtxt = null;

	private static DecimalFormat decimalFormat = new DecimalFormat("000000000000.000");
	private static Sarsyc sarsyc = null;
	private static Saran saran = null;
	private static Sarsya sarsya = null;
	private static Parole parole = null;
	private static SarsycMapper sarsycMapper = null;
	private static SaranMapper saranMapper = null;
	private static SarsyaMapper sarsyaMapper = null;
	private static ParoleMapper paroleMapper = null;
	private static String regionePrec, istatPrec, prvPrec, prgLocMin,
			prgLocMax, prgPrvMin, prgPrvMax, prgRegMin, PrgRegMax;
	private static String prgAna;
	private static String istat;
	private static String prv;
	private static String riga = null;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static DecimalFormat intFormat = new DecimalFormat("#,###,###,###");
	private static Integer cAnagLette = 0;
	private static String regPrec = null;
	private static Integer cAnagReg = 0;
	private static Integer cAnagScritte = 0;
	private static String[] arrAnag = null;
	private static Integer[] arrSarsya = null;
	private static String alberto = null;

	public static void main(String[] args) throws IOException {
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();

		saran = new Saran();
		saranMapper = sqlSession.getMapper(SaranMapper.class);
		sarsyc = new Sarsyc();
		sarsycMapper = sqlSession.getMapper(SarsycMapper.class);
		sarsya = new Sarsya();
		sarsyaMapper = sqlSession.getMapper(SarsyaMapper.class);
		parole = new Parole();
		paroleMapper = sqlSession.getMapper(ParoleMapper.class);
		arrSarsya = new Integer[150];
		for (int i = 0; i < 150; i++) {
			arrSarsya[i] = 0;
		}
		fileOutSaran = new File("D:/FileInfoCam/perAS400/SARANTXT.TXT");
		fileOutSarsyc = new File("D:/FileInfoCam/perAS400/SARSYCTXT.TXT");
		fileOutInfpartxt = new File("D:/FileInfoCam/perWEB/INFPARTXT.TXT");
		fileOutAmdAnagtxt = new File("D:/FileInfoCam/perWEB/AMDANAGTXT.TXT");
		try {
			recOutSaran = new BufferedWriter(new FileWriter(fileOutSaran, false));
			recOutSarsyc = new BufferedWriter(new FileWriter(fileOutSarsyc,false));
			recOutInfpartxt = new BufferedWriter(new FileWriter(fileOutInfpartxt, false));
			recOutAmdAnagtxt = new BufferedWriter(new FileWriter(fileOutAmdAnagtxt, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(dateFormat.format(new Date()) + " -----INIZIO ELABORAZIONE----- ");
		File fileInput = new File("D:/FileInfoCam/DBanagrafiche.txt");
		if (fileInput.isFile()) {
			try {
				BufferedReader input = new BufferedReader(new FileReader(fileInput));
				while ((riga = input.readLine()) != null) {
					arrAnag = riga.toString().trim().split("\\|");
					cAnagLette++;
					if (cAnagLette == 1) {
						saranMapper.deleteByExample(null);
						sarsycMapper.deleteByExample(null);
						paroleMapper.deleteByExample(null);
						sarsyaMapper.deleteByExample(null);
					}
					if (((cAnagLette / 100000) * 100000) == cAnagLette) {
						System.out.println(dateFormat.format(new Date()) + " anagrafiche lette: " + intFormat.format(cAnagLette));
					}
					arrSarsya[Integer.parseInt(arrAnag[5])] = arrSarsya[Integer.parseInt(arrAnag[5])] + 1;
					creaSARAN();
					creaSARSYC();
					creaParoleWeb();
				}
				System.out.println(dateFormat.format(new Date()) + " anagrafiche lette: " + intFormat.format(cAnagLette));
				recOutSaran.close();
				creaSARSYCLoc();
				creaSARSYCPrv();
				creaSARSYCReg();
				creaSARSYA();
				recOutSarsyc.close();
				recOutInfpartxt.close();
				recOutAmdAnagtxt.close();
				System.out.println(dateFormat.format(new Date()) + " -----FINE ELABORAZIONE----- ");
			} catch (IOException ioException) {
			}
		}
	}

	private static void creaSARSYA() {
		for (int i = 0; i < 150; i++) {
			if (arrSarsya[i] > 0) {
				sarsya.setCodistatprv(LeftZero.LeftZero(Integer.toString(i), 3,	"0"));
				sarsya.setTotana(arrSarsya[i]);
				sarsya.setToteff(0);
				sarsyaMapper.insert(sarsya);
			}
		}
	}

	private static void creaSARAN() {
		if (arrAnag[4].equals(regPrec)) {
			cAnagReg++;
		} else {
			if (cAnagLette > 1) {
				creaSARSYCLoc();
				creaSARSYCPrv();
				creaSARSYCReg();
			}
			regPrec = arrAnag[4];
			cAnagReg = 1;
		}
		prgAna = LeftZero.LeftZero(Integer.toString(cAnagReg), 7, "0");
		Double importo = Double.parseDouble(arrAnag[19]);
		String nome = arrAnag[2].replace("*", " ");
		String indir = arrAnag[10].replace("*", " ");
		outRigaSaran = " "
				+ arrAnag[4] //codiceregione
				+ prgAna 
				+ "I"
				+ LeftZero.LeftZero(arrAnag[1], 9, "0") //kanagra
				+ PadString.padRight(arrAnag[7], 3)     //statores
				+ PadString.padRight(arrAnag[5], 3)     //istatprvres
				+ PadString.padRight(arrAnag[6], 3)     //istatcomres
				+ PadString.padRight(arrAnag[12], 3)    //statonasc
				+ PadString.padRight(arrAnag[13], 3)    //istatprvnas
				+ PadString.padRight(arrAnag[14], 3)    //istatcomnas
				+ PadString.padRight(arrAnag[11], 8)    //datanasc
				+ PadString.padRight(arrAnag[3], 16)    //codfisc
				+ LeftZero.LeftZero(arrAnag[18], 4, "0") //numtoteff
				+ String.valueOf(decimalFormat.format(importo)).replace(",", "") //imptoteffetti
				+ PadString.padRight(arrAnag[21], 8) //dataulteff
				+ arrAnag[22].substring(2, 6) //datains
				+ LeftZero.LeftZero(Integer.toString(nome.length() + indir.length() + 1), 6, "0") + nome.substring(0, nome.length()) + "*"
				+ indir.substring(0, indir.length());

		try {
			recOutSaran.write(PadString.padRight(outRigaSaran, 450) + "\r\n");
			cAnagScritte++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		saran.setFlgcan(" ");
		saran.setK1saran(arrAnag[4] + prgAna);
		saran.setKanagra("I" + LeftZero.LeftZero(arrAnag[1], 9, "0"));
		saran.setStares(arrAnag[7]);
		saran.setIstres(arrAnag[5] + arrAnag[6]);
		saran.setStanas(arrAnag[12]);
		saran.setIstnas(arrAnag[13] + arrAnag[14]);
		saran.setDatnas(arrAnag[11]);
		saran.setCodfis(arrAnag[3]);
		saran.setMaxpro(LeftZero.LeftZero(arrAnag[18], 4, "0"));
		saran.setTimpro(String.valueOf(decimalFormat.format(importo)).replace(",", ""));
		saran.setDtulpro(arrAnag[21]);
		saran.setDatins(arrAnag[22].substring(2, 6));
		String nomeIndir = nome.substring(0, nome.length()) + "*" + indir.substring(0, indir.length());
		if (nomeIndir.length() > 200) {
			saran.setLnomin("0200");
			saran.setNominat(nomeIndir.substring(0, 200));
		} else {
			saran.setLnomin(LeftZero.LeftZero(Integer.toString(nome.length() + indir.length() + 1), 4, "0"));
			saran.setNominat(nome.substring(0, nome.length()) + "*" + indir.substring(0, indir.length()));
		}
//		saranMapper.insert(saran);

		outRigaAmdAnagtxt = " " 
				+ ";"
				+ arrAnag[4] + prgAna
				+ ";"
				+ "I"
				+ ";"
				+ LeftZero.LeftZero(arrAnag[1], 9, "0")
				+ ";"
				+ arrAnag[7]
				+ ";"
				+ arrAnag[5]
				+ arrAnag[6]
				+ ";"
				+ arrAnag[12]
				+ ";"
				+ arrAnag[13]
				+ arrAnag[14]
				+ ";"
				+ arrAnag[11]
				+ ";"
				+ arrAnag[3]
				+ ";"
				+ LeftZero.LeftZero(arrAnag[18], 4, "0")
				+ ";"
				+ String.valueOf(decimalFormat.format(importo)).replace(",", "") 
				+ ";" 
				+ arrAnag[21] 
				+ ";"
				+ arrAnag[22].substring(2, 6) 
				+ ";";
		nomeIndir.replace(";", " ");
		if (nomeIndir.length() > 324) {
			outRigaAmdAnagtxt = outRigaAmdAnagtxt + nomeIndir.substring(0, 324);
		} else {
			outRigaAmdAnagtxt = outRigaAmdAnagtxt + (nome.substring(0, nome.length()) + "*" + indir.substring(0, indir.length()));
		}
		try {
			recOutAmdAnagtxt.write(PadString.padRight(outRigaAmdAnagtxt, 450) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void creaSARSYC() {
		// prgAna = regione + LeftZero.LeftZero(Integer.toString(j + 1), 7,
		// "0");
		istat = arrAnag[5] + arrAnag[6];
		prv = arrAnag[5];
		if (!arrAnag[4].equals(regionePrec)) {
			regionePrec = arrAnag[4];
			istatPrec = istat;
			prvPrec = prv;
			prgLocMin = arrAnag[4] + prgAna;
			prgLocMax = arrAnag[4] + prgAna;
			prgPrvMin = arrAnag[4] + prgAna;
			prgPrvMax = arrAnag[4] + prgAna;
			prgRegMin = arrAnag[4] + prgAna;
			PrgRegMax = arrAnag[4] + prgAna;
		}
		if (!(prv.equals(prvPrec))) {
			creaSARSYCLoc();
			creaSARSYCPrv();
		} else {
			if (!(istat.equals(istatPrec))) {
				creaSARSYCLoc();
			}
		}
		prgLocMax = arrAnag[4] + prgAna;
		prgPrvMax = arrAnag[4] + prgAna;
		PrgRegMax = arrAnag[4] + prgAna;
	}

	private static void creaSARSYCPrv() {
		sarsyc.setRegione(regionePrec);
		sarsyc.setIstat(prvPrec + "999");
		sarsyc.setProgrmin(LeftZero.LeftZero(prgPrvMin, 9, "0"));
		sarsyc.setProgrmax(LeftZero.LeftZero(prgPrvMax, 9, "0"));
		sarsycMapper.insert(sarsyc);

		outRigaSarsyc = regionePrec + prvPrec + "999"
				+ LeftZero.LeftZero(prgPrvMin, 9, "0")
				+ LeftZero.LeftZero(prgPrvMax, 9, "0");
		prgPrvMin = arrAnag[4] + prgAna;
		prgPrvMax = arrAnag[4] + prgAna;
		prvPrec = prv;
		try {
			recOutSarsyc.write(PadString.padRight(outRigaSarsyc, 26) + "\r\n");
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

		outRigaSarsyc = regionePrec + istatPrec
				+ LeftZero.LeftZero(prgLocMin, 9, "0")
				+ LeftZero.LeftZero(prgLocMax, 9, "0");
		prgLocMin = arrAnag[4] + prgAna;
		prgLocMax = arrAnag[4] + prgAna;
		istatPrec = istat;
		try {
			recOutSarsyc.write(PadString.padRight(outRigaSarsyc, 26) + "\r\n");
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

		outRigaSarsyc = regionePrec + "999999"
				+ LeftZero.LeftZero(prgRegMin, 9, "0")
				+ LeftZero.LeftZero(PrgRegMax, 9, "0");
		try {
			recOutSarsyc.write(PadString.padRight(outRigaSarsyc, 26) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void creaParoleWeb() {
		arrAnag[2] = arrAnag[2].replace("\\", " ");
		arrAnag[2] = arrAnag[2].replace("\\|", " ");
		String[] arrNome = arrAnag[2].trim().split(" ");
		// elimino eventuali parole doppie (es. DI MAURO MAURO)
		Map<String, String> mapNome = new HashMap<String, String>();
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), arrNome[i].trim());
		}
		for (Map.Entry<String, String> entry : mapNome.entrySet()) {
			if (entry.getKey().trim().length() > 1) {
				if (entry.getKey().length() < 11) {
					parole.setTipoparola("A");
				} else {
					parole.setTipoparola("B");
				}
				parole.setParola(entry.getKey().trim().toString());
				parole.setCodiceregione(arrAnag[4]);
				parole.setProgressivo(arrAnag[4] + prgAna);
				parole.setKanagra("I" + LeftZero.LeftZero(arrAnag[1], 9, "0"));
				parole.setIstatprvres(arrAnag[5]);
				parole.setIstatres(arrAnag[5] + arrAnag[6]);
				paroleMapper.insert(parole);

				outRigaInfpartxt = entry.getKey().trim().toString() 
						+ ";" 
						+ "I"
						+ LeftZero.LeftZero(arrAnag[1], 9, "0") 
						+ ";"
						+ arrAnag[5] 
						+ ";" 
						+ arrAnag[5] + arrAnag[6] 
						+ ";"
						+ parole.getTipoparola() 
						+ ";" 
						+ arrAnag[4] 
						+ ";"
						+ arrAnag[4] + prgAna;
				try {
					recOutInfpartxt.write(PadString.padRight(outRigaInfpartxt, 57) + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		arrAnag[10] = arrAnag[10].replace("\\", " ");
		arrAnag[10] = arrAnag[10].replace("\\|", " ");
		String[] arrIndir = arrAnag[10].trim().split(" ");
		// elimino eventuali parole doppie (es. VIA GIOVANNI DE GIOVANNI)
		Map<String, String> mapIndir = new HashMap<String, String>();
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 20) {
				arrIndir[i] = arrIndir[i].trim().substring(0, 20);
			}
			mapIndir.put(arrIndir[i].trim(), arrIndir[i].trim());
		}
		for (Map.Entry<String, String> entry : mapIndir.entrySet()) {
			if (entry.getKey().trim().length() > 1) {
				if (entry.getKey().length() < 11) {
					parole.setTipoparola("C");
				} else {
					parole.setTipoparola("D");
				}
				parole.setParola(entry.getKey().trim().toString());
				parole.setCodiceregione(arrAnag[4]);
				parole.setProgressivo(arrAnag[4] + prgAna);
				parole.setKanagra("I" + LeftZero.LeftZero(arrAnag[1], 9, "0"));
				parole.setIstatprvres(arrAnag[5]);
				parole.setIstatres(arrAnag[5] + arrAnag[6]);
				paroleMapper.insert(parole);

				outRigaInfpartxt = entry.getKey().trim().toString() 
						+ ";" 
						+ "I"
						+ LeftZero.LeftZero(arrAnag[1], 9, "0") 
						+ ";"
						+ arrAnag[5] 
						+ ";" 
						+ arrAnag[5] + arrAnag[6] 
						+ ";"
						+ parole.getTipoparola() 
						+ ";" 
						+ arrAnag[4] 
						+ ";"
						+ arrAnag[4] + prgAna;
				try {
					recOutInfpartxt.write(PadString.padRight(outRigaInfpartxt, 57) + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (arrAnag[3].trim().length() > 0) {
			parole.setTipoparola("E");
			parole.setParola(arrAnag[3].trim());
			parole.setCodiceregione(arrAnag[4]);
			parole.setProgressivo(arrAnag[4] + prgAna);
			parole.setKanagra("I" + LeftZero.LeftZero(arrAnag[1], 9, "0"));
			parole.setIstatprvres(arrAnag[5]);
			parole.setIstatres(arrAnag[5] + arrAnag[6]);
			paroleMapper.insert(parole);

			outRigaInfpartxt = arrAnag[3].trim() 
					+ ";" 
					+ "I"
					+ LeftZero.LeftZero(arrAnag[1], 9, "0") 
					+ ";" 
					+ arrAnag[5]
					+ ";" 
					+ arrAnag[5] 
					+ arrAnag[6]
					+ ";"
					+ parole.getTipoparola()
					+ ";"
					+ arrAnag[4]
					+ ";"
					+ arrAnag[4] + prgAna;
			try {
				recOutInfpartxt.write(PadString.padRight(outRigaInfpartxt, 57) + "\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (arrAnag[11].trim().length() > 0) {
			parole.setTipoparola("F");
			parole.setParola(arrAnag[11].trim());
			parole.setCodiceregione(arrAnag[4]);
			parole.setProgressivo(arrAnag[4] + prgAna);
			parole.setKanagra("I" + LeftZero.LeftZero(arrAnag[1], 9, "0"));
			parole.setIstatprvres(arrAnag[5]);
			parole.setIstatres(arrAnag[5] + arrAnag[6]);
			paroleMapper.insert(parole);

			outRigaInfpartxt = arrAnag[11].trim() 
					+ ";" 
					+ "I"
					+ LeftZero.LeftZero(arrAnag[1], 9, "0")
					+ ";"
					+ arrAnag[5]
					+ ";" 
					+ arrAnag[5]
					+ arrAnag[6]
					+ ";"
					+ parole.getTipoparola()
					+ ";"
					+ arrAnag[4]
					+ ";"
					+ arrAnag[4] + prgAna;
			try {
				recOutInfpartxt.write(PadString.padRight(outRigaInfpartxt, 57) + "\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if ((arrAnag[13].trim() + arrAnag[14].trim()).length() > 0) {
			if (!(arrAnag[13].trim() + arrAnag[14].trim()).equals("000000")) {
				parole.setTipoparola("G");
				parole.setParola((arrAnag[13].trim() + arrAnag[14].trim()).trim());
				parole.setCodiceregione(arrAnag[4]);
				parole.setProgressivo(arrAnag[4] + prgAna);
				parole.setKanagra("I" + LeftZero.LeftZero(arrAnag[1], 9, "0"));
				parole.setIstatprvres(arrAnag[5]);
				parole.setIstatres(arrAnag[5] + arrAnag[6]);
				paroleMapper.insert(parole);

				outRigaInfpartxt = (arrAnag[13].trim() + arrAnag[14].trim()).trim()
						+ ";"
						+ "I"
						+ LeftZero.LeftZero(arrAnag[1], 9, "0")
						+ ";"
						+ arrAnag[5]
						+ ";"
						+ arrAnag[5]
						+ arrAnag[6]
						+ ";"
						+ parole.getTipoparola()
						+ ";"
						+ arrAnag[4]
						+ ";"
						+ arrAnag[4] + prgAna;
				try {
					recOutInfpartxt.write(PadString.padRight(outRigaInfpartxt, 57) + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}