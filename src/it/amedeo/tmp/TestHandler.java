package it.amedeo.tmp;

import it.amedeo.mybatis.handler.impl.AggioramentoAnagrafiche_HandlerImpl;
import it.amedeo.mybatis.handler.impl.SelectInfAnagrafiche_HandlerImpl;
import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.sqlquery.SqlInfAnagrafiche;

public class TestHandler {

	public static void main(String[] args) {
//		SelectInfAnagrafiche_HandlerImpl resultHandler = new SelectInfAnagrafiche_HandlerImpl();
//		
//		Infanagrafiche where = new Infanagrafiche();
////		where.setIdflusso("%NARPXX201305%");
//		where.setDateflusso("201305201306");
//		//where.setProgrriga(1L);
//		
//		SqlInfAnagrafiche sqlInfAnagrafiche = new SqlInfAnagrafiche();
//		sqlInfAnagrafiche.SelectInfAnagrafiche_Handler(resultHandler, where, "progrriga");
		
		
		
		
		
		AggioramentoAnagrafiche_HandlerImpl resultHandler = new AggioramentoAnagrafiche_HandlerImpl();
		
		Infanagrafiche where = new Infanagrafiche();
//		where.setIdflusso("%NARPXX201305%");
		where.setDateflusso("201305201306");
		//where.setProgrriga(1L);
		
		SqlInfAnagrafiche sqlInfAnagrafiche = new SqlInfAnagrafiche();
		sqlInfAnagrafiche.SelectInfAnagrafiche_Handler(resultHandler, where, "progrriga");

	}

}
