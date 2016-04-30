package it.amedeo.mybatis.handler.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javaclient.InfcomuniMapper;
import it.amedeo.mybatis.javaclient.StrcodcomnasMapper;
import it.amedeo.mybatis.javaclient.StrcodcomresMapper;
import it.amedeo.mybatis.javaclient.StrcodfiscMapper;
import it.amedeo.mybatis.javaclient.StrcodiceregioneMapper;
import it.amedeo.mybatis.javaclient.StrcodprvresMapper;
import it.amedeo.mybatis.javaclient.StrdatnasMapper;
import it.amedeo.mybatis.javaclient.ext.StrindirMapperExt;
import it.amedeo.mybatis.javaclient.ext.StrnomiMapperExt;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.AnagraficheExample;
import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.mybatis.javamodel.Strcodcomnas;
import it.amedeo.mybatis.javamodel.Strcodcomres;
import it.amedeo.mybatis.javamodel.Strcodfisc;
import it.amedeo.mybatis.javamodel.Strcodiceregione;
import it.amedeo.mybatis.javamodel.Strcodprvres;
import it.amedeo.mybatis.javamodel.Strdatnas;
import it.amedeo.utils.CreaLogElab;
import it.amedeo.utils.CreaNomeTabStringhe;
import it.amedeo.utils.FindSQLExceptionWithVendorErrorCode;
import it.amedeo.utils.MyBatisConnectionFactory;

@SuppressWarnings("rawtypes")
public class AggioramentoAnagrafiche_HandlerImpl  implements ResultHandler { 
	private static Anagrafiche anagrafiche = null;
	private static List<Infcomuni> lstInfComuni = null;
	private static DateFormat dFIns = new SimpleDateFormat("yyyyMMdd HHmmss");
	private static Date dataIns = new Date();
	private static String sDataIns = dFIns.format(dataIns);
	private static String riga = null;
	private static Integer lettiTot=0;
	private static Integer lettiAnag=0;
	private static Integer doppiAnag=0;
	private static Integer scrittiAnag=0;

	private static Map<String, String> mapPrvReg = null;
	private static Map<String, String> mapInfCodComCodIstat = null;

	private static InfcomuniExample infcomuniExample = null;
	
