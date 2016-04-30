package it.amedeo.utils;

import java.util.HashMap;
import java.util.Map;

import it.amedeo.mybatis.javaclient.StrcodcomnasMapper;
import it.amedeo.mybatis.javaclient.StrcodcomresMapper;
import it.amedeo.mybatis.javaclient.StrcodfiscMapper;
import it.amedeo.mybatis.javaclient.StrcodiceregioneMapper;
import it.amedeo.mybatis.javaclient.StrcodprvresMapper;
import it.amedeo.mybatis.javaclient.StrdatnasMapper;
import it.amedeo.mybatis.javaclient.ext.StrindirMapperExt;
import it.amedeo.mybatis.javaclient.ext.StrnomiMapperExt;
import it.amedeo.mybatis.javamodel.Strcodcomnas;
import it.amedeo.mybatis.javamodel.Strcodcomres;
import it.amedeo.mybatis.javamodel.Strcodfisc;
import it.amedeo.mybatis.javamodel.Strcodiceregione;
import it.amedeo.mybatis.javamodel.Strcodprvres;
import it.amedeo.mybatis.javamodel.Strdatnas;

public class CreaParole {

	public static synchronized void creaParole(String riga, String dateFlusso, String dataIns, Map<String, String> mapPrvReg) {
		// if (riga.startsWith("000000041AN008125109")) {
		// String a = null;
		// }
		String[] arrNome = riga.substring(20, 263).trim().split(" ");
		// elimino eventuali parole doppie (es. DI MAURO MAURO)
		Map<String, Long> mapNome = new HashMap<String, Long>();
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}

		// rimpiazzo i caratteri speciali con null (es. "F.LLI" divenda "FLLI"
		String alphaAndDigits1 = riga.substring(20, 263).trim().replaceAll("[^a-zA-Z0-9 ]+", "");
		arrNome = alphaAndDigits1.trim().split(" ");
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}

		// rimpiazzo i caratteri speciali con " " (es. "F.LLI" divenda "F LLI"
		String alphaAndDigits2 = riga.substring(20, 263).trim().replaceAll("[^a-zA-Z0-9 ]+", " ");
		arrNome = alphaAndDigits2.trim().split(" ");
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 20) {
				arrNome[i] = arrNome[i].trim().substring(0, 20);
			}
			mapNome.put(arrNome[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}
		for (Map.Entry<String, Long> entry : mapNome.entrySet()) {
			if (entry.getKey().length() > 1 && !"".equals(entry.getKey()) && !" ".equals(entry.getKey())
					&& entry.getKey() != null) {
				Map<String, String> param = new HashMap<String, String>();
				CreaNomeTabStringhe creaNomeTabStringhe = new CreaNomeTabStringhe();
				param.put("tableName", creaNomeTabStringhe.CreaNomeTabStringhe("nomi", entry.getKey().length()));
				param.put("kanagra", riga.substring(11, 20));
				param.put("parola", entry.getKey());
				param.put("datatimeins", dataIns);
				param.put("dateflusso", dateFlusso);
				MyBatisConnectionFactory.getSqlSession().getMapper(StrnomiMapperExt.class).insertTab(param);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
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

		// rimpiazzo i caratteri speciali con null (es. "F.LLI" divenda "FLLI"
		alphaAndDigits1 = riga.substring(289, 369).trim().replaceAll("[^a-zA-Z0-9 ]+", "");
		arrIndir = alphaAndDigits1.trim().split(" ");
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 20) {
				arrIndir[i] = arrIndir[i].trim().substring(0, 20);
			}
			mapIndir.put(arrIndir[i].trim(), Long.parseLong(riga.substring(11, 20)));
		}

		// rimpiazzo i caratteri speciali con " " (es. "F.LLI" divenda "F LLI"
		alphaAndDigits2 = riga.substring(289, 369).trim().replaceAll("[^a-zA-Z0-9 ]+", " ");
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
				param.put("datatimeins", dataIns);
				param.put("dateflusso", dateFlusso);
				MyBatisConnectionFactory.getSqlSession().getMapper(StrindirMapperExt.class).insertTab(param);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
			}
		}

		if (riga.substring(263, 279).trim().trim().length() > 0) {
			Strcodfisc strcodfisc = new Strcodfisc();
			strcodfisc.setParola(riga.substring(263, 279).trim());
			strcodfisc.setKanagra(Long.parseLong(riga.substring(11, 20)));
			strcodfisc.setDatatimeins(dataIns);
			strcodfisc.setDateflusso(dateFlusso);
			try {
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodfiscMapper.class).insert(strcodfisc);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
					new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object() {
					}.getClass().getEnclosingClass().getSimpleName());
					e.printStackTrace();
				} else {

				}
			}
		}

		if (riga.substring(369, 377).trim().length() > 0) {
			Strdatnas strdatnas = new Strdatnas();
			strdatnas.setParola(riga.substring(369, 377));
			strdatnas.setKanagra(Long.parseLong(riga.substring(11, 20)));
			strdatnas.setDatatimeins(dataIns);
			strdatnas.setDateflusso(dateFlusso);
			try {
				MyBatisConnectionFactory.getSqlSession().getMapper(StrdatnasMapper.class).insert(strdatnas);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
					new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object() {
					}.getClass().getEnclosingClass().getSimpleName());
					e.printStackTrace();
				} else {

				}
			}
		}

		if (riga.substring(382, 387).trim().length() > 0) {
			Strcodcomnas strcodcomnas = new Strcodcomnas();
			strcodcomnas.setParola(riga.substring(382, 387));
			strcodcomnas.setKanagra(Long.parseLong(riga.substring(11, 20)));
			strcodcomnas.setDatatimeins(dataIns);
			strcodcomnas.setDateflusso(dateFlusso);
			try {
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodcomnasMapper.class).insert(strcodcomnas);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
					new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object() {
					}.getClass().getEnclosingClass().getSimpleName());
					e.printStackTrace();
				} else {

				}
			}
		}

		if (riga.substring(284, 289).trim().length() > 0) {
			Strcodcomres strcodcomres = new Strcodcomres();
			strcodcomres.setParola(riga.substring(284, 289));
			strcodcomres.setKanagra(Long.parseLong(riga.substring(11, 20)));
			strcodcomres.setDatatimeins(dataIns);
			strcodcomres.setDateflusso(dateFlusso);
			try {
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodcomresMapper.class).insert(strcodcomres);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
					new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object() {
					}.getClass().getEnclosingClass().getSimpleName());
					e.printStackTrace();
				} else {

				}
			}
		}

		if (riga.substring(282, 284).trim().length() > 0) {
			Strcodprvres strcodprvres = new Strcodprvres();
			strcodprvres.setParola(riga.substring(282, 284));
			strcodprvres.setKanagra(Long.parseLong(riga.substring(11, 20)));
			strcodprvres.setDatatimeins(dataIns);
			strcodprvres.setDateflusso(dateFlusso);
			try {
				MyBatisConnectionFactory.getSqlSession().getMapper(StrcodprvresMapper.class).insert(strcodprvres);
//        		MyBatisConnectionFactory.commitSqlSession();
//        		MyBatisConnectionFactory.closeSqlSession();
			} catch (Exception e) {
				if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
					new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object() {
					}.getClass().getEnclosingClass().getSimpleName());
					e.printStackTrace();
				} else {

				}
			}
			if (mapPrvReg.containsKey(riga.substring(282, 284).trim())) {
				Strcodiceregione strcodiceregione = new Strcodiceregione();
				strcodiceregione.setParola(mapPrvReg.get(riga.substring(282, 284).trim()));
				strcodiceregione.setKanagra(Long.parseLong(riga.substring(11, 20)));
				strcodiceregione.setDatatimeins(dataIns);
				strcodiceregione.setDateflusso(dateFlusso);
				try {
					MyBatisConnectionFactory.getSqlSession().getMapper(StrcodiceregioneMapper.class).insert(strcodiceregione);
//            		MyBatisConnectionFactory.commitSqlSession();
//            		MyBatisConnectionFactory.closeSqlSession();
				} catch (Exception e) {
					if (FindSQLExceptionWithVendorErrorCode.SqlDuplicateKey(e).equals("error")) {
						new CreaLogElab(" ***** FINE ANOMALA : " + e.getMessage(), new Object() {
						}.getClass().getEnclosingClass().getSimpleName());
						e.printStackTrace();
					} else {

					}
				}
			}
		}

	}

}
