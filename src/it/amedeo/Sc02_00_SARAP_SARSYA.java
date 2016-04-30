// Legge Dump EFFETTI e Scarica su DB: SARAP, completa SARSYA
//        		       in formato TXT: SARAPTXT, SARSYATXT, AMDPSTITXT
package it.amedeo;

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
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import it.amedeo.mybatis.javaclient.InfcomuniMapper;
import it.amedeo.mybatis.javaclient.SarapMapper;
import it.amedeo.mybatis.javaclient.SarsyaMapper;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.mybatis.javamodel.Sarap;
import it.amedeo.mybatis.javamodel.Sarsya;
import it.amedeo.mybatis.javamodel.SarsyaExample;
import it.amedeo.utils.CreaLogElab;
import it.amedeo.utils.FindSQLExceptionWithVendorErrorCode;
import it.amedeo.utils.LeftZero;
import it.amedeo.utils.MyBatisConnectionFactory;
import it.amedeo.utils.PadString;


public class Sc02_00_SARAP_SARSYA {
	private static File fileOutSarap = null;
	private static Writer recOutSarap = null;
	private static String outRigaSarap = "";
	
	private static File fileOutSarsya = null;
	private static Writer recOutSarsya = null;
	private static String outRigaSarsya = "";
	
	private static File fileOutAmdPstitxt = null;
	private static Writer recOutAmdPstitxt = null;
	private static String outRigaAmdPstitxt = "";
	
	private static Integer cScrittiSarap = 0;
	private static Integer cEffettiLetti = 0;
	private static Integer kanagraPrec = 0;
	private static Integer idEff = 0;
	private static Integer indTab = 0;
	private static Integer progrRec = 0;
	private static Integer totEff = 0;
	private static DecimalFormat decimalFormat = new DecimalFormat( "000000000000.000" );
	private static DecimalFormat intFormat = new DecimalFormat( "#,###,###,###" );
	private static Sarap sarap = null;
	private static SarapMapper sarapMapper = null;
	private static Sarsya sarsya = null;
	private static SarsyaMapper sarsyaMapper = null;
	private static Map<String, String> mapInfCodComCodIstat = null;
	private static InfcomuniMapper infcomuniMapper = null;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static String riga = null;	
	private static Integer[][] arrSarsya = null;
	private static String[] arrEffetti = null;
	
	private static String alberto = null;

