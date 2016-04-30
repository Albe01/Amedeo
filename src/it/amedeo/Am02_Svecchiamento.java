package it.amedeo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javaclient.EffettiMapper;
import it.amedeo.mybatis.javaclient.StrcodcomnasMapper;
import it.amedeo.mybatis.javaclient.StrcodcomresMapper;
import it.amedeo.mybatis.javaclient.StrcodfiscMapper;
import it.amedeo.mybatis.javaclient.StrcodiceregioneMapper;
import it.amedeo.mybatis.javaclient.StrcodprvresMapper;
import it.amedeo.mybatis.javaclient.StrdatnasMapper;
import it.amedeo.mybatis.javaclient.Strindir03Mapper;
import it.amedeo.mybatis.javaclient.Strindir06Mapper;
import it.amedeo.mybatis.javaclient.Strindir10Mapper;
import it.amedeo.mybatis.javaclient.Strindir15Mapper;
import it.amedeo.mybatis.javaclient.Strindir20Mapper;
import it.amedeo.mybatis.javaclient.Strnomi03Mapper;
import it.amedeo.mybatis.javaclient.Strnomi04Mapper;
import it.amedeo.mybatis.javaclient.Strnomi05Mapper;
import it.amedeo.mybatis.javaclient.Strnomi06Mapper;
import it.amedeo.mybatis.javaclient.Strnomi07Mapper;
import it.amedeo.mybatis.javaclient.Strnomi08Mapper;
import it.amedeo.mybatis.javaclient.Strnomi10Mapper;
import it.amedeo.mybatis.javaclient.Strnomi15Mapper;
import it.amedeo.mybatis.javaclient.Strnomi20Mapper;
import it.amedeo.mybatis.javaclient.ext.AnagraficheMapperExt;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.AnagraficheExample;
import it.amedeo.mybatis.javamodel.Effetti;
import it.amedeo.mybatis.javamodel.EffettiExample;
import it.amedeo.mybatis.javamodel.StrcodcomnasExample;
import it.amedeo.mybatis.javamodel.StrcodcomresExample;
import it.amedeo.mybatis.javamodel.StrcodfiscExample;
import it.amedeo.mybatis.javamodel.StrcodiceregioneExample;
import it.amedeo.mybatis.javamodel.StrcodprvresExample;
import it.amedeo.mybatis.javamodel.StrdatnasExample;
import it.amedeo.mybatis.javamodel.Strindir03Example;
import it.amedeo.mybatis.javamodel.Strindir06Example;
import it.amedeo.mybatis.javamodel.Strindir10Example;
import it.amedeo.mybatis.javamodel.Strindir15Example;
import it.amedeo.mybatis.javamodel.Strindir20Example;
import it.amedeo.mybatis.javamodel.Strnomi03Example;
import it.amedeo.mybatis.javamodel.Strnomi04Example;
import it.amedeo.mybatis.javamodel.Strnomi05Example;
import it.amedeo.mybatis.javamodel.Strnomi06Example;
import it.amedeo.mybatis.javamodel.Strnomi07Example;
import it.amedeo.mybatis.javamodel.Strnomi08Example;
import it.amedeo.mybatis.javamodel.Strnomi10Example;
import it.amedeo.mybatis.javamodel.Strnomi15Example;
import it.amedeo.mybatis.javamodel.Strnomi20Example;
import it.amedeo.utils.MyBatisConnectionFactory;

public class Am02_Svecchiamento {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static DecimalFormat intFormat = new DecimalFormat( "#,###,###,###" );
	private static Integer dataSveccNum = null;
	private static String dataSveccString = null;
	private static SqlSession sqlSession = null;
	private static Anagrafiche anagrafiche = null;
	private static AnagraficheMapper anagraficheMapper = null;
	private static AnagraficheMapperExt anagraficheMapperExt = null;
	private static AnagraficheExample anagraficheExample = null;
	