	@Override
	public void handleResult(ResultContext record) {
		Infanagrafiche infanagrafiche = (Infanagrafiche) record.getResultObject();
		riga = infanagrafiche.getRiga();
		lettiTot++;
		if (lstInfComuni == null) {
			infcomuniExample = new InfcomuniExample();
			infcomuniExample.createCriteria().andCodregioneNotEqualTo("00");
			lstInfComuni =  MyBatisConnectionFactory.getSqlSession().getMapper(InfcomuniMapper.class).selectByExample(infcomuniExample);
			mapPrvReg = new HashMap<String, String>();
			mapInfCodComCodIstat = new HashMap<String, String>();
			for (int i = 0; i < lstInfComuni.size(); i++) {
				mapPrvReg.put(lstInfComuni.get(i).getSiglaprovincia().trim(), lstInfComuni.get(i).getCodregione());
				mapInfCodComCodIstat.put(lstInfComuni.get(i).getInfcodicecomune(), lstInfComuni.get(i).getCodistat());
			}
		}
		
		if (!"".equals(riga) && riga != null) {
			if (lettiTot == 1) {
		    	new CreaLogElab(" ----- INIZIO ELABORAZIONE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
		    	new CreaLogElab(" ----- INIZIO ELABORAZIONE ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
			}
            if (riga.substring(9, 11).equals("TS")) {
            	new CreaLogElab(" File Anagrafiche elaborato:" + riga, new Object(){}.getClass().getEnclosingClass().getSimpleName());
			} else if (riga.substring(9, 11).equals("AN")) {
            	lettiAnag++;
            	
            	AnagraficheExample anagraficheExample = new AnagraficheExample();
            	anagraficheExample.createCriteria().andKanagraEqualTo(Long.parseLong(riga.substring(11, 20)));
            	List<Anagrafiche> lstAnag = MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).selectByExample(anagraficheExample);
            	if (lstAnag.size() > 0) {
					doppiAnag++;
				} else {
	    	        anagrafiche = new Anagrafiche();
	            	anagrafiche.setKanagra(Long.parseLong(riga.substring(11, 20)));
                	anagrafiche.setFlgcan("0");
                	anagrafiche.setDateflusso(infanagrafiche.getDateflusso());
                	anagrafiche.setNominativo(riga.substring(20, 263).trim());
                	anagrafiche.setCodicefiscale(riga.substring(263, 279).trim());
                	if (mapPrvReg.containsKey(riga.substring(282, 284).trim())) {
    					anagrafiche.setCodregioneres(mapPrvReg.get(riga.substring(282, 284).trim()));
                	} else {
                		anagrafiche.setCodregioneres("00");
                		if (!riga.substring(282, 284).trim().equals("")) {
    	                	new CreaLogElab("Recupero codreg - provincia res. sconosciuta. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingClass().getSimpleName());	            			
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
    	                	new CreaLogElab("Recupero istatcodprv e istatcodcom  - Infcodcomres sconosciuto. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingClass().getSimpleName());	            			
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
    	                	new CreaLogElab("Recupero istatcodprv e istatcodcom  - Infcodcomnas sconosciuto. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingClass().getSimpleName());	            			
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
            			anagraficheExample = new AnagraficheExample();
            			MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).insert(anagrafiche);
    	            	scrittiAnag++;
    	            	creaParole(riga, infanagrafiche.getDateflusso());
    	            	if (((scrittiAnag / 10000) * 10000) == scrittiAnag) {
    	                	new CreaLogElab("Anagrafiche nuove inserite............: " + scrittiAnag, new Object(){}.getClass().getEnclosingClass().getSimpleName());		                    
    	            	}
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
				}
			}
		}
	}


//	public static void main(String[] args) throws IOException {
//		
//		args = new String[2];		
//		args[0] = "D:\\FileInfoCam\\Anagrafiche2009.txt";
//		args[1] = "D:\\FileInfoCam\\Effetti2009.txt";
//		
//		
//		if (args.length<1){
//			System.out.println("\nParametri in input mancanti.\nEsecuzione interrotta.");
//			System.exit(1);
//		}
//		
////		ReadFilesInDir feadFilesInDir = new ReadFilesInDir();
////		ArrayList<String> listFiles = feadFilesInDir.ReadFilesInDir("d:/sharew7");
//		
//		DateFormat dFIns = new SimpleDateFormat("yyyyMMdd HHmmss");		            		
//		Date dataIns = new Date();
//		sDataIns = dFIns.format(dataIns);
//		String riga = "";
//		Integer lettiTot=0;
//		Integer lettiAnag=0;
//		Integer doppiAnag=0;
//		Integer scrittiAnag=0;
//		Integer lettiEff=0;
//		Integer scrittiEff=0;
//
//		MyBatisConnectionFactory.openSqlSessionFactory();
//		MyBatisConnectionFactory.openSqlSession();
//		sqlSession = MyBatisConnectionFactory.getSqlSession();
//		
//		anagraficheMapper = sqlSession.getMapper(AnagraficheMapper.class);
//        logelabMapper = sqlSession.getMapper(LogelabMapper.class);
//		strnomiMapperext = sqlSession.getMapper(StrnomiMapperext.class);
//		strindirMapperext = sqlSession.getMapper(StrindirMapperext.class);
//		strcodcomnasMapper = sqlSession.getMapper(StrcodcomnasMapper.class);
//		strcodcomresMapper = sqlSession.getMapper(StrcodcomresMapper.class);
//		strcodfiscMapper = sqlSession.getMapper(StrcodfiscMapper.class);
//		strcodiceregioneMapper = sqlSession.getMapper(StrcodiceregioneMapper.class);
//		strcodprvresMapper = sqlSession.getMapper(StrcodprvresMapper.class);
//		strdatnasMapper = sqlSession.getMapper(StrdatnasMapper.class);
//		infcomuniMapper = sqlSession.getMapper(InfcomuniMapper.class);
//
//		InfcomuniExample infcomuniExample = new InfcomuniExample();
//		infcomuniExample.createCriteria().andCodregioneNotEqualTo("00");
//		List<Infcomuni> lstInfComuni =  infcomuniMapper.selectByExample(infcomuniExample);
//		mapPrvReg = new HashMap<String, String>();
//		mapInfCodComCodIstat = new HashMap<String, String>();
//		for (int i = 0; i < lstInfComuni.size(); i++) {
//			mapPrvReg.put(lstInfComuni.get(i).getSiglaprovincia().trim(), lstInfComuni.get(i).getCodregione());
//			mapInfCodComCodIstat.put(lstInfComuni.get(i).getInfcodicecomune(), lstInfComuni.get(i).getCodistat());
//		}
//		
//    	new CreaLogElab(" ----- INIZIO ELABORAZIONE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab(" ----- INIZIO ELABORAZIONE ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
//
//		BUFFER_SIZE = 502;
//		bytes = new byte[BUFFER_SIZE];
//		in = new BufferedInputStream(new FileInputStream(args[0].toString()));
//		riga = "";
//		n = in.read(bytes, 0, BUFFER_SIZE);
//		while (n >= 0) {
//			riga = new String(bytes);
//			lettiTot++;
////			if (lettiTot > 91500) {
////				System.out.println("Record n.:" + lettiTot + "-" + riga.trim() + "-");				
////			}
//
//			if (riga != null) {
//	            if (riga.substring(9, 11).equals("TS")) {
//	            	new CreaLogElab(" File Anagrafiche elaborato:" + riga, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//				}
//	            anagrafiche = new Anagrafiche();
//	            if (riga.substring(9, 11).equals("AN")) {
//	            	lettiAnag++;
//	            	anagrafiche.setFlgcan("0");
//	            	anagrafiche.setKanagra(Integer.parseInt(riga.substring(11, 20)));	            	
//	            	anagrafiche.setNominativo(riga.substring(20, 263).trim());
//	            	anagrafiche.setCodicefiscale(riga.substring(263, 279).trim());
//	            	if (mapPrvReg.containsKey(riga.substring(282, 284).trim())) {
//						anagrafiche.setCodregioneres(mapPrvReg.get(riga.substring(282, 284).trim()));
//	            	} else {
//	            		anagrafiche.setCodregioneres("00");
//	            		if (!riga.substring(282, 284).trim().equals("")) {
//		                	new CreaLogElab("Recupero codreg - provincia res. sconosciuta. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingClass().getSimpleName());	            			
//	            		}
//	            	}
//	            	if (mapInfCodComCodIstat.containsKey(riga.substring(284, 289))) {
//	            		String codIstat = mapInfCodComCodIstat.get(riga.substring(284, 289));
//	            		String codPrv = codIstat.substring(0,3);
//	            		String codCom = codIstat.substring(3, 6);
//						anagrafiche.setCodistatprvres(codPrv);
//						anagrafiche.setCodistatcomres(codCom);
//	            	} else {
//						anagrafiche.setCodistatprvres("000");
//						anagrafiche.setCodistatcomres("000");
//	            		if (!riga.substring(284, 289).trim().equals("")) {
//		                	new CreaLogElab("Recupero istatcodprv e istatcodcom  - Infcodcomres sconosciuto. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingClass().getSimpleName());	            			
//	            		}
//	            	}
//	            	anagrafiche.setCodicestatoresidenza(riga.substring(279, 282).trim());
//	            	anagrafiche.setSiglaprovinciaresidenza(riga.substring(282, 284).trim());	      
//	            	anagrafiche.setCodicecomuneresidenza(riga.substring(284, 289).trim());	  
//	            	anagrafiche.setIndirizzoresidenza(riga.substring(289, 369).trim());
//	            	anagrafiche.setDatanascita(riga.substring(369, 377).trim());
//	            	anagrafiche.setCodicestatonascita(riga.substring(377, 380).trim());
//	            	if (mapInfCodComCodIstat.containsKey(riga.substring(382, 387))) {
//	            		String codIstat = mapInfCodComCodIstat.get(riga.substring(382, 387));
//	            		String codPrv = codIstat.substring(0,3);
//	            		String codCom = codIstat.substring(3, 6);
//						anagrafiche.setCodistatprvnas(codPrv);
//						anagrafiche.setCodistatcomnas(codCom);
//	            	} else {
//						anagrafiche.setCodistatprvnas("000");
//						anagrafiche.setCodistatcomnas("000");
//	            		if (!riga.substring(382, 387).trim().equals("")) {
//		                	new CreaLogElab("Recupero istatcodprv e istatcodcom  - Infcodcomnas sconosciuto. Record: " + riga.trim(), new Object(){}.getClass().getEnclosingClass().getSimpleName());	            			
//	            		}
//	            	}
//	            	anagrafiche.setSiglaprovincianascita(riga.substring(380, 382).trim());	            	
//	            	anagrafiche.setCodicecomunenascita(riga.substring(382, 387).trim());
//	            	anagrafiche.setLuogonascita(riga.substring(387, 467).trim());
//	            	anagrafiche.setNumtoteff(0);
//	            	anagrafiche.setImptoteff(0.0);
//	            	anagrafiche.setDataprimoeff("99999999");
//	            	anagrafiche.setDataulteff("00000000");
//	            	anagrafiche.setDatatimeins(sDataIns);
//	            	try {
//		            	anagraficheMapper.insert(anagrafiche);
//		            	scrittiAnag++;
//		            	creaParole(riga);
//		            	if (((scrittiAnag / 10000) * 10000) == scrittiAnag) {
//		                	new CreaLogElab("Anagrafiche nuove inserite............: " + scrittiAnag, new Object(){}.getClass().getEnclosingClass().getSimpleName());		                    
//		                    sqlSession.commit();
//		            	}
//					} catch (Exception e) { // chiave duplicata?
//						if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
//					    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());
//							e.printStackTrace();
//						} else {
////					        anagraficheMapper.updateByPrimaryKey(anagrafiche);
//							doppiAnag++;
//						}
//					}
//				}
//	        }
//        	n = in.read(bytes, 0, BUFFER_SIZE);
//		}
//		
//    	new CreaLogElab(" ----- FINE ELABORAZIONE ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab("Anagrafiche lette.....................: " + lettiAnag, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab("Anagrafiche gia' censite scartate.....: " + doppiAnag, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab("Anagrafiche nuove inserite............: " + scrittiAnag, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//        sqlSession.commit();
//		
//		String giaScaricatiEff = "si";
//        EffettiMapper effettiMapper = sqlSession.getMapper(EffettiMapper.class);
//        ArrayList<String> lstEff = new ArrayList<String>();
//    	new CreaLogElab(" ----- INIZIO ELABORAZIONE EFFETTI -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
//
//    	BUFFER_SIZE = 202;
//		bytes = new byte[BUFFER_SIZE];
//		in = new BufferedInputStream(new FileInputStream(args[1].toString()));
//		riga = "";
//		n = in.read(bytes, 0, BUFFER_SIZE);
//    	while (n >= 0) {
//			riga = new String(bytes);
//			if (riga != null) {
//	            if (riga.substring(9, 11).equals("TS")) {
//	            	new CreaLogElab(" File Effetti elaborato:" + riga, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//				}
//	            if (riga.substring(9, 11).equals("EF")) {
//	            	if (giaScaricatiEff.equals("si")) {
//						lstEff.clear();
//						giaScaricatiEff = "no";
//					}
//	            	lstEff.add(riga);
//	            	lettiEff++;
//				} else {
//					if (riga.substring(9, 11).equals("AC")) {
//						kAnagra = Integer.parseInt(riga.substring(11, 20));
//						for (Iterator<String> iterator2 = lstEff.iterator(); iterator2.hasNext();) {
//							String stringa = (String) iterator2.next();
//				            Effetti effetti = new Effetti();
//				            effetti.setKanagra(kAnagra);
//				            effetti.setCciaapubblicazione(stringa.substring(11, 13).trim());
//				            effetti.setDataiscrizione(stringa.substring(13, 21).trim());
//				            effetti.setDatalevata(stringa.substring(21, 29).trim());
//				            effetti.setSiglaprovincialevata(stringa.substring(29, 31).trim());
//				            effetti.setCodicecomunelevata(stringa.substring(31, 36).trim());				            
//				            effetti.setDatascadenza(stringa.substring(36, 44).trim());
//				            effetti.setTiposcadenza(stringa.substring(44, 45).trim());
//				            effetti.setTipoeffetto(stringa.substring(45, 46).trim());
//				            effetti.setImporto((Double.parseDouble(stringa.substring(46, 61))) / 1000);
//				            effetti.setCodicevaluta(stringa.substring(61, 64).trim());
//				            effetti.setImportocorrente((Double.parseDouble(stringa.substring(64, 79))) / 1000);
//				            effetti.setCodicevalutacorrente(stringa.substring(79, 82).trim());
//				            effetti.setCodmotmancpag(stringa.substring(82, 85).trim());
//				            effetti.setStatoeffetto(stringa.substring(85, 86).trim());
//				            effetti.setDatatimeins(sDataIns);
//
//				            effettiMapper.insert(effetti);
//				            scrittiEff++;
//			            	if (((scrittiEff / 10000) * 10000) == scrittiEff) {
//			                	new CreaLogElab("Effetti inseriti............: " + scrittiEff, new Object(){}.getClass().getEnclosingClass().getSimpleName());		                    
//			                    sqlSession.commit();							
//			            	}
//			            	anagrafiche = anagraficheMapper.selectByPrimaryKey(kAnagra);
//			            	Integer numTotEff = anagrafiche.getNumtoteff();
//			            	Double impTotEff = anagrafiche.getImptoteff();
//			            	String dataPrimoEff = anagrafiche.getDataprimoeff();
//			            	String dataUltEff = anagrafiche.getDataulteff();
//			            	anagrafiche = new Anagrafiche();
//			            	anagrafiche.setKanagra(kAnagra);
//			    			anagrafiche.setNumtoteff(numTotEff + 1);
//			    			if (effetti.getCodicevaluta().trim().equals("EU")) {
//				    			anagrafiche.setImptoteff(impTotEff + effetti.getImporto());
//							} else {
//				    			anagrafiche.setImptoteff(impTotEff + effetti.getImportocorrente());
//							}
//			    			if (effetti.getDataiscrizione().compareTo(dataPrimoEff) < 0) {
//								anagrafiche.setDataprimoeff(effetti.getDataiscrizione());
//							}
//			    			if (effetti.getDataiscrizione().compareTo(dataUltEff) > 0) {
//								anagrafiche.setDataulteff(effetti.getDataiscrizione());
//							}
//			    			anagraficheMapper.updateByPrimaryKeySelective(anagrafiche);
//						}
//						giaScaricatiEff = "si";
//					}
//				}
//			}
//        	n = in.read(bytes, 0, BUFFER_SIZE);
//		}
//    	new CreaLogElab(" ----- FINE ELABORAZIONE EFFETTI -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab("Effetti letti...............: " + lettiEff, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab("Effetti inseriti............: " + scrittiEff, new Object(){}.getClass().getEnclosingClass().getSimpleName());
//    	new CreaLogElab(" ----- FINE ELABORAZIONE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
//        sqlSession.commit();
//    	sqlSession.close();
//	}
		
		
		
	private static void creaParole (String riga, String dateFlusso) { 
//		if (riga.startsWith("000000041AN008125109")) {
//			String a = null;
//		}
		String[] arrNome = riga.substring(20, 263).trim().split(" ");
		// elimino eventuali parole doppie (es. DI MAURO MAURO)
		Map<String, Long> mapNome = new HashMap<String, Long>();
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
		
		// rimpiazzo i caratteri speciali con null  (es. "F.LLI" divenda "FLLI"
    	String alphaAndDigits1 = riga.substring(20, 263).trim().replaceAll("[^a-zA-Z0-9 ]+","");
		arrNome = alphaAndDigits1.trim().split(" ");
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
    	
		// rimpiazzo i caratteri speciali con " " (es. "F.LLI" divenda "F LLI" 
    	String alphaAndDigits2 = riga.substring(20, 263).trim().replaceAll("[^a-zA-Z0-9 ]+"," ");
		arrNome = alphaAndDigits2.trim().split(" ");
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
		for (Map.Entry<String, Long> entry : mapNome.entrySet()) {
			if (entry.getKey().length() > 1 && !"".equals(entry.getKey()) && !" ".equals(entry.getKey()) && entry.getKey() != null) {
				Map<String, String> param = new HashMap<String, String>();
				CreaNomeTabStringhe creaNomeTabStringhe = new CreaNomeTabStringhe();
				param.put("tableName", creaNomeTabStringhe.CreaNomeTabStringhe("nomi", entry.getKey().length()));
				param.put("kanagra", riga.substring(11, 20));
				param.put("parola", entry.getKey());
				param.put("datatimeins", sDataIns);
				param.put("dateflusso", dateFlusso);
				MyBatisConnectionFactory.getSqlSession().getMapper(StrnomiMapperExt.class).insertTab(param);
			}
		}		

		

		String[] arrIndir = riga.substring(289, 369).trim().split(" ");
		// elimino eventuali parole doppie (es. VIA GIOVANNI DE GIOVANNI)
		Map<String, Long> mapIndir = new HashMap<String, Long>();
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 20) {
				arrIndir[i] = arrIndir[i].trim().substring(0, 20);
			}
			mapIndir.put(arrIndir[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
		
		// rimpiazzo i caratteri speciali con null  (es. "F.LLI" divenda "FLLI"
    	alphaAndDigits1 = riga.substring(289, 369).trim().replaceAll("[^a-zA-Z0-9 ]+","");
    	arrIndir = alphaAndDigits1.trim().split(" ");
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 20) {
				arrIndir[i] = arrIndir[i].trim().substring(0, 20);
			}
			mapIndir.put(arrIndir[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
    	
		// rimpiazzo i caratteri speciali con " " (es. "F.LLI" divenda "F LLI" 
    	alphaAndDigits2 = riga.substring(289, 369).trim().replaceAll("[^a-zA-Z0-9 ]+"," ");
    	arrIndir = alphaAndDigits2.trim().split(" ");
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 20) {
				arrIndir[i] = arrIndir[i].trim().substring(0, 20);
			}
			mapIndir.put(arrIndir[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
		for (Map.Entry<String, Long> entry : mapIndir.entrySet()) {
			if (entry.getKey().length() > 1) {
				Map<String, String> param = new HashMap<String, String>();
				CreaNomeTabStringhe creaNomeTabStringhe = new CreaNomeTabStringhe();
				param.put("tableName", creaNomeTabStringhe.CreaNomeTabStringhe("indir", entry.getKey().length()));
				param.put("kanagra", riga.substring(11, 20));
				param.put("parola", entry.getKey());
				param.put("datatimeins", sDataIns);
				param.put("dateflusso", dateFlusso);
				MyBatisConnectionFactory.getSqlSession().getMapper(StrindirMapperExt.class).insertTab(param);
			}
		}		
		
        if (riga.substring(263, 279).trim().trim().length() > 0) {
        	Strcodfisc strcodfisc = new Strcodfisc();
        	strcodfisc.setParola(riga.substring(263, 279).trim());
        	strcodfisc.setKanagra(Long.parseLong(riga.substring(11, 20)));
        	strcodfisc.setDatatimeins(sDataIns);
        	strcodfisc.setDateflusso(dateFlusso);
			try {        	
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodfiscMapper.class).insert(strcodfisc);
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
			    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());					
					e.printStackTrace();					
				} else {
					
				}
			}
		}
        
    	if (riga.substring(369, 377).trim().length() > 0) {
        	Strdatnas strdatnas = new Strdatnas();
    		strdatnas.setParola(riga.substring(369, 377));
    		strdatnas.setKanagra(Long.parseLong(riga.substring(11, 20)));
    		strdatnas.setDatatimeins(sDataIns);
    		strdatnas.setDateflusso(dateFlusso);
			try {    		
				MyBatisConnectionFactory.getSqlSession().getMapper(StrdatnasMapper.class).insert(strdatnas);
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
			    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());					
					e.printStackTrace();					
				} else {
					
				}
			}
    	}
    	
    	if (riga.substring(382, 387).trim().length() > 0) {
        	Strcodcomnas strcodcomnas = new Strcodcomnas();
    		strcodcomnas.setParola(riga.substring(382, 387));
    		strcodcomnas.setKanagra(Long.parseLong(riga.substring(11, 20)));
    		strcodcomnas.setDatatimeins(sDataIns);
    		strcodcomnas.setDateflusso(dateFlusso);
			try {    		
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodcomnasMapper.class).insert(strcodcomnas);
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
			    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());					
					e.printStackTrace();					
				} else {
					
				}
			}
    	}
    	
    	if (riga.substring(284, 289).trim().length() > 0) {
        	Strcodcomres strcodcomres = new Strcodcomres();
    		strcodcomres.setParola(riga.substring(284, 289));
    		strcodcomres.setKanagra(Long.parseLong(riga.substring(11, 20)));
    		strcodcomres.setDatatimeins(sDataIns);
    		strcodcomres.setDateflusso(dateFlusso);
			try {    		
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodcomresMapper.class).insert(strcodcomres);
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
			    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());					
					e.printStackTrace();					
				} else {
					
				}
			}
    	}
    	
    	if (riga.substring(282, 284).trim().length() > 0) {
        	Strcodprvres strcodprvres = new Strcodprvres();    		
    		strcodprvres.setParola(riga.substring(282, 284));
    		strcodprvres.setKanagra(Long.parseLong(riga.substring(11, 20)));
    		strcodprvres.setDatatimeins(sDataIns);
    		strcodprvres.setDateflusso(dateFlusso);
			try {    		
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodprvresMapper.class).insert(strcodprvres);
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
			    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());					
					e.printStackTrace();					
				} else {
					
				}
			}
			if (mapPrvReg.containsKey(riga.substring(282, 284).trim())) {
	        	Strcodiceregione strcodiceregione = new Strcodiceregione();    		
	        	strcodiceregione.setParola(mapPrvReg.get(riga.substring(282, 284).trim()));
	        	strcodiceregione.setKanagra(Long.parseLong(riga.substring(11, 20)));
	        	strcodiceregione.setDatatimeins(sDataIns);
	        	strcodiceregione.setDateflusso(dateFlusso);
				try {    		
					MyBatisConnectionFactory.getSqlSession().getMapper(StrcodiceregioneMapper.class).insert(strcodiceregione);
				} catch (Exception e) {
					if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
				    	new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object(){}.getClass().getEnclosingClass().getSimpleName());					
						e.printStackTrace();					
					} else {
						
					}
				}
			} 
    	}
	}


	public static Integer getLettiTot() {
		return lettiTot;
	}


	public static void setLettiTot(Integer lettiTot) {
		AggioramentoAnagrafiche_HandlerImpl.lettiTot = lettiTot;
	}


	public static Integer getLettiAnag() {
		return lettiAnag;
	}


	public static void setLettiAnag(Integer lettiAnag) {
		AggioramentoAnagrafiche_HandlerImpl.lettiAnag = lettiAnag;
	}


	public static Integer getDoppiAnag() {
		return doppiAnag;
	}


	public static void setDoppiAnag(Integer doppiAnag) {
		AggioramentoAnagrafiche_HandlerImpl.doppiAnag = doppiAnag;
	}


	public static Integer getScrittiAnag() {
		return scrittiAnag;
	}


	public static void setScrittiAnag(Integer scrittiAnag) {
		AggioramentoAnagrafiche_HandlerImpl.scrittiAnag = scrittiAnag;
	}

}
