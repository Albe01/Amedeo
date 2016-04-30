// Scarica SARAP E SARSYA
package it.amedeo.tmp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import it.amedeo.mybatis.javaclient.InfcomuniMapper;
import it.amedeo.mybatis.javaclient.SarapMapper;
import it.amedeo.mybatis.javaclient.ext.EffettiMapperExt;
import it.amedeo.mybatis.javamodel.Effetti;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.mybatis.javamodel.Sarap;
import it.amedeo.utils.LeftZero;
import it.amedeo.utils.MyBatisConnectionFactory;
import it.amedeo.utils.PadString;


public class ScaricaDBAs400_03 {
	private static File fileOutSarap = null;
	private static Writer recOutSarap = null;
	private static String outRigaSarap = "";
	private static File fileOutSarsya = null;
	private static Writer recOutSarsya = null;
	private static String outRigaSarsya = "";
	private static Integer cEffettiQuery = 0;
	private static Integer cEffettiLetti = 0;
	private static Long kanagraPrec = null;
	private static Integer idEff = 0;
	private static Integer indTab = 0;
	private static Integer progrRec = 0;
	private static Integer totEff = 0;
	private static DecimalFormat decimalFormat = new DecimalFormat( "000000000000.000" );
	private static Sarap sarap = null;
	private static SarapMapper sarapMapper = null;
	private static EffettiMapperExt effettiMapperExt = null;
	private static List<Effetti> lstEffetti = null;
	private static Map<String, String> mapInfCodComCodIstat = null;
	private static InfcomuniMapper infcomuniMapper = null;
	
	
	
	private static String alberto = null;
	
	public static void main(String[] args) {
		try {
			MyBatisConnectionFactory.openSqlSessionFactory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
		
		Long startQuery = new Long(0);
		Long endQuery = new Long(0);
		Map<String, Integer> paramQuery = new HashMap<String, Integer>();
		sarap = new Sarap();
		sarapMapper = sqlSession.getMapper(SarapMapper.class);
		effettiMapperExt = sqlSession.getMapper(EffettiMapperExt.class);
		infcomuniMapper = sqlSession.getMapper(InfcomuniMapper.class);
		
		fileOutSarap = new File("D:/FileInfoCam/SARAP.TXT");
		try {
			recOutSarap = new BufferedWriter(new FileWriter(fileOutSarap,false));
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
		
		int maxNbOfRows = 10000;
		int i = 0;
		while (i < 999999999) {
			paramQuery.put("offset", i);
			paramQuery.put("maxNbOfRows", maxNbOfRows);	
			startQuery = System.currentTimeMillis();			
			lstEffetti = effettiMapperExt.selectLimit(paramQuery);
			endQuery = System.currentTimeMillis();
			cEffettiQuery = cEffettiQuery + lstEffetti.size();
			String durationQuery = String.valueOf((endQuery - startQuery) / 1000) + "," + String.valueOf((endQuery - startQuery) % 1000);
			System.out.println(" Durata Query: "  + durationQuery + " effetti letti: " + cEffettiQuery);
			if (lstEffetti.size() == 0) {
				scriviSarap();
				break;
			}
			for (int j = 0; j < lstEffetti.size(); j++) {
				cEffettiLetti++;
				if (cEffettiLetti == 1) {
					kanagraPrec = lstEffetti.get(j).getKanagra();
					idEff = 0;
					progrRec = 1;
					totEff = 0;
					indTab = 0;
				}
				if (lstEffetti.get(j).getKanagra().equals(kanagraPrec)) {
					intabellaEffetti(j);
				} else {
					scriviSarap();
					
					kanagraPrec = lstEffetti.get(j).getKanagra();
					idEff = 0;
					progrRec = 1;
					totEff = 0;
					indTab = 0;
					intabellaEffetti(j);
				}
			}
			i = i + maxNbOfRows;
		}
		try {
			recOutSarap.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void scriviSarap() {
		sarap.setFlgcan(" ");
		sarap.setKanagra(LeftZero.LeftZero(Long.toString(kanagraPrec), 9, "0"));
		sarap.setPrgrec(LeftZero.LeftZero(Integer.toString(progrRec), 4, "0"));
		sarap.setLenpro(LeftZero.LeftZero(Integer.toString((indTab * 81)), 5, "0"));
		sarap.setRigapro(outRigaSarap);
//		sarapMapper.insert(sarap);
		
		outRigaSarap = " " +
				LeftZero.LeftZero(Long.toString(kanagraPrec), 9, "0") +
				LeftZero.LeftZero(Integer.toString(progrRec), 4, "0") +
				LeftZero.LeftZero(Integer.toString((indTab * 81)), 5, "0") + 
				outRigaSarap;
		try {
			recOutSarap.write(PadString.padRight(outRigaSarap, 1640) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		indTab=0;
		outRigaSarap = "";
	}
	
	private static void intabellaEffetti(Integer j) {
		if (progrRec == 1) {
			if (idEff == 0) {
				outRigaSarap = "";
			}
		}

		if (lstEffetti.get(j).getDataiscrizione().length() < 1) {
			lstEffetti.get(j).setDataiscrizione("00000000");
		}
		if (lstEffetti.get(j).getDatascadenza().length() < 1) {
			lstEffetti.get(j).setDatascadenza("00000000");
		}
		if (lstEffetti.get(j).getDatalevata().length() < 1) {
			lstEffetti.get(j).setDatalevata("00000000");
		}
		if (lstEffetti.get(j).getTiposcadenza().length() < 1) {
			lstEffetti.get(j).setTiposcadenza(" ");
		}
		String codIstatLev = "000000";
		if (mapInfCodComCodIstat.containsKey(lstEffetti.get(j).getCodicecomunelevata())) {
			codIstatLev = mapInfCodComCodIstat.get(lstEffetti.get(j).getCodicecomunelevata());
    	}

		
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
				lstEffetti.get(j).getDatatimeins().subSequence(2, 4) +
				lstEffetti.get(j).getDatatimeins().subSequence(4, 6) +
				lstEffetti.get(j).getCciaapubblicazione() +
				codIstatLev +
				lstEffetti.get(j).getDataiscrizione() +
				lstEffetti.get(j).getDatascadenza() + 
				lstEffetti.get(j).getTiposcadenza() + 
				lstEffetti.get(j).getCodmotmancpag() + 
				lstEffetti.get(j).getStatoeffetto() +
				lstEffetti.get(j).getTipoeffetto() + 
				lstEffetti.get(j).getDatalevata() + 
				PadString.padRight(lstEffetti.get(j).getCodicevaluta(), 3) + 
				String.valueOf(decimalFormat.format(lstEffetti.get(j).getImporto())).replace(",", "") +
				String.valueOf(decimalFormat.format(lstEffetti.get(j).getImportocorrente())).replace(",", "");
	alberto=null;
	}
}