	private static StrcodcomnasMapper strcodcomnasMapper = null;
	private static StrcodcomresMapper strcodcomresMapper = null;
	private static StrcodfiscMapper strcodfiscMapper = null;
	private static StrcodiceregioneMapper strcodiceregioneMapper = null;
	private static StrcodprvresMapper strcodprvresMapper = null;
	private static StrdatnasMapper strdatnasMapper = null;
	private static Strindir03Mapper strindir03Mapper = null;
	private static Strindir06Mapper strindir06Mapper = null;
	private static Strindir10Mapper strindir10Mapper = null;
	private static Strindir15Mapper strindir15Mapper = null;
	private static Strindir20Mapper strindir20Mapper = null;
	private static Strnomi03Mapper strnomi03Mapper = null;
	private static Strnomi04Mapper strnomi04Mapper = null;
	private static Strnomi05Mapper strnomi05Mapper = null;
	private static Strnomi06Mapper strnomi06Mapper = null;
	private static Strnomi07Mapper strnomi07Mapper = null;
	private static Strnomi08Mapper strnomi08Mapper = null;
	private static Strnomi10Mapper strnomi10Mapper = null;
	private static Strnomi15Mapper strnomi15Mapper = null;
	private static Strnomi20Mapper strnomi20Mapper = null;
	private static List<Anagrafiche> listAnagra = null;
	private static EffettiMapper effettiMapper = null;
	private static EffettiExample effettiExample = null;
	private static Integer cAnagQuery = 0;
	private static int i, cAnagCanc, cEffettiCanc, cAnagUpd, cAnagFlag0, cAnagFlag1 ,cAnagFlag2   = 0;
	
