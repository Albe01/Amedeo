package it.amedeo.mybatis.handler.impl;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import it.amedeo.mybatis.javamodel.Infanagrafiche;

@SuppressWarnings("rawtypes")
public class SelectInfAnagrafiche_HandlerImpl implements ResultHandler {

	@Override
	public void handleResult(ResultContext record) {
		
		Infanagrafiche infanagrafiche = (Infanagrafiche) record.getResultObject();
		System.out.println(infanagrafiche.getProgrriga() + infanagrafiche.getIdflusso() + infanagrafiche.getRiga());
		
		String albe = null;
	}

}
