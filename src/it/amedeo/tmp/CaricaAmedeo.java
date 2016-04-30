package it.amedeo.tmp;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javaclient.EffettiMapper;
import it.amedeo.mybatis.javaclient.InfanagraficheMapper;
import it.amedeo.mybatis.javaclient.InfeffettiMapper;
import it.amedeo.mybatis.javaclient.ParoleMapper;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.Effetti;
import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.javamodel.Infeffetti;
import it.amedeo.mybatis.javamodel.Parole;
import it.amedeo.utils.MyBatisConnectionFactory;

public class CaricaAmedeo {
	static SqlSession sqlSession = null;
	static Anagrafiche anagrafiche = null;
	
	public static void main(String[] args) throws IOException {
		Infanagrafiche infanagrafiche = null;
		Infeffetti infeffetti = null;
		String riga = null;
		Long kAnagra = null;
		List<Infanagrafiche> lstInfAna = null;
		List<Infeffetti> lstInfEff = null;
		SqlSessionFactory sqlSessionFactory = null;
		
//		sqlSessionFactory = (SqlSessionFactory) MyBatisConnectionFactory.myBatisConnectionFactory();
//		sqlSession = sqlSessionFactory.openSession();
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		sqlSession = MyBatisConnectionFactory.getSqlSession();

		try {
            InfanagraficheMapper infanagrafichemapper = sqlSession.getMapper(InfanagraficheMapper.class);
            
//            ParoleMapper parolemapper = sqlSession.getMapper(ParoleMapper.class);
//            ParoleExample paroleExample = new ParoleExample();
//            paroleExample.createCriteria().andCodcomresBetween("NA001", "NA100");
//            paroleExample.or(paroleExample.createCriteria().andCodcomresEqualTo("TP011"));
//            List ls = parolemapper.selectByExample(paroleExample);
////            ==>  Preparing: select tipoparola, parola, codiceregione, progressivo, kanagra, codprvres, codcomres
////            						from parole WHERE ( codcomres between ? and ? ) or ( codcomres = ? ) 
////            ==> Parameters: NA001(String), NA100(String), TP011(String)
            
            
//            ParoleMapper parolemapper = sqlSession.getMapper(ParoleMapper.class);
//            ParoleExample paroleExample = new ParoleExample();
//            Criteria criteria = paroleExample.createCriteria();
//            String xxxx = null;
//            
//            //-------- la prima parentesi aperta non ci vuole
//            xxxx = "tipoparola=" + "'a'" + " and " + "parola=" + "'ditta'" + ")";
//            criteria.Albe01(xxxx);
//            
//            xxxx = "kanagra in (SELECT kanagra from parole where tipoparola=" + "'a'" + " and " + "parola=" + "'ferrante'" + ")";
//            criteria.Albe01(xxxx);
//            
//            //-------- l'ultima parentesi chiusa non ci vuole
//            xxxx = "kanagra in (SELECT kanagra from parole where codcomres=" + "'te006'";
//            criteria.Albe01(xxxx);
//            List<Parole> listKanagra = parolemapper.selectByExample(paroleExample);
//            
//            Map<Integer, Integer> mapKanagra = new HashMap<Integer, Integer>();
//            for (Parole parole : listKanagra) {
//            	mapKanagra.put(parole.getKanagra(), parole.getKanagra());
//			  }

            
            
//          ParoleMapper parolemapper = sqlSession.getMapper(ParoleMapper.class);
//          ParoleExample paroleExample = new ParoleExample();
//          Criteria criteria = paroleExample.createCriteria();
//          criteria.Criteria01("a", "baldoni");
//          criteria.Criteria02("a", "ferrante");
//          criteria.Criteria03("codcomres", "te006");
//          paroleExample.setOrderByClause("kanagra");
          
//          ParoleMapper parolemapper = sqlSession.getMapper(ParoleMapper.class);
//          ParoleExample paroleExample = new ParoleExample();
//          Criteria criteria = paroleExample.createCriteria();
//          criteria.Criteria04(null, null, "codcomres", "te006");
//          criteria.Criteria04(null, "a", "parola", "ditta");
//          criteria.Criteria04("selectin", "a",  "parola" ,"ferrante");
//          criteria.Criteria04("selectin", "c",  "parola" ,"borgo");
//          criteria.Criteria04("selectin", "c",  "parola" ,"martini");
//          paroleExample.setOrderByClause("kanagra");
//          List<Parole> listKanagra = parolemapper.selectByExample(paroleExample);
//          Map<Integer, Integer> mapKanagra = new HashMap<Integer, Integer>();
//          for (Parole parole : listKanagra) {
//          	mapKanagra.put(parole.getKanagra(), parole.getKanagra());
//		  }

            
            lstInfAna = infanagrafichemapper.selectByExample(null);
            AnagraficheMapper anagraficheMapper = sqlSession.getMapper(AnagraficheMapper.class);
            for (Iterator<Infanagrafiche> iterator = lstInfAna.iterator(); iterator.hasNext();) {
				infanagrafiche = (Infanagrafiche) iterator.next();
				riga = infanagrafiche.getRiga();
	            anagrafiche = new Anagrafiche();
	            if (riga.substring(9, 11).equals("AN")) {
	            	anagrafiche.setKanagra(Long.parseLong(riga.substring(11, 20)));	            	
	            	anagrafiche.setNominativo(riga.substring(20, 263));
	            	anagrafiche.setCodicefiscale(riga.substring(263, 279));
	            	anagrafiche.setCodicestatoresidenza(riga.substring(279, 282));
	            	anagrafiche.setSiglaprovinciaresidenza(riga.substring(282, 284));	      
	            	anagrafiche.setCodicecomuneresidenza(riga.substring(284, 289));	  
	            	anagrafiche.setIndirizzoresidenza(riga.substring(289, 369));
	            	anagrafiche.setDatanascita(riga.substring(369, 377));
	            	anagrafiche.setCodicestatonascita(riga.substring(377, 380));	            	
	            	anagrafiche.setSiglaprovincianascita(riga.substring(380, 382));
	            	anagrafiche.setCodicecomunenascita(riga.substring(382, 387));
	            	anagrafiche.setLuogonascita(riga.substring(387, 467));
	            	
	            	try {
	            		
		            	anagraficheMapper.insert(anagrafiche);
		            	creaParole();
		            	
					} catch (Exception e) { // chiave duplicata?
						SQLException sqlException = findSQLExceptionWithVendorErrorCode(e);
						if (sqlException != null) {
							if (sqlException.getErrorCode() == 1062) {
								if (sqlException.getSQLState().equals("23000")) {
									
//					            	anagraficheMapper.updateByPrimaryKey(anagrafiche);
					            	
								}  else {
									e.printStackTrace();
									return;
								}
							}  else {
								e.printStackTrace();
								return;
							}
						} else {
							e.printStackTrace();
							return;
						}
					}
				}
			}
            sqlSession.commit();
        }
		catch (Exception e) {
			e.printStackTrace();
		}
        finally {
        	sqlSession.close();
        }
		
		
		
		
		sqlSession = sqlSessionFactory.openSession();
		try {
			ArrayList<String> lstEff;
			lstEff = new ArrayList<String>();
			
            InfeffettiMapper infeffettimapper = sqlSession.getMapper(InfeffettiMapper.class);
            
            lstInfEff = infeffettimapper.selectByExample(null);
            EffettiMapper effettiMapper = sqlSession.getMapper(EffettiMapper.class);
            for (Iterator<Infeffetti> iterator = lstInfEff.iterator(); iterator.hasNext();) {
				infeffetti = (Infeffetti) iterator.next();
				riga = infeffetti.getRiga();
	            if (riga.substring(9, 11).equals("EF")) {
	            	lstEff.add(riga);
				} else {
					if (riga.substring(9, 11).equals("AC")) {
						kAnagra = Long.parseLong(riga.substring(11, 20));
						for (Iterator<String> iterator2 = lstEff.iterator(); iterator2.hasNext();) {
							String stringa = (String) iterator2.next();
				            Effetti effetti = new Effetti();
				            effetti.setKanagra(kAnagra);
				            effetti.setCciaapubblicazione(stringa.substring(11, 13));
				            effetti.setDataiscrizione(stringa.substring(13, 21));
				            effetti.setDatalevata(stringa.substring(21, 29));
				            effetti.setSiglaprovincialevata(stringa.substring(29, 31));
				            effetti.setCodicecomunelevata(stringa.substring(31, 36));				            
				            effetti.setDatascadenza(stringa.substring(36, 44));
				            effetti.setTiposcadenza(stringa.substring(44, 45));
				            effetti.setTipoeffetto(stringa.substring(45, 46));
				            effetti.setImporto((Double.parseDouble(stringa.substring(46, 61))) / 1000);
				            effetti.setCodicevaluta(stringa.substring(61, 64));
				            effetti.setImportocorrente((Double.parseDouble(stringa.substring(64, 79))) / 1000);
				            effetti.setCodicevalutacorrente(stringa.substring(79, 82));
				            effetti.setCodmotmancpag(stringa.substring(82, 85));
				            effetti.setStatoeffetto(stringa.substring(85, 86));

				            effettiMapper.insert(effetti);							
						}
						lstEff.clear();
					}
				}
			}
            sqlSession.commit();
        }
		catch (Exception e) {
			e.printStackTrace();
		}
        finally {
        	sqlSession.close();
        }
	}


