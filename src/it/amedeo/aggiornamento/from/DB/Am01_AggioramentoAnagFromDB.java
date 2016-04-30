package it.amedeo.aggiornamento.from.DB;

import it.amedeo.mybatis.handler.impl.AggioramentoAnagrafiche_HandlerImpl;
import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.sqlquery.SqlInfAnagrafiche;
import it.amedeo.utils.CreaLogElab;

public class Am01_AggioramentoAnagFromDB {
	
	public static void main(String[] args) {
		
		Am01_AggioramentoAnagFromDB am01_00_AggioramentoAnag_Main = new Am01_AggioramentoAnagFromDB();
		am01_00_AggioramentoAnag_Main.elaborate();
	}

	private void elaborate() {
		AggioramentoAnagrafiche_HandlerImpl resultHandler = new AggioramentoAnagrafiche_HandlerImpl();
		
		Infanagrafiche where = new Infanagrafiche();
		where.setDateflusso("201307201308");
//		where.setProgrriga(1L);
		
		SqlInfAnagrafiche sqlInfAnagrafiche = new SqlInfAnagrafiche();
		sqlInfAnagrafiche.SelectInfAnagrafiche_Handler(resultHandler, where, "progrriga");
		
    	new CreaLogElab(" ----- FINE ELABORAZIONE ANAGRAFICHE -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Anagrafiche lette.....................: " + AggioramentoAnagrafiche_HandlerImpl.getLettiAnag(), new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Anagrafiche gia' censite scartate.....: " + AggioramentoAnagrafiche_HandlerImpl.getDoppiAnag(), new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Anagrafiche nuove inserite............: " + AggioramentoAnagrafiche_HandlerImpl.getScrittiAnag(), new Object(){}.getClass().getEnclosingClass().getSimpleName());

		
	}

}
