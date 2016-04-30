// Legge Dump DBparole e Scarica su DB: SARIN E SARPR
//        		        in formato TXT: SARINTXT, SARPRTXT
package it.amedeo;

import it.amedeo.mybatis.javaclient.SarinMapper;
import it.amedeo.mybatis.javaclient.SarprMapper;
import it.amedeo.mybatis.javamodel.Sarin;
import it.amedeo.mybatis.javamodel.Sarpr;
import it.amedeo.utils.LeftZero;
import it.amedeo.utils.MyBatisConnectionFactory;
import it.amedeo.utils.PadString;

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

import org.apache.ibatis.session.SqlSession;


public class Sc03_SARIN_SARPR {
	private static File fileOutSarin = null;
	private static Writer recOutSarin = null;
	private static String outRigaSarin = "";
	private static File fileOutSarpr = null;
	private static Writer recOutSarpr = null;
	private static String outRigaSarpr = "";
	private static DecimalFormat intFormat = new DecimalFormat( "#,###,###,###" );
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
	private static Sarin sarin = null;
	private static Sarpr sarpr = null;
	private static SarinMapper sarinMapper = null;
	private static SarprMapper sarprMapper = null;
	private static String tipoParolaPrec, parolaPrec, regionePrec;
	private static Integer iSarpr = 0;
	private static Integer cParola = 0;
	private static Integer cParolaNaz = 0;
	private static Integer cParTot = 0;
	private static Integer puntaPrSarin = 0;
	private static Integer puntaPrNaz = 0;
	private static Integer cParoleLette = 0;
	private static String riga = null;
	private static String[] arrParole = null;
	private static BufferedReader input = null;
	private static String alberto = null;
	
	public static void main(String[] args) {
		try {
			MyBatisConnectionFactory.openSqlSessionFactory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
		sarin = new Sarin();
		sarpr = new Sarpr();
		sarinMapper = sqlSession.getMapper(SarinMapper.class);
		sarprMapper = sqlSession.getMapper(SarprMapper.class);
		fileOutSarpr = new File("D:/FileInfoCam/perAS400/SARPRTXT.TXT");
		fileOutSarin = new File("D:/FileInfoCam/perAS400/SARINTXT.TXT");
		try {
			recOutSarin = new BufferedWriter(new FileWriter(fileOutSarin,false));
			recOutSarpr = new BufferedWriter(new FileWriter(fileOutSarpr,false));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		iSarpr = 0;
		cParola = 0;
		cParTot = 0;
		System.out.println(dateFormat.format(new Date()) + " -----INIZIO ELABORAZIONE----- ");		
		File fileInput = new File("D:/FileInfoCam/DBparole.txt");
		if (fileInput.isFile()) {
			try {
				input = new BufferedReader(new FileReader(fileInput));	
				while ((riga = input.readLine()) != null)  {
					arrParole = riga.toString().trim().split("\\|");					
					cParoleLette++;
					if (cParoleLette == 1) {
						sarinMapper.deleteByExample(null);
						sarprMapper.deleteByExample(null);
					}
	            	if (((cParoleLette / 100000) * 100000) == cParoleLette) {
	        			System.out.println(dateFormat.format(new Date()) + " parole lette: " + intFormat.format(cParoleLette));		                    
	            	}
					if (cParoleLette == 1) {
						tipoParolaPrec = arrParole[0];
						parolaPrec = arrParole[1];
						regionePrec = arrParole[2];
						puntaPrSarin = 1;
						puntaPrNaz = 1;
					}
					if (arrParole[0].equals(tipoParolaPrec) && arrParole[1].equals(parolaPrec) && arrParole[2].equals(regionePrec)) {
						intabellaPunta(arrParole[3]);
					} else {
						scriviSarin();
						if (!arrParole[1].equals(parolaPrec)) {
							scriviSarinNaz();
						}
						puntaPrSarin = cParTot + 1;
						tipoParolaPrec = arrParole[0];
						parolaPrec = arrParole[1];
						regionePrec = arrParole[2];
						cParola = 0;
						intabellaPunta(arrParole[3]);
					}
				}
				scriviSarin();
				scriviSarinNaz();
				scriviSarpr();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			try {
				recOutSarin.close();
				recOutSarpr.close();
    			System.out.println(dateFormat.format(new Date()) + " parole lette: " + intFormat.format(cParoleLette));
				System.out.println(dateFormat.format(new Date()) + " -----FINE ELABORAZIONE----- ");				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void scriviSarinNaz() {
		sarin.setTipoparola(tipoParolaPrec);
		sarin.setParola(parolaPrec);
		sarin.setRegione("99");
		sarin.setRicorrenze(cParolaNaz);
		sarin.setPuntapr(puntaPrNaz);
		sarinMapper.insert(sarin);
		outRigaSarin = tipoParolaPrec + 
				PadString.padRight(parolaPrec, 20) + 
				"99" + 
				LeftZero.LeftZero(Integer.toString(cParolaNaz), 9, "0") + 
				LeftZero.LeftZero(Integer.toString(puntaPrNaz), 9, "0");
		try {
			recOutSarin.write(PadString.padRight(outRigaSarin, 41) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		puntaPrNaz = cParTot + 1;
		cParolaNaz= 0;
	}
	
	private static void scriviSarin() {
		sarin.setTipoparola(tipoParolaPrec);
		sarin.setParola(parolaPrec);
		sarin.setRegione(regionePrec);
		sarin.setRicorrenze(cParola);
		sarin.setPuntapr(puntaPrSarin);
		sarinMapper.insert(sarin);
		outRigaSarin = tipoParolaPrec + 
				PadString.padRight(parolaPrec, 20) + 
				regionePrec + 
				LeftZero.LeftZero(Integer.toString(cParola), 9, "0") + 
				LeftZero.LeftZero(Integer.toString(puntaPrSarin), 9, "0");
		try {
			recOutSarin.write(PadString.padRight(outRigaSarin, 41) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void scriviSarpr() {
		sarpr.setProgressivi(outRigaSarpr);
		sarprMapper.insert(sarpr);
		try {
			recOutSarpr.write(PadString.padRight(outRigaSarpr, 9500) + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		iSarpr = 1;
		outRigaSarpr = "";
	}
	
	private static void intabellaPunta(String progressivo) {
		iSarpr++;
		if (iSarpr > 1015) {
			scriviSarpr();
		}
		outRigaSarpr = outRigaSarpr + progressivo;
		cParola++;
		cParolaNaz++;
		cParTot++;
	}
}