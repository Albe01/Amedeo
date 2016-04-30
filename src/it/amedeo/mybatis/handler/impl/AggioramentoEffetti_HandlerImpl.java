package it.amedeo.mybatis.handler.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javaclient.EffettiMapper;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.AnagraficheExample;
import it.amedeo.mybatis.javamodel.Effetti;
import it.amedeo.mybatis.javamodel.Infeffetti;
import it.amedeo.utils.CreaLogElab;
import it.amedeo.utils.MyBatisConnectionFactory;

@SuppressWarnings("rawtypes")
public class AggioramentoEffetti_HandlerImpl implements ResultHandler {
	private static DateFormat dFIns = new SimpleDateFormat("yyyyMMdd HHmmss");
	private static Date dataIns = new Date();
	private static String sDataIns = dFIns.format(dataIns);
	private static String riga = null;
	private static String tipoRecord = null;
	private static Integer lettiTot = 0;
	private static Integer lettiEff = 0;
	private static Integer scrittiEff = 0;
	private static ArrayList<String> lstEff = null;
	private static Long kAnagra = 0L;
	private static int cEffetti = 0;

	@Override
	public void handleResult(ResultContext record) {
		
		Infeffetti infeffetti = (Infeffetti) record.getResultObject();
		riga = infeffetti.getRiga();
		lettiTot++;
		
		if (!"".equals(riga) && riga != null) {
			
			if (lettiTot == 1) {
		    	new CreaLogElab(" ----- INIZIO ELABORAZIONE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
		    	new CreaLogElab(" ----- INIZIO ELABORAZIONE EFFETTI -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
		        lstEff = new ArrayList<String>();
			}
			
			tipoRecord = riga.substring(9, 11);
			if (tipoRecord.equals("TS")) {
            	new CreaLogElab(" File Effetti elaborato:" + riga, new Object(){}.getClass().getEnclosingClass().getSimpleName());
			} else if (tipoRecord.equals("EF")) {
				cEffetti++;
//				if (cEffetti > 1) {
//					String aa = null;
//				}
				if (cEffetti == 1) {
					lstEff = new ArrayList<String>();
				}
            	lstEff.add(riga);
            	lettiEff++;
			} else if (tipoRecord.equals("AC")) {
				cEffetti = 0;
				kAnagra = Long.parseLong(riga.substring(11, 20));
//				if (kAnagra.equals(12394632L) || kAnagra.equals(12394633L)) {
//					String zz = null;
//				}
				for (Iterator<String> iterator2 = lstEff.iterator(); iterator2.hasNext();) {
					String stringa = (String) iterator2.next();
		            Effetti effetti = new Effetti();
		            effetti.setDateflusso(infeffetti.getDateflusso());
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
		            MyBatisConnectionFactory.getSqlSession().getMapper(EffettiMapper.class).insert(effetti);
		            scrittiEff++;
	            	if (((scrittiEff / 10000) * 10000) == scrittiEff) {
	                	new CreaLogElab("Effetti inseriti............: " + scrittiEff, new Object(){}.getClass().getEnclosingClass().getSimpleName());		                    
	            	}
	            	
	            	AnagraficheExample anagraficheExample = new AnagraficheExample();
	            	anagraficheExample.createCriteria().andKanagraEqualTo(kAnagra);
	            	List<Anagrafiche> lstAnag = MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).selectByExample(anagraficheExample);
	            	Anagrafiche anagrafiche = lstAnag.get(0);
	            	
	            	Integer numTotEff = anagrafiche.getNumtoteff();
	            	Double impTotEff = anagrafiche.getImptoteff();
	            	String dataPrimoEff = anagrafiche.getDataprimoeff();
	            	String dataUltEff = anagrafiche.getDataulteff();
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
//				lstEff = new ArrayList<String>();
			}
		}
	}

	
	public static Integer getLettiTot() {
		return lettiTot;
	}

	public static void setLettiTot(Integer lettiTot) {
		AggioramentoEffetti_HandlerImpl.lettiTot = lettiTot;
	}

	public static Integer getLettiEff() {
		return lettiEff;
	}

	public static void setLettiEff(Integer lettiEff) {
		AggioramentoEffetti_HandlerImpl.lettiEff = lettiEff;
	}

	public static Integer getScrittiEff() {
		return scrittiEff;
	}

	public static void setScrittiEff(Integer scrittiEff) {
		AggioramentoEffetti_HandlerImpl.scrittiEff = scrittiEff;
	}

}
