package it.amedeo.tmp;

import it.amedeo.mybatis.javaclient.ext.ParoleMapperExt;
import it.amedeo.mybatis.javamodel.Parole;
import it.amedeo.utils.MyBatisConnectionFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ibm.as400.access.AS400PackedDecimal;
import com.ibm.as400.access.AS400UnsignedBin1;
import com.ibm.as400.access.AS400UnsignedBin2;
import com.ibm.as400.access.AS400UnsignedBin4;
import com.ibm.as400.access.AS400UnsignedBin8;

public class Test {
	static String alberto = null;
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AS400UnsignedBin1 a1 = new AS400UnsignedBin1();
		AS400UnsignedBin2 a2 = new AS400UnsignedBin2();
		AS400UnsignedBin4 a4 = new AS400UnsignedBin4();
		AS400UnsignedBin8 a8 = new AS400UnsignedBin8();
		Long ll = 1L;
//		byte [] x1 = a1.toBytes(ll);
//		byte [] x2 = a2.toBytes(ll);
		byte [] x4 = a4.toBytes(ll);
//		byte [] x8 = a8.toBytes(ll);
		
		AS400PackedDecimal pd = new AS400PackedDecimal(3,0);
		BigDecimal bd = new BigDecimal(10);
		byte [] zz = pd.toBytes(bd);
		
		
		
		
		
		try {
			MyBatisConnectionFactory.openSqlSessionFactory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
		
		ParoleMapperExt parolemapperext = sqlSession.getMapper(ParoleMapperExt.class);
		
		String stringa = "where tipoparola='a' and parola ='sedita' and 1=1 " +
				"and progressivo in (select progressivo from parole where tipoparola='a' and parola ='sedita' and 1=1 " +
				"and progressivo in (select progressivo from parole where tipoparola='a' and parola ='giuseppe' and 1=1 " +
				"and progressivo in (select progressivo from parole where tipoparola='c' and parola ='via' and 1=1 " +
				"and progressivo in (select progressivo from parole where tipoparola='c' and parola ='calcutta'  and 1=1 " +
				"and progressivo in (select progressivo from parole where tipoparola='g' and parola ='000000')))))";
		
		HashMap<String, String> param1 = new HashMap<String, String>();
		param1.put("stringa", stringa);
		
//		List<Parole> list = parolemapperext.selectDynamic(param1);
		
		alberto = null;
		
		
		String nome = "";
		String[] arrNome = nome.trim().split(" ");
		Map<String, String> mapParole = new HashMap<String, String>();
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome[i].trim().length() > 0) {
				if (arrNome[i].trim().length() > 20) {
					arrNome[i] = arrNome[i].trim().substring(0, 20);
				}
				if (arrNome[i].trim().length() < 11) {
					mapParole.put("A" + i, arrNome[i].trim());					
				} else {
					mapParole.put("B" + i, arrNome[i].trim());
				}
			}
		}

		String indir ="via 13";
		String[] arrIndir = indir.trim().split(" ");
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir[i].trim().length() > 0) {
				if (arrIndir[i].trim().length() > 20) {
					arrIndir[i] = arrIndir[i].trim().substring(0, 20);
				}
				if (arrIndir[i].trim().length() < 11) {
					mapParole.put("C" + i, arrIndir[i].trim());					
				} else {
					mapParole.put("D" + i, arrIndir[i].trim());
				}
			}
		}

		String stringaTab = "";
		String stringaAndProgr = " where (p1.progressivo=p2.progressivo) ";
		String stringaAndParole = "";
		if (mapParole.size() > 1) {
			for (int i = 0; i < mapParole.size(); i++) {
				
				if (i < (mapParole.size() - 1)) {
					stringaTab = stringaTab + " ,parole as p" + (i + 2);
				}
				if (i < (mapParole.size() - 2)) {
					stringaAndProgr = stringaAndProgr + " and (p1.progressivo=p" + (i + 3) + ".progressivo) ";
				}
			}
			int cParole=0;
			for (Map.Entry<String, String> entry : mapParole.entrySet()) {
				cParole++;
				stringaAndParole = stringaAndParole + " and (p" + cParole + ".tipoparola = '" + entry.getKey().substring(0, 1)
						+ "' and p" + cParole + ".parola = '" + entry.getValue().trim() + "')"; 
			}
			stringa = stringaTab + stringaAndProgr + stringaAndParole;
		} else {
			for (Map.Entry<String, String> entry : mapParole.entrySet()) {
				stringa = " where p1.tipoparola='" + entry.getKey().substring(0, 1) + "' and p1.parola ='" + entry.getValue().trim() + "'";		
			}
		}
		HashMap<String, String> param2 = new HashMap<String, String>();
		param2.put("stringa", stringa);
		Long startQuery = System.currentTimeMillis();
		List<Parole> list = parolemapperext.selectDynamic(param2);
		Long endQuery = System.currentTimeMillis();
		String durationQuery = String.valueOf((endQuery - startQuery) / 1000) + "," + String.valueOf((endQuery - startQuery) % 1000);
		System.out.println("-Durata Query: "  + durationQuery + " -Soggetti trovati: " + list.size());
		
		alberto = null;
	}
}