	public static void main(String[] args) throws IOException {
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		anagraficheMapper = sqlSession.getMapper(AnagraficheMapper.class);
		anagraficheMapperExt = sqlSession.getMapper(AnagraficheMapperExt.class);
		effettiMapper = sqlSession.getMapper(EffettiMapper.class);
		strcodcomnasMapper = sqlSession.getMapper(StrcodcomnasMapper.class);
		strcodcomresMapper = sqlSession.getMapper(StrcodcomresMapper.class);
		strcodfiscMapper = sqlSession.getMapper(StrcodfiscMapper.class);
		strcodiceregioneMapper = sqlSession.getMapper(StrcodiceregioneMapper.class);
		strcodprvresMapper = sqlSession.getMapper(StrcodprvresMapper.class);
		strdatnasMapper = sqlSession.getMapper(StrdatnasMapper.class);
		strindir03Mapper = sqlSession.getMapper(Strindir03Mapper.class);
		strindir06Mapper = sqlSession.getMapper(Strindir06Mapper.class);
		strindir10Mapper = sqlSession.getMapper(Strindir10Mapper.class);
		strindir15Mapper = sqlSession.getMapper(Strindir15Mapper.class);
		strindir20Mapper = sqlSession.getMapper(Strindir20Mapper.class);
		strnomi03Mapper = sqlSession.getMapper(Strnomi03Mapper.class);
		strnomi04Mapper = sqlSession.getMapper(Strnomi04Mapper.class);
		strnomi05Mapper = sqlSession.getMapper(Strnomi05Mapper.class);
		strnomi06Mapper = sqlSession.getMapper(Strnomi06Mapper.class);
		strnomi07Mapper = sqlSession.getMapper(Strnomi07Mapper.class);
		strnomi08Mapper = sqlSession.getMapper(Strnomi08Mapper.class);
		strnomi10Mapper = sqlSession.getMapper(Strnomi10Mapper.class);
		strnomi15Mapper = sqlSession.getMapper(Strnomi15Mapper.class);
		strnomi20Mapper = sqlSession.getMapper(Strnomi20Mapper.class);
		dataSveccNum = Integer.parseInt(dateFormat.format(new Date()).substring(0, 4) + 
				dateFormat.format(new Date()).substring(5, 7) +
				"01");
		dataSveccNum = dataSveccNum - 50000;
		dataSveccString = Integer.toString(dataSveccNum);
		
		Map<String, Integer> paramQueryAnag = new HashMap<String, Integer>();
		anagrafiche = new Anagrafiche();
		Long startQuery = new Long(0);
		Long endQuery = new Long(0);
		System.out.println(dateFormat.format(new Date()) + " -----INIZIO ELABORAZIONE----- ");
		int maxNbOfRows = 50000;
		i = 0;
		while (i < 999999999) {
			paramQueryAnag.put("offset", i);
			paramQueryAnag.put("maxNbOfRows", maxNbOfRows);	
			startQuery = System.currentTimeMillis();			
			listAnagra = anagraficheMapperExt.selectLimit(paramQueryAnag);
			if (listAnagra.size() > 0) {
				endQuery = System.currentTimeMillis();
				cAnagQuery = cAnagQuery + listAnagra.size();
				String durationQuery = String.valueOf((endQuery - startQuery) / 1000) + "," + String.valueOf((endQuery - startQuery) % 1000);
				System.out.println(dateFormat.format(new Date()) + " Durata Query: "  + durationQuery + " anagrafiche lette: " + intFormat.format(cAnagQuery));
			} else {
				break;				
			}
			for (int j = 0; j < listAnagra.size(); j++) {
				if ((listAnagra.get(j).getDataprimoeff().compareTo(dataSveccString) > -1) && 
						(listAnagra.get(j).getDataulteff().compareTo(dataSveccString) > -1)) {
					flagAnagrafica(listAnagra.get(j).getKanagra(), "2");
				}					
			
				if ((listAnagra.get(j).getDataprimoeff().compareTo(dataSveccString) < 0) && 
						(listAnagra.get(j).getDataulteff().compareTo(dataSveccString) < 0)) {
					flagAnagrafica(listAnagra.get(j).getKanagra(), "1");
					cancellaAllEffetti(listAnagra.get(j).getKanagra());
					cancellaStringhe(listAnagra.get(j).getKanagra());
				} else {
					if (listAnagra.get(j).getNumtoteff() > 1) {
						esaminaEffetti(listAnagra.get(j).getKanagra());						
					}
				}
			}
		}
		
		cancellaAnagraficheWithFlgCan1();
		resetFlgCanAnagraficheFrom2To0();
		
		System.out.println(dateFormat.format(new Date()) + " Anagrafiche lette                               : " + intFormat.format(cAnagQuery));
		System.out.println(dateFormat.format(new Date()) + " Anagrafiche flaggate a -1- da cancellare        : " + intFormat.format(cAnagFlag1));
		System.out.println(dateFormat.format(new Date()) + " Anagrafiche cancellate                          : " + intFormat.format(cAnagCanc));
		System.out.println(dateFormat.format(new Date()) + " Anagrafiche flaggate a -2- da ripristinare a -0-: " + intFormat.format(cAnagFlag2));
		System.out.println(dateFormat.format(new Date()) + " Anagrafiche ripristinate a -0-                  : " + intFormat.format(cAnagFlag0));
		System.out.println(dateFormat.format(new Date()) + " Anagrafiche aggiornate                          : " + intFormat.format(cAnagUpd));
		System.out.println(dateFormat.format(new Date()) + " Effetti     cancellati                          : " + intFormat.format(cEffettiCanc));
		System.out.println(dateFormat.format(new Date()) + " -----FINE ELABORAZIONE----- ");
	}


	private static void resetFlgCanAnagraficheFrom2To0() {
		anagrafiche = new Anagrafiche();
		anagrafiche.setFlgcan("0");
		anagraficheExample = new AnagraficheExample();
		anagraficheExample.createCriteria().andFlgcanEqualTo("2");
		cAnagFlag0 = anagraficheMapper.updateByExampleSelective(anagrafiche, anagraficheExample);
	}