	public static void main(String[] args) throws IOException {
		try {
			MyBatisConnectionFactory.openSqlSessionFactory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();

		sarap = new Sarap();
		sarapMapper = sqlSession.getMapper(SarapMapper.class);
		sarsya = new Sarsya();
		sarsyaMapper = sqlSession.getMapper(SarsyaMapper.class);
		infcomuniMapper = sqlSession.getMapper(InfcomuniMapper.class);

		arrSarsya = new Integer[150][6];
		for (int i = 0; i < arrSarsya.length; i++) {
			arrSarsya[i][0] = 99999999; //datiscmin
			arrSarsya[i][1] = 0;        //datiscmax
			arrSarsya[i][2] = 99999999; //datlevmin
			arrSarsya[i][3] = 0;        //datlevmax
			arrSarsya[i][4] = 0;        //totana
			arrSarsya[i][4] = 0;        //totana
			arrSarsya[i][5] = 0;        //toteff
		}

		List<Sarsya> lstSarsya = sarsyaMapper.selectByExample(null);
		for (int i = 0; i < lstSarsya.size(); i++) {
			arrSarsya[Integer.parseInt(lstSarsya.get(i).getCodistatprv())][4] = lstSarsya.get(i).getTotana(); 
		}
		
		fileOutSarap = new File("D:/FileInfoCam/perAS400/SARAPTXT.TXT");
		fileOutSarsya = new File("D:/FileInfoCam/perAS400/SARSYATXT.TXT");
		fileOutAmdPstitxt = new File("D:/FileInfoCam/perWEB/AMDPSTITXT.TXT");
		try {
			recOutSarap = new BufferedWriter(new FileWriter(fileOutSarap,false));
			recOutSarsya = new BufferedWriter(new FileWriter(fileOutSarsya,false));
			recOutAmdPstitxt = new BufferedWriter(new FileWriter(fileOutAmdPstitxt, false));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		InfcomuniExample infcomuniExample = new InfcomuniExample();
		infcomuniExample.createCriteria().andCodregioneNotEqualTo("00");
		List<Infcomuni> lstInfComuni =  infcomuniMapper.selectByExample(infcomuniExample);
		mapInfCodComCodIstat = new HashMap<String, String>();
		for (int i = 0; i < lstInfComuni.size(); i++) {
			mapInfCodComCodIstat.put(lstInfComuni.get(i).getInfcodicecomune(), lstInfComuni.get(i).getCodistat());
		}
		System.out.println(dateFormat.format(new Date()) + " -----INIZIO ELABORAZIONE----- ");		
		File fileInput = new File("D:/FileInfoCam/DBeffetti.txt");
		if (fileInput.isFile()) {
			try {
				BufferedReader input = new BufferedReader(new FileReader(fileInput));
				while ((riga = input.readLine()) != null)  {
					arrEffetti = riga.toString().trim().split("\\|");
					cEffettiLetti++;
	            	if (((cEffettiLetti / 100000) * 100000) == cEffettiLetti) {
	        			System.out.println(dateFormat.format(new Date()) + " effetti letti: " + intFormat.format(cEffettiLetti));		                    
	            	}
					if (cEffettiLetti == 1) {
						kanagraPrec = Integer.parseInt(arrEffetti[1]);
						idEff = 0;
						progrRec = 1;
						totEff = 0;
						indTab = 0;
					}
					scriviPstiWeb();
					if (Integer.parseInt(arrEffetti[1]) == kanagraPrec) {
						intabellaEffetti(arrEffetti);
					} else {
						scriviSarap();
						kanagraPrec = Integer.parseInt(arrEffetti[1]);
						idEff = 0;
						progrRec = 1;
						totEff = 0;
						indTab = 0;
						intabellaEffetti(arrEffetti);
					}
					creaArrSarsya(arrEffetti);
				}
				scriviSarap();
				scriviSarsya();
    			System.out.println(dateFormat.format(new Date()) + " effetti letti: " + intFormat.format(cEffettiLetti));
				input.close();
			} catch (IOException ioException) {
			}
		}
		try {
			recOutSarap.close();
			recOutSarsya.close();
			recOutAmdPstitxt.close();
			System.out.println(dateFormat.format(new Date()) + " -----FINE ELABORAZIONE----- ");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static void scriviPstiWeb() {
		if (arrEffetti[3].length() < 1) { // data iscrizione
			arrEffetti[3]="00000000";
		}
		if (arrEffetti[4].length() < 1) { // data levata
			arrEffetti[4]="00000000";
		}
		if (arrEffetti[7].length() < 1) { // data scadenza
			arrEffetti[7]="00000000";
		}
		if (arrEffetti[8].length() < 1) { // tipo scadenza
			arrEffetti[8]=" ";
		}
		String codIstatLev = "000000";
		if (mapInfCodComCodIstat.containsKey(arrEffetti[6])) {
			codIstatLev = mapInfCodComCodIstat.get(arrEffetti[6]);
    	}
		
		Double importo = Double.parseDouble(arrEffetti[10]);
		Double importoCorrente = Double.parseDouble(arrEffetti[12]);
		outRigaAmdPstitxt = " " +
				";" +
				"I" +
				";" +
				LeftZero.LeftZero(arrEffetti[1], 9, "0") +
				";" +
				arrEffetti[16].subSequence(2, 4) + 	arrEffetti[16].subSequence(4, 6) +
				";" +
				arrEffetti[2] +
				";" +
				codIstatLev +
				";" +
				arrEffetti[3] +
				";" +
				arrEffetti[4] +
				";" +
				arrEffetti[8] +
				";" +
				arrEffetti[14] +
				";" +
				arrEffetti[15] +
				";" +
				arrEffetti[9] +
				";" +
				arrEffetti[4] +
				";" +
				PadString.padRight(arrEffetti[11], 3) + 
				String.valueOf(decimalFormat.format(importo).replace(",", "") +
				String.valueOf(decimalFormat.format(importoCorrente).replace(",", "")));
		try {
			recOutAmdPstitxt.write(PadString.padRight(outRigaAmdPstitxt, 130) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void scriviSarsya() {
		outRigaSarsya="DATEPROT00000";
		for (int i = 0; i < 150; i++) {
			if (arrSarsya[i][4] > 0) {
				sarsya.setCodistatprv(LeftZero.LeftZero(Integer.toString(i), 3, "0"));
				sarsya.setDatiscmin(arrSarsya[i][0].toString());
				sarsya.setDatiscmax(arrSarsya[i][1].toString());
				sarsya.setDatlevmin(arrSarsya[i][2].toString());
				sarsya.setDatlevmax(arrSarsya[i][3].toString());
				sarsya.setTotana(0);
				sarsya.setToteff(arrSarsya[i][5]);
				try {
					sarsyaMapper.insert(sarsya);
				} catch (Exception e) { // chiave duplicata?
					if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
				    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), "Sc02_00_SARAP_SARSYA");
						e.printStackTrace();
					} else {
						sarsya.setCodistatprv(null);
						sarsya.setTotana(null);
						SarsyaExample sarsyaExample = new SarsyaExample();
						sarsyaExample.createCriteria().andCodistatprvEqualTo(LeftZero.LeftZero(Integer.toString(i), 3, "0"));
				        sarsyaMapper.updateByExampleSelective(sarsya, sarsyaExample);
					}
				}
			}
			
			if (i == 130) {
				String alberto = null;
			}
			
			
			if (arrSarsya[i][4] == 0) {
				outRigaSarsya = outRigaSarsya + "0000000000000000000000000000000000000000000000";
			} else {
				outRigaSarsya = outRigaSarsya +
						arrSarsya[i][0] + 
						arrSarsya[i][1] + 
						arrSarsya[i][2] + 
						arrSarsya[i][3] + 
						LeftZero.LeftZero(Integer.toString(arrSarsya[i][4]), 7, "0") +
						LeftZero.LeftZero(Integer.toString(arrSarsya[i][5]), 7, "0");
			}		
		}
		try {
			recOutSarsya.write(PadString.padRight(outRigaSarsya, 4064) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void creaArrSarsya(String[] arrEffetti) {
		String codIstatLev = "000000";
		if (mapInfCodComCodIstat.containsKey(arrEffetti[6])) {
			codIstatLev = mapInfCodComCodIstat.get(arrEffetti[6]);
    	}
		Integer codIstatPrv = Integer.parseInt(codIstatLev.substring(0,3)); 
		Integer datIsc = Integer.parseInt(arrEffetti[3]);
		Integer datLev = Integer.parseInt(arrEffetti[4]);
		
		if (datIsc < arrSarsya[codIstatPrv][0]) {
			arrSarsya[codIstatPrv][0] = datIsc;
		}
		if (datIsc > arrSarsya[codIstatPrv][1]) {
			arrSarsya[codIstatPrv][1] = datIsc;
		}
		if (datLev < arrSarsya[codIstatPrv][2]) {
			arrSarsya[codIstatPrv][2] = datLev;
		}
		if (datLev > arrSarsya[codIstatPrv][3]) {
			arrSarsya[codIstatPrv][3] = datLev;
		}
		arrSarsya[codIstatPrv][5] = arrSarsya[codIstatPrv][5] + 1;
	}
	
	
	
	private static void scriviSarap() {
//01  SARAP-R.
//	    03 PARFIS-SARAP-R.
//	       05 FLGCAN-SARAP-R              PIC X(001).
//	       05 KANAGRA-SARAP-R.
//	          07 KFLGNUM-SARAP-R          PIC X(001).
//	          07 KANAINF-SARAP-R          PIC 9(009).
//	          07 PRGPRO-SARAP-R           PIC 9(003).
//	       05 LPRO-SARAP-R                PIC 9(004)  COMP-4.
//	    03 PARVAR-SARAP-R.
//	       05 TABPRO-SARAP-R.
//	          07 ELEPRO-SARAP-R OCCURS 20.
//	             09 FLGCEFF-SARAP-R       PIC  X(001).
//	             09 IDEFF-SARAP-R         PIC  9(005)      COMP-3.
//	             09 DATINS-SARAP-R        PIC  9(004).
//	             09 CCIAAPUB-SARAP-R      PIC  X(002).
//	             09 ISTLEV-SARAP-R        PIC  9(006).
//	             09 DATISC-SARAP-R        PIC  9(008)      COMP-3.
//	             09 DATSCA-SARAP-R        PIC  9(008)      COMP-3.
//	             09 TIPSCA-SARAP-R        PIC  X(001).
//	             09 CMMPAG-SARAP-R        PIC  X(003).
//	             09 STAEFF-SARAP-R        PIC  X(001).
//	             09 TIPEFF-SARAP-R        PIC  X(001).
//	             09 DATLEV-SARAP-R        PIC  9(008)      COMP-3.
//	             09 VALUTL-SARAP-R        PIC  X(003).
//	             09 IMPLEV-SARAP-R        PIC  9(012)V999  COMP-3.
//	             09 IMPCOR-SARAP-R        PIC  9(012)V999  COMP-3.

		cScrittiSarap++;
		if (cScrittiSarap == 1) {
//			sarapMapper.deleteByExample(null);
		}
		sarap.setFlgcan(" ");
		sarap.setKanagra(LeftZero.LeftZero(Integer.toString(kanagraPrec), 9, "0"));
		sarap.setPrgrec(LeftZero.LeftZero(Integer.toString(progrRec), 4, "0"));
		sarap.setLenpro(LeftZero.LeftZero(Integer.toString((indTab * 81)), 5, "0"));
		sarap.setRigapro(outRigaSarap);
//		sarapMapper.insert(sarap);
		
		outRigaSarap = " I" +
				LeftZero.LeftZero(Integer.toString(kanagraPrec), 9, "0") +
				LeftZero.LeftZero(Integer.toString(progrRec), 4, "0") +
				LeftZero.LeftZero(Integer.toString((indTab * 56)), 5, "0") + 
				outRigaSarap;
		try {
			recOutSarap.write(PadString.padRight(outRigaSarap, 1640) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		indTab=0;
		outRigaSarap = "";
	}
	
	private static void intabellaEffetti(String[] arrEffetti) {

//	       05 TABPRO-SARAP-R.
//	          07 ELEPRO-SARAP-R OCCURS 20.
//	             09 FLGCEFF-SARAP-R       PIC  X(001).
//	             09 IDEFF-SARAP-R         PIC  9(005)      COMP-3.
//	             09 DATINS-SARAP-R        PIC  9(004).
//	             09 CCIAAPUB-SARAP-R      PIC  X(002).
//	             09 ISTLEV-SARAP-R        PIC  9(006).
//	             09 DATISC-SARAP-R        PIC  9(008)      COMP-3.
//	             09 DATSCA-SARAP-R        PIC  9(008)      COMP-3.
//	             09 TIPSCA-SARAP-R        PIC  X(001).
//	             09 CMMPAG-SARAP-R        PIC  X(003).
//	             09 STAEFF-SARAP-R        PIC  X(001).
//	             09 TIPEFF-SARAP-R        PIC  X(001).
//	             09 DATLEV-SARAP-R        PIC  9(008)      COMP-3.
//	             09 VALUTL-SARAP-R        PIC  X(003).
//	             09 IMPLEV-SARAP-R        PIC  9(012)V999  COMP-3.
//	             09 IMPCOR-SARAP-R        PIC  9(012)V999  COMP-3.

		if (progrRec == 1) {
			if (idEff == 0) {
				outRigaSarap = "";
			}
		}

		if (arrEffetti[3].length() < 1) { // data iscrizione
			arrEffetti[3]="00000000";
		}
		if (arrEffetti[4].length() < 1) { // data levata
			arrEffetti[4]="00000000";
		}
		if (arrEffetti[7].length() < 1) { // data scadenza
			arrEffetti[7]="00000000";
		}
		if (arrEffetti[8].length() < 1) { // tipo scadenza
			arrEffetti[8]=" ";
		}
		String codIstatLev = "000000";
		if (mapInfCodComCodIstat.containsKey(arrEffetti[6])) {
			codIstatLev = mapInfCodComCodIstat.get(arrEffetti[6]);
    	}
		
		Double importo = Double.parseDouble(arrEffetti[10]);
		Double importoCorrente = Double.parseDouble(arrEffetti[12]);

		if (indTab > 19) {
			scriviSarap();
			indTab = 0;
			progrRec++;
		}
		indTab++;
		totEff++;
		idEff++;
		outRigaSarap = outRigaSarap +
				" " +
				LeftZero.LeftZero(Integer.toString(idEff), 5, "0") +
				arrEffetti[16].subSequence(2, 4) +
				arrEffetti[16].subSequence(4, 6) +
				arrEffetti[2] +
				codIstatLev +
				arrEffetti[3] +
				arrEffetti[7] + 
				arrEffetti[8] + 
				arrEffetti[14] + 
				arrEffetti[15] +
				arrEffetti[9] + 
				arrEffetti[4] + 
				PadString.padRight(arrEffetti[11], 3) + 
				String.valueOf(decimalFormat.format(importo).replace(",", "") +
				String.valueOf(decimalFormat.format(importoCorrente).replace(",", "")));
	}
}