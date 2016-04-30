package it.amedeo.aggiornamento.from.FS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.amedeo.mybatis.javaclient.AmedeodateaggMapper;
import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javaclient.EffettiMapper;
import it.amedeo.mybatis.javaclient.InfcomuniMapper;
import it.amedeo.mybatis.javamodel.Amedeodateagg;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.Effetti;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.utils.CreaLogElab;
import it.amedeo.utils.CreaParole;
import it.amedeo.utils.FindSQLExceptionWithVendorErrorCode;
import it.amedeo.utils.MyBatisConnectionFactory;

public class Am01_00_AggioramentoFromFS {
	private static String dateFlusso;
	private static Anagrafiche anagrafiche = null;
	private static Long kAnagra = new Long(0);
	private static String sDataIns = null;
	private static String tipoRecordPrec = null;
	private static Map<String, String> mapPrvReg = null;
	private static Map<String, String> mapInfCodComCodIstat = null;
	private static Map<String, String> mapDateAggMin = null;
	private static Map<String, String> mapDateAggMax = null;
	private static Map<Long, Integer> mapCointesta = new HashMap<Long, Integer>();

	public static void main(String[] args) throws IOException {
		
		args = new String[2];		
		args[0] = "D:\\Google Drive\\FileInfoCam\\INFOCAMERE1601\\INFANA01";
		args[1] = "D:\\Google Drive\\FileInfoCam\\INFOCAMERE1601\\INFEFF01";
		
		if (args.length<1){
			System.out.println("\nParametri in input mancanti.\nEsecuzione interrotta.");
			System.exit(1);
		}
		
//		ReadFilesInDir feadFilesInDir = new ReadFilesInDir();
//		ArrayList<String> listFiles = feadFilesInDir.ReadFilesInDir("d:/sharew7");
		
		DateFormat dFIns = new SimpleDateFormat("yyyyMMdd HHmmss");		            		
		Date dataIns = new Date();
		sDataIns = dFIns.format(dataIns);
		String riga = "";
		Integer lettiTot=0;
		Integer lettiAnag=0;
		Integer doppiAnag=0;
		Integer scrittiAnag=0;
		Integer lettiEff=0;
		Integer scrittiEff=0;

//		File fileOutPippo = new File("D:/FileInfoCam/DBpippo.txt");
//		fileOutPippo.delete();
//		AnagraficheMapperExt anagraficheMapperExt = null;
//		anagraficheMapperExt = sqlSession.getMapper(AnagraficheMapperExt.class);
//		anagraficheMapperExt.selectPippo();
		
		InfcomuniExample infcomuniExample = new InfcomuniExample();
		infcomuniExample.createCriteria().andCodregioneNotEqualTo("00");
		List<Infcomuni> lstInfComuni = MyBatisConnectionFactory.getSqlSession().getMapper(InfcomuniMapper.class).selectByExample(infcomuniExample);
		mapPrvReg = new HashMap<String, String>();
		mapInfCodComCodIstat = new HashMap<String, String>();
		for (int i = 0; i < lstInfComuni.size(); i++) {
			mapPrvReg.put(lstInfComuni.get(i).getSiglaprovincia().trim(), lstInfComuni.get(i).getCodregione());
			mapInfCodComCodIstat.put(lstInfComuni.get(i).getInfcodicecomune(), lstInfComuni.get(i).getCodistat());
		}
		
    	new CreaLogElab(" ----- INIZIO ELABORAZIONE -----", new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab(" ----- INIZIO ELABORAZIONE ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingMethod().getName());

		riga = "";
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			while ((riga = br.readLine()) != null) {
				lettiTot++;
				if (riga != null) {
					if (riga.startsWith("000000001TS")) {
		            	new CreaLogElab(" File Anagrafiche in elaborazione:" + riga, new Object(){}.getClass().getEnclosingMethod().getName());
		            	dateFlusso = riga.substring(37, 43) + riga.substring(45, 51);
					}
		            anagrafiche = new Anagrafiche();
		            if (riga.substring(9, 11).equals("AN")) {
		            	lettiAnag++;
		            	anagrafiche.setFlgcan("0");
		            	anagrafiche.setDateflusso(dateFlusso);
		            	anagrafiche.setKanagra(Long.parseLong(riga.substring(11, 20)));
		            	anagrafiche.setNominativo(riga.substring(20, 263).trim());
		            	anagrafiche.setCodicefiscale(riga.substring(263, 279).trim());
		            	if (mapPrvReg.containsKey(riga.substring(282, 284).trim())) {
							anagrafiche.setCodregioneres(mapPrvReg.get(riga.substring(282, 284).trim()));
		            	} else {
		            		anagrafiche.setCodregioneres("00");
		            		if (!riga.substring(282, 284).trim().equals("")) {
			                	new CreaLogElab("Recupero codreg - provincia res. sconosciuta. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingMethod().getName());	            			
		            		}
		            	}
		            	if (mapInfCodComCodIstat.containsKey(riga.substring(284, 289))) {
		            		String codIstat = mapInfCodComCodIstat.get(riga.substring(284, 289));
		            		String codPrv = codIstat.substring(0,3);
		            		String codCom = codIstat.substring(3, 6);
							anagrafiche.setCodistatprvres(codPrv);
							anagrafiche.setCodistatcomres(codCom);
		            	} else {
							anagrafiche.setCodistatprvres("000");
							anagrafiche.setCodistatcomres("000");
		            		if (!riga.substring(284, 289).trim().equals("")) {
			                	new CreaLogElab("Recupero istatcodprv e istatcodcom  - Infcodcomres sconosciuto. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingMethod().getName());	            			
		            		}
		            	}
		            	anagrafiche.setCodicestatoresidenza(riga.substring(279, 282).trim());
		            	anagrafiche.setSiglaprovinciaresidenza(riga.substring(282, 284).trim());	      
		            	anagrafiche.setCodicecomuneresidenza(riga.substring(284, 289).trim());	  
		            	anagrafiche.setIndirizzoresidenza(riga.substring(289, 369).trim());
		            	anagrafiche.setDatanascita(riga.substring(369, 377).trim());
		            	anagrafiche.setCodicestatonascita(riga.substring(377, 380).trim());
		            	if (mapInfCodComCodIstat.containsKey(riga.substring(382, 387))) {
		            		String codIstat = mapInfCodComCodIstat.get(riga.substring(382, 387));
		            		String codPrv = codIstat.substring(0,3);
		            		String codCom = codIstat.substring(3, 6);
							anagrafiche.setCodistatprvnas(codPrv);
							anagrafiche.setCodistatcomnas(codCom);
		            	} else {
							anagrafiche.setCodistatprvnas("000");
							anagrafiche.setCodistatcomnas("000");
		            		if (!riga.substring(382, 387).trim().equals("")) {
			                	new CreaLogElab("Recupero istatcodprv e istatcodcom  - Infcodcomnas sconosciuto. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingMethod().getName());	            			
		            		}
		            	}
		            	anagrafiche.setSiglaprovincianascita(riga.substring(380, 382).trim());	            	
		            	anagrafiche.setCodicecomunenascita(riga.substring(382, 387).trim());
		            	anagrafiche.setLuogonascita(riga.substring(387, 467).trim());
		            	anagrafiche.setNumtoteff(0);
		            	anagrafiche.setImptoteff(0.0);
		            	anagrafiche.setDataprimoeff("99999999");
		            	anagrafiche.setDataulteff("00000000");
		            	anagrafiche.setDatatimeins(sDataIns);
		            	try {
		            		MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).insert(anagrafiche);
			            	scrittiAnag++;
			            	CreaParole.creaParole(riga, dateFlusso, sDataIns, mapPrvReg);
			            	if (((scrittiAnag / 10000) * 10000) == scrittiAnag) {
			                	new CreaLogElab("Anagrafiche nuove inserite............: " + scrittiAnag, new Object(){}.getClass().getEnclosingMethod().getName());		                    
			            	}
						} catch (Exception e) { // chiave duplicata?
							if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
						    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingMethod().getName());
								e.printStackTrace();
							} else {
								doppiAnag++;
							}
						}
					}
		        }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	new CreaLogElab(" ----- FINE ELABORAZIONE ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab("Anagrafiche lette.....................: " + lettiAnag, new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab("Anagrafiche gia' censite scartate.....: " + doppiAnag, new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab("Anagrafiche nuove inserite............: " + scrittiAnag, new Object(){}.getClass().getEnclosingMethod().getName());
    	MyBatisConnectionFactory.commitSqlSession();
		
        ArrayList<String> lstEff = new ArrayList<String>();
    	new CreaLogElab(" ----- INIZIO ELABORAZIONE EFFETTI -----", new Object(){}.getClass().getEnclosingMethod().getName());
    	mapDateAggMin = new HashMap<String, String>();
    	mapDateAggMax = new HashMap<String, String>(); 
    	setTipoRecordPrec("AC");
		riga = "";
		try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
			while ((riga = br.readLine()) != null) {
				if (riga != null) {
					if (riga.startsWith("000000001TS")) {
		            	new CreaLogElab(" File Effetti in elaborazione:" + riga, new Object(){}.getClass().getEnclosingMethod().getName());
		            	dateFlusso = riga.substring(37, 43) + riga.substring(45, 51);
					}
		            if (riga.substring(9, 11).equals("EF")) {
		            	lettiEff++;
						if (!riga.substring(9, 11).equals(tipoRecordPrec)) {
							lstEff = new ArrayList<String>();
							setTipoRecordPrec(riga.substring(9, 11));
						}
		            	lstEff.add(riga);
					} else {
						if (riga.substring(9, 11).equals("AC")) {

//							if (getTipoRecordPrec().equals("AC")) {
//								if (mapCointesta.containsKey(Long.parseLong(riga.substring(11, 20)))) {
//									mapCointesta.put(Long.parseLong(riga.substring(11, 20)), (mapCointesta.get(Long.parseLong(riga.substring(11, 20))) + 1));
//								} else {
//									mapCointesta.put(Long.parseLong(riga.substring(11, 20)), 1);
//								}
//							}
							
							setTipoRecordPrec("AC");
							kAnagra = Long.parseLong(riga.substring(11, 20));
							for (Iterator<String> iterator2 = lstEff.iterator(); iterator2.hasNext();) {
								String stringa = (String) iterator2.next();
					            Effetti effetti = new Effetti();
					            effetti.setDateflusso(dateFlusso);
					            effetti.setKanagra(kAnagra);
					            effetti.setCciaapubblicazione(stringa.substring(11, 13).trim());
					            effetti.setDataiscrizione(stringa.substring(13, 21).trim());
					            effetti.setDatalevata(stringa.substring(21, 29).trim());
					            effetti.setSiglaprovincialevata(stringa.substring(29, 31).trim());
					            effetti.setCodicecomunelevata(stringa.substring(31, 36).trim());				            
					            effetti.setDatascadenza(stringa.substring(36, 44).trim());
					            effetti.setTiposcadenza(stringa.substring(44, 45).trim());
					            effetti.setTipoeffetto(stringa.substring(45, 46).trim());
					            effetti.setImporto((Double.parseDouble(stringa.substring(46, 61))) / 1000);
					            effetti.setCodicevaluta(stringa.substring(61, 64).trim());
					            effetti.setImportocorrente((Double.parseDouble(stringa.substring(64, 79))) / 1000);
					            effetti.setCodicevalutacorrente(stringa.substring(79, 82).trim());
					            effetti.setCodmotmancpag(stringa.substring(82, 85).trim());
					            effetti.setStatoeffetto(stringa.substring(85, 86).trim());
					            effetti.setDatatimeins(sDataIns);
					            try {
					            	if ("".equals(mapDateAggMin.get(effetti.getCciaapubblicazione())) || 
					            			mapDateAggMin.get(effetti.getCciaapubblicazione()) == null || 
					            			(mapDateAggMin.get(effetti.getCciaapubblicazione()).compareTo(effetti.getDataiscrizione()) > 0)) {
					            		mapDateAggMin.put(effetti.getCciaapubblicazione(), effetti.getDataiscrizione());		
									} 
					            	if ("".equals(mapDateAggMax.get(effetti.getCciaapubblicazione())) || 
					            			mapDateAggMax.get(effetti.getCciaapubblicazione()) == null || 
					            			(mapDateAggMax.get(effetti.getCciaapubblicazione()).compareTo(effetti.getDataiscrizione()) < 0)) {
					            		mapDateAggMax.put(effetti.getCciaapubblicazione(), effetti.getDataiscrizione());		
									} 

								} catch (Exception e) {
								}
			            		MyBatisConnectionFactory.getSqlSession().getMapper(EffettiMapper.class).insert(effetti);
					            scrittiEff++;
				            	if (((scrittiEff / 10000) * 10000) == scrittiEff) {
				                	new CreaLogElab("Effetti inseriti............: " + scrittiEff, new Object(){}.getClass().getEnclosingMethod().getName());		                    
				            	}
				            	anagrafiche = MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).selectByPrimaryKey(kAnagra);
				            	if (anagrafiche != null) {
					            	Integer numTotEff = anagrafiche.getNumtoteff();
					            	Double impTotEff = anagrafiche.getImptoteff();
					            	String dataPrimoEff = anagrafiche.getDataprimoeff();
					            	String dataUltEff = anagrafiche.getDataulteff();
					            	anagrafiche = new Anagrafiche();
					            	anagrafiche.setKanagra(kAnagra);
					    			anagrafiche.setNumtoteff(numTotEff + 1);
					    			if (effetti.getCodicevaluta().trim().equals("EU")) {
						    			anagrafiche.setImptoteff(impTotEff + effetti.getImporto());
									} else {
						    			anagrafiche.setImptoteff(impTotEff + effetti.getImportocorrente());
									}
					    			if (effetti.getDataiscrizione().compareTo(dataPrimoEff) < 0) {
										anagrafiche.setDataprimoeff(effetti.getDataiscrizione());
									}
					    			if (effetti.getDataiscrizione().compareTo(dataUltEff) > 0) {
										anagrafiche.setDataulteff(effetti.getDataiscrizione());
									}
					    			MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).updateByPrimaryKeySelective(anagrafiche);
								}
							}
						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Map.Entry<String, String> entry : mapDateAggMin.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    Amedeodateagg amedeodateagg =  MyBatisConnectionFactory.getSqlSession().getMapper(AmedeodateaggMapper.class).selectByPrimaryKey(key);
		    if (amedeodateagg == null) {
		    	amedeodateagg = new Amedeodateagg();
		    	amedeodateagg.setCciaa(key);
		    	amedeodateagg.setDatamin(value);
			    MyBatisConnectionFactory.getSqlSession().getMapper(AmedeodateaggMapper.class).insert(amedeodateagg);
			} else {
				if (amedeodateagg.getDatamin().compareTo(value) > 0) {
					amedeodateagg.setDatamin(value);
					MyBatisConnectionFactory.getSqlSession().getMapper(AmedeodateaggMapper.class).updateByPrimaryKey(amedeodateagg);
				}
			}
		}
		for (Map.Entry<String, String> entry : mapDateAggMax.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    Amedeodateagg amedeodateagg =  MyBatisConnectionFactory.getSqlSession().getMapper(AmedeodateaggMapper.class).selectByPrimaryKey(key);
		    if (amedeodateagg == null) {
		    	amedeodateagg = new Amedeodateagg();
		    	amedeodateagg.setCciaa(key);
		    	amedeodateagg.setDatamax(value);
			    MyBatisConnectionFactory.getSqlSession().getMapper(AmedeodateaggMapper.class).insert(amedeodateagg);
			} else {
				if ("".equals(amedeodateagg.getDatamax()) || 
						amedeodateagg.getDatamax() == null ||  
						amedeodateagg.getDatamax().compareTo(value) < 0) {
					amedeodateagg.setDatamax(value);
					MyBatisConnectionFactory.getSqlSession().getMapper(AmedeodateaggMapper.class).updateByPrimaryKey(amedeodateagg);
				}
			}
		}
		
		
//		for (Map.Entry<Long, Integer> entry : mapCointesta.entrySet()) {
//			Long key = entry.getKey();
//			Integer value = entry.getValue();
//			if (value > 1) {
//				System.out.println("Cointestatario=" + key + " " + value);
//			}
//		}
		
		
    	new CreaLogElab(" ----- FINE ELABORAZIONE EFFETTI -----", new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab("Effetti letti...............: " + lettiEff, new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab("Effetti inseriti............: " + scrittiEff, new Object(){}.getClass().getEnclosingMethod().getName());
    	new CreaLogElab(" ----- FINE ELABORAZIONE -----", new Object(){}.getClass().getEnclosingMethod().getName());
    	MyBatisConnectionFactory.commitSqlSession();
    	MyBatisConnectionFactory.closeSqlSession();
	}
		
		
	public static String readDoc(File f) {
	    String text = "";
	    int read, N = 1024 * 1024 * 10;
//	    int read, N = 600000000;
	    char[] buffer = new char[N];

	    try {
	        FileReader fr = new FileReader(f);
	        BufferedReader br = new BufferedReader(fr);

	        while(true) {
	            try {
					read = br.read(buffer, 0, N);
					text += new String(buffer, 0, read);

//					System.out.println(text);
					
					if(read < N) {
					    break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        try {
		        if (br != null) {
					br.close();
				}
			} catch (Exception e) {
			}
	    } catch(Exception ex) {
	        ex.printStackTrace();
	    }

	    return text;
	}


	public static String getTipoRecordPrec() {
		return tipoRecordPrec;
	}


	public static void setTipoRecordPrec(String tipoRecordPrec) {
		Am01_00_AggioramentoFromFS.tipoRecordPrec = tipoRecordPrec;
	}
}