	private static void flagAnagrafica(Long kanagra, String flgCan) {
		anagrafiche = new Anagrafiche();
		anagrafiche.setFlgcan(flgCan);
		anagraficheExample = new AnagraficheExample();
		anagraficheExample.createCriteria().andKanagraEqualTo(kanagra);
		anagraficheMapper.updateByExampleSelective(anagrafiche, anagraficheExample);
		if (flgCan.equals("1")) {
			cAnagFlag1++;			
		}
		if (flgCan.equals("2")) {
			cAnagFlag2++;			
		}
	}
	

	private static void cancellaAnagraficheWithFlgCan1() {
		anagraficheExample = new AnagraficheExample();
		anagraficheExample.createCriteria().andFlgcanEqualTo("1");
		cAnagCanc = cAnagCanc + anagraficheMapper.deleteByExample(anagraficheExample);
	}

	
	private static void cancellaAllEffetti(Long kanagra) {
		effettiExample = new EffettiExample();
		effettiExample.createCriteria().andKanagraEqualTo(kanagra);
		cEffettiCanc = cEffettiCanc + effettiMapper.deleteByExample(effettiExample);
	}

	
	private static void esaminaEffetti(Long kanagra) {
		int cEffCanc = 0;
		int numTotEff = 0;
		Double impTotEff = 0.000;
		String dataPrimoEff = "99999999";
		String dataUltEff = "00000000";
		List<Effetti> listEffetti = new ArrayList<Effetti>();
		effettiExample = new EffettiExample();
		effettiExample.createCriteria().andKanagraEqualTo(kanagra);
		effettiExample.setOrderByClause("dataiscrizione");
		listEffetti = effettiMapper.selectByExample(effettiExample);
		for (int k = 0; k < listEffetti.size(); k++) {
			if (listEffetti.get(k).getDataiscrizione().compareTo(dataSveccString) > -1) {
				numTotEff++;
				if (listEffetti.get(k).getCodicevaluta().equals("EU")) {
					impTotEff = impTotEff + listEffetti.get(k).getImporto();					
				} else {
					impTotEff = impTotEff + listEffetti.get(k).getImportocorrente();
				}
				if (dataPrimoEff.equals("99999999")) {
					dataPrimoEff = listEffetti.get(k).getDataiscrizione();					
				}
				if (listEffetti.get(k).getDataiscrizione().compareTo(dataUltEff) > 0) {
					dataUltEff = listEffetti.get(k).getDataiscrizione();					
				}
			} else {
				cancellaSingoloEffetto(listEffetti.get(k).getProgrriga());
				cEffCanc++;
			}
		}
		if (cEffCanc > 0) {
			if (cEffCanc == listEffetti.size()) {
				flagAnagrafica(listEffetti.get(0).getKanagra(), "1");
				cancellaStringhe(listEffetti.get(0).getKanagra());
			} else {
				aggiornaAnagrafica(listEffetti.get(0).getKanagra(), dataPrimoEff, dataUltEff, impTotEff, numTotEff);
				flagAnagrafica(listEffetti.get(0).getKanagra(), "2");
			}
		}
	}

	private static void cancellaSingoloEffetto(Integer progrRiga) {
		effettiExample = new EffettiExample();
		effettiExample.createCriteria().andProgrrigaEqualTo(progrRiga);
		effettiMapper.deleteByExample(effettiExample);
		cEffettiCanc++;
	}

	private static void aggiornaAnagrafica(Long kanagra, String dataPrimoEff, String dataUltEff, Double impTotEff, Integer numTotEff) {
		anagrafiche = new Anagrafiche();
		anagrafiche.setDataprimoeff(dataPrimoEff);
		anagrafiche.setDataulteff(dataUltEff);
		anagrafiche.setImptoteff(impTotEff);
		anagrafiche.setNumtoteff(numTotEff);
		anagraficheExample = new AnagraficheExample();
		anagraficheExample.createCriteria().andKanagraEqualTo(kanagra);
		anagraficheMapper.updateByExampleSelective(anagrafiche, anagraficheExample);
		cAnagUpd++;
	}

