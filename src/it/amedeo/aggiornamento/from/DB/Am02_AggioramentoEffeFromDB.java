package it.amedeo.aggiornamento.from.DB;

import it.amedeo.mybatis.handler.impl.AggioramentoEffetti_HandlerImpl;
import it.amedeo.mybatis.javamodel.Infeffetti;
import it.amedeo.mybatis.sqlquery.SqlInfEffetti;
import it.amedeo.utils.CreaLogElab;

public class Am02_AggioramentoEffeFromDB {
	
	public static void main(String[] args) {
		
		Am02_AggioramentoEffeFromDB am01_00_AggioramentoEffe_Main = new Am02_AggioramentoEffeFromDB();
		am01_00_AggioramentoEffe_Main.elaborate();
	}

	private void elaborate() {
		AggioramentoEffetti_HandlerImpl resultHandler = new AggioramentoEffetti_HandlerImpl();
		
		Infeffetti where = new Infeffetti();
		where.setDateflusso("201305201306");
//		where.setProgrriga(1L);
		
		SqlInfEffetti sqlInfEffetti = new SqlInfEffetti();
		sqlInfEffetti.SelectInfEffetti_Handler(resultHandler, where, "progrriga");
		
    	new CreaLogElab(" ----- FINE ELABORAZIONE EFFETTI -----", new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Effetti letti...............: " + AggioramentoEffetti_HandlerImpl.getLettiEff() , new Object(){}.getClass().getEnclosingClass().getSimpleName());
    	new CreaLogElab("Effetti inseriti............: " + AggioramentoEffetti_HandlerImpl.getScrittiEff(), new Object(){}.getClass().getEnclosingClass().getSimpleName());

		
	}

}
