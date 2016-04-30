package it.amedeo.tmp;

import it.amedeo.mybatis.javaclient.InfcomuniMapper;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.utils.MyBatisConnectionFactory;
import it.amedeo.utils.PadString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class CreaSartabs {

	public static void main(String[] args) throws IOException {
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
		
		
		InfcomuniMapper infcomuniMapper = sqlSession.getMapper(InfcomuniMapper.class);
		InfcomuniExample infcomuniExample = new InfcomuniExample();
		infcomuniExample.createCriteria().andCodicecomuneEqualTo("999");
		infcomuniExample.setOrderByClause("siglaprovincia");
		List<Infcomuni> lstInfComuni =  infcomuniMapper.selectByExample(infcomuniExample);
		
		File fileOutSartabs = new File("D:/FileInfoCam/perAS400/SARTABSTXT.TXT");
		Writer recOutSartabs = null;
		try {
			recOutSartabs = new BufferedWriter(new FileWriter(fileOutSartabs, false));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String regione = null;
		String outRigaSartabs = null;
		for (int i = 0; i < lstInfComuni.size(); i++) {
			if (lstInfComuni.get(i).getCodregione().equals("01")) {
				regione="PIEMONTE";
			}
			if (lstInfComuni.get(i).getCodregione().equals("02")) {
				regione="VALLE D'AOSTA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("03")) {
				regione="LOMBARDIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("04")) {
				regione="TRENTINO";
			}
			if (lstInfComuni.get(i).getCodregione().equals("05")) {
				regione="VENETO";
			}
			if (lstInfComuni.get(i).getCodregione().equals("06")) {
				regione="FRIULI";
			}
			if (lstInfComuni.get(i).getCodregione().equals("07")) {
				regione="LIGURIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("08")) {
				regione="EMILIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("09")) {
				regione="TOSCANA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("10")) {
				regione="UMBRIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("11")) {
				regione="MARCHE";
			}
			if (lstInfComuni.get(i).getCodregione().equals("12")) {
				regione="LAZIO";
			}
			if (lstInfComuni.get(i).getCodregione().equals("13")) {
				regione="ABRUZZI";
			}
			if (lstInfComuni.get(i).getCodregione().equals("14")) {
				regione="MOLISE";
			}
			if (lstInfComuni.get(i).getCodregione().equals("15")) {
				regione="CAMPANIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("16")) {
				regione="PUGLIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("17")) {
				regione="BASILICATA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("18")) {
				regione="CALABRIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("19")) {
				regione="SICILIA";
			}
			if (lstInfComuni.get(i).getCodregione().equals("20")) {
				regione="SARDEGNA";
			}
			outRigaSartabs = PadString.padRight(" ", 26)
					+ lstInfComuni.get(i).getSiglaprovincia()
					+ "PR"
					+ lstInfComuni.get(i).getCodistat().substring(0, 3)
					+ PadString.padRight(" ", 33)
					+ "PR"
					+ lstInfComuni.get(i).getSiglaprovincia()
					+ PadString.padRight(" ", 4)
					+ regione;
			
			recOutSartabs.write(outRigaSartabs + "\r\n");
		}
		
		infcomuniExample = new InfcomuniExample();
		infcomuniExample.createCriteria().andCodregioneNotEqualTo("00").andCodicecomuneNotEqualTo("999");
		infcomuniExample.setOrderByClause("codistat");
		lstInfComuni =  infcomuniMapper.selectByExample(infcomuniExample);
		for (int i = 0; i < lstInfComuni.size(); i++) {
			InfcomuniExample infcomuniExamplePrv = new InfcomuniExample();
			infcomuniExamplePrv.createCriteria().andCodicecomuneEqualTo("999").andCodiceprovinciaEqualTo(lstInfComuni.get(i).getCodiceprovincia());
			List<Infcomuni> lstInfComuniPrv =  infcomuniMapper.selectByExample(infcomuniExamplePrv);
			if (lstInfComuniPrv.size() == 0) {
				String albe=null;
			}
			
			
			
			outRigaSartabs = PadString.padRight(" ", 22)
					+ lstInfComuni.get(i).getCodistat()
					+ "IS"
					+ PadString.padRight(lstInfComuni.get(i).getDenomcomune(), 34)
					+ lstInfComuni.get(i).getSiglaprovincia()
					+ "IS"
					+ lstInfComuni.get(i).getCodistat()
					+ lstInfComuni.get(i).getSiglaprovincia()
					+ lstInfComuniPrv.get(0).getDenomcomune();
			
			recOutSartabs.write(outRigaSartabs + "\r\n");
		}
		
		
		recOutSartabs.close();
	}

}