	private static void cancellaStringhe(Long kanagra) {
		StrcodcomnasExample strcodcomnasExample = new StrcodcomnasExample();
		strcodcomnasExample.createCriteria().andKanagraEqualTo(kanagra);
		strcodcomnasMapper.deleteByExample(strcodcomnasExample);

		StrcodcomresExample strcodcomresExample = new StrcodcomresExample();
		strcodcomresExample.createCriteria().andKanagraEqualTo(kanagra);
		strcodcomresMapper.deleteByExample(strcodcomresExample);
	
		StrcodfiscExample strcodfiscExample = new StrcodfiscExample();
		strcodfiscExample.createCriteria().andKanagraEqualTo(kanagra);
		strcodfiscMapper.deleteByExample(strcodfiscExample);
	
		StrcodiceregioneExample strcodiceregioneExample = new StrcodiceregioneExample();
		strcodiceregioneExample.createCriteria().andKanagraEqualTo(kanagra);
		strcodiceregioneMapper.deleteByExample(strcodiceregioneExample);
		
		StrcodprvresExample strcodprvresExample = new StrcodprvresExample();
		strcodprvresExample.createCriteria().andKanagraEqualTo(kanagra);
		strcodprvresMapper.deleteByExample(strcodprvresExample);
		
		StrdatnasExample strdatnasExample = new StrdatnasExample();
		strdatnasExample.createCriteria().andKanagraEqualTo(kanagra);
		strdatnasMapper.deleteByExample(strdatnasExample);
		
		Strindir03Example strindir03Example = new Strindir03Example();
		strindir03Example.createCriteria().andKanagraEqualTo(kanagra);
		strindir03Mapper.deleteByExample(strindir03Example);
		
		Strindir06Example strindir06Example = new Strindir06Example();
		strindir06Example.createCriteria().andKanagraEqualTo(kanagra);
		strindir06Mapper.deleteByExample(strindir06Example);
		
		Strindir10Example strindir10Example = new Strindir10Example();
		strindir10Example.createCriteria().andKanagraEqualTo(kanagra);
		strindir10Mapper.deleteByExample(strindir10Example);
		
		Strindir15Example strindir15Example = new Strindir15Example();
		strindir15Example.createCriteria().andKanagraEqualTo(kanagra);
		strindir15Mapper.deleteByExample(strindir15Example);
		
		Strindir20Example strindir20Example = new Strindir20Example();
		strindir20Example.createCriteria().andKanagraEqualTo(kanagra);
		strindir20Mapper.deleteByExample(strindir20Example);
		
		Strnomi03Example strnomi03Example = new Strnomi03Example();
		strnomi03Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi03Mapper.deleteByExample(strnomi03Example);
		
		Strnomi04Example strnomi04Example = new Strnomi04Example();
		strnomi04Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi04Mapper.deleteByExample(strnomi04Example);
		
		Strnomi05Example strnomi05Example = new Strnomi05Example();
		strnomi05Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi05Mapper.deleteByExample(strnomi05Example);
		
		Strnomi06Example strnomi06Example = new Strnomi06Example();
		strnomi06Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi06Mapper.deleteByExample(strnomi06Example);
		
		Strnomi07Example strnomi07Example = new Strnomi07Example();
		strnomi07Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi07Mapper.deleteByExample(strnomi07Example);
		
		Strnomi08Example strnomi08Example = new Strnomi08Example();
		strnomi08Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi08Mapper.deleteByExample(strnomi08Example);
		
		Strnomi10Example strnomi10Example = new Strnomi10Example();
		strnomi10Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi10Mapper.deleteByExample(strnomi10Example);
		
		Strnomi15Example strnomi15Example = new Strnomi15Example();
		strnomi15Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi15Mapper.deleteByExample(strnomi15Example);
		
		Strnomi20Example strnomi20Example = new Strnomi20Example();
		strnomi20Example.createCriteria().andKanagraEqualTo(kanagra);
		strnomi20Mapper.deleteByExample(strnomi20Example);
	}

}