	private static SQLException findSQLExceptionWithVendorErrorCode (Throwable t) {
		  Throwable rootCause = t;
		  while (rootCause != null) {
		    if (rootCause instanceof SQLException) {
		        SQLException sqlException = (SQLException) rootCause;
		        if (sqlException.getErrorCode() != 0)  { 
		           return sqlException;
		           //return null;
		        }
		    }
		    rootCause = t.getCause();
		  }
		  return null;
	}
	
	private static void creaParole () {
		String[] arrNome = null;
		String[] arrIndir = null;
		Parole parole = null;
		
		arrNome = anagrafiche.getNominativo().split(" ");
		arrIndir = anagrafiche.getIndirizzoresidenza().split(" ");
        parole = new Parole();
        
//        parole.setCodiceregione("XX");
//        parole.setProgressivo(0);
//        parole.setKanagra(anagrafiche.getKanagra());
//        parole.setCodprvres(anagrafiche.getSiglaprovinciaresidenza());
//        parole.setCodcomres(anagrafiche.getCodicecomuneresidenza());

        ParoleMapper paroleMapper = sqlSession.getMapper(ParoleMapper.class);
		for (int i = 0; i < arrNome.length; i++) {
			if (arrNome [i].trim().length() > 1) {
	            if (arrNome [i].length() < 11) {
		            parole.setTipoparola("A");					
				} else {
		            parole.setTipoparola("B");
				}
	            parole.setParola(arrNome[i].trim());
            	paroleMapper.insert(parole);
			}
		}
		
		
        if (anagrafiche.getCodicefiscale().trim().length() > 0) {
            parole.setTipoparola("E");
            parole.setParola(anagrafiche.getCodicefiscale());
            paroleMapper.insert(parole);
		}
    	if (anagrafiche.getDatanascita().trim().length() > 0) {
            parole.setTipoparola("F");
            parole.setParola(anagrafiche.getDatanascita());
        	paroleMapper.insert(parole);
		}
    	if (anagrafiche.getCodicecomunenascita().trim().length() > 0) {
            parole.setTipoparola("G");
            parole.setParola(anagrafiche.getCodicecomunenascita());
        	paroleMapper.insert(parole);
		}
		
		for (int i = 0; i < arrIndir.length; i++) {
			if (arrIndir [i].trim().length() > 1) {
	            if (arrIndir [i].length() < 11) {
		            parole.setTipoparola("C");					
				} else {
		            parole.setTipoparola("D");
				}
	            parole.setParola(arrIndir[i].trim());
//	            parole.setCodiceregione("XX");
//	            parole.setProgressivo(0);
//	            parole.setKanagra(anagrafiche.getKanagra());
//	            parole.setCodprvres(anagrafiche.getSiglaprovinciaresidenza());
//	            parole.setCodcomres(anagrafiche.getCodicecomuneresidenza());
            	paroleMapper.insert(parole);
			}
		}
	}
	
}
