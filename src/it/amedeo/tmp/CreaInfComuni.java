package it.amedeo.tmp;

import it.amedeo.mybatis.javaclient.InfcomuninewMapper;
import it.amedeo.mybatis.javaclient.RegprvMapper;
import it.amedeo.mybatis.javaclient.SaranMapper;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.mybatis.javamodel.Infcomuninew;
import it.amedeo.mybatis.javamodel.Regprv;
import it.amedeo.mybatis.javamodel.RegprvExample;
import it.amedeo.utils.MyBatisConnectionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class CreaInfComuni {

	public static void main(String[] args) throws IOException {
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();

		InfcomuninewMapper infcomuninewMapper = sqlSession.getMapper(InfcomuninewMapper.class);
		RegprvMapper regprvMapper = sqlSession.getMapper(RegprvMapper.class);
		String riga = null;
		File fileInput = new File("D:/FileInfoCam/s2comuni.txt");
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileInput));
			while ((riga = input.readLine()) != null) {
				RegprvExample regprvExample = new RegprvExample();
				regprvExample.createCriteria().andSiglaprovinciaEqualTo(riga.substring(0, 2));
				List<Regprv> lstRegPrv =  regprvMapper.selectByExample(regprvExample);
				String codreg="XX";
				String codprv="XXX";
				String codistat="XXXXXX";
				String denreg="XXXXXXXXXXS";

				if (lstRegPrv.size() == 0) {
					if (riga.substring(0, 2).equals("EE")) {
						codreg="00";
						codprv="000";
						codistat=codprv + riga.substring(2, 5);
						denreg=" ";
					}
				}
				
				if (lstRegPrv.size() == 0) {
					if (riga.substring(0, 2).equals("FO")) {
							codreg="08";
							codprv="040";
							codistat=codprv + riga.substring(2, 5);
							denreg="EMILIA-ROMAGNA";
					}						
				}

				if (lstRegPrv.size() == 0) {
					if (riga.substring(0, 2).equals("PS")) {
							codreg="11";
							codprv="041";
							codistat=codprv + riga.substring(2, 5);
							denreg="MARCHE";
					}						
				}

				if (lstRegPrv.size() > 0) {						
					codreg=Integer.toString(lstRegPrv.get(0).getCodiceregione());
					if (codreg.length() < 2 ) {
						codreg="0" + codreg;
					}
					codprv=Integer.toString(lstRegPrv.get(0).getCodprovincia());
					if (codprv.length() == 1 ) {
						codprv="00" + codprv;
					}
					if (codprv.length() == 2 ) {
						codprv="0" + codprv;
					}
					codistat=codprv + riga.substring(2, 5);
					denreg=lstRegPrv.get(0).getDenomregionemaiusc();
				} 
				
				
				if (codreg.equals("XX")) {
					String albe=null;
				}
				
				Infcomuninew infcomuninew = new Infcomuninew();
				infcomuninew.setCodregione(codreg);
				infcomuninew.setCodiceprovincia(codprv);
				infcomuninew.setCodicecomune(riga.substring(2, 5));
				infcomuninew.setCodistat(codistat);
				infcomuninew.setSiglaprovincia(riga.substring(0, 2));
				infcomuninew.setInfcodicecomune(riga.substring(0, 5));
				infcomuninew.setDenomcomune(riga.substring(6));
				infcomuninew.setDenomregione(denreg);
				infcomuninewMapper.insert(infcomuninew);
				
				String albe = null;
				
			}
			
			RegprvExample regprvExample = new RegprvExample();
			List<Regprv> lstRegPrv =  regprvMapper.selectByExample(null);
			for (int i = 0; i < lstRegPrv.size(); i++) {
				String codreg="XX";
				String codprv="XXX";
				String codistat="XXXXXX";
				String denreg="XXXXXXXXXXS";

				codreg=Integer.toString(lstRegPrv.get(i).getCodiceregione());
				if (codreg.length() < 2 ) {
					codreg="0" + codreg;
				}
				codprv=Integer.toString(lstRegPrv.get(i).getCodprovincia());
				if (codprv.length() == 1 ) {
					codprv="00" + codprv;
				}
				if (codprv.length() == 2 ) {
					codprv="0" + codprv;
				}
				codistat=codprv + "999";
				denreg=lstRegPrv.get(i).getDenomregionemaiusc();
				
				
				if (lstRegPrv.get(i).getSiglaprovincia().equals("FC")) {
					lstRegPrv.get(i).setSiglaprovincia("FO");
					lstRegPrv.get(i).setDenomprovincia("FORLI'");
					codreg="08";
					codprv="040";
					codistat=codprv + "999";
					denreg="EMILIA-ROMAGNA";
				}						

				if (lstRegPrv.get(i).getSiglaprovincia().equals("PU")) {
					lstRegPrv.get(i).setSiglaprovincia("PS");	
					lstRegPrv.get(i).setDenomprovincia("PESARO");
					codreg="11";
					codprv="041";
					codistat=codprv + "999";
					denreg="MARCHE";
				}						
				
				
				Infcomuninew infcomuninew = new Infcomuninew();
				infcomuninew.setCodregione(codreg);
				infcomuninew.setCodiceprovincia(codprv);
				infcomuninew.setCodicecomune("999");
				infcomuninew.setCodistat(codistat);
				infcomuninew.setSiglaprovincia(lstRegPrv.get(i).getSiglaprovincia());
				infcomuninew.setInfcodicecomune(lstRegPrv.get(i).getSiglaprovincia() + "999");
				infcomuninew.setDenomcomune(lstRegPrv.get(i).getDenomprovincia().toUpperCase());
				infcomuninew.setDenomregione(denreg);
				infcomuninewMapper.insert(infcomuninew);
				
			}
		} catch (IOException ioException) {
		}
	}

}
//if (riga.substring(0, 2).equals("EE")) {
//	infcomuninew.setCodregione("00");
//	infcomuninew.setCodiceprovincia("000");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//
//
//if (riga.substring(0, 2).equals("AG")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("084");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("CL")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("085");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("CT")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("087");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("EN")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("086");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("ME")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("083");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("PA")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("082");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("RG")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("088");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("SR")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("089");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//if (riga.substring(0, 2).equals("TP")) {
//	infcomuninew.setCodregione("19");
//	infcomuninew.setCodiceprovincia("081");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
//
//
//
//
//
//
//
//
//if (riga.substring(0, 2).equals("CZ")) {
//	infcomuninew.setCodregione("18");
//	infcomuninew.setCodiceprovincia("079");
//	infcomuninew.setCodistat(infcomuninew.getCodiceprovincia() + riga.substring(2, 5));
//}
