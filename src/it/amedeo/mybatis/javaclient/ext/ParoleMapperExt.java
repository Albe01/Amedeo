package it.amedeo.mybatis.javaclient.ext;

import it.amedeo.mybatis.javamodel.Parole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ParoleMapperExt {
	/**
	 * QUERY PERSONALIZZATE
	 */
	
	List<Parole> selectDynamic(HashMap<String, String> param);
	List<Parole> selectLimit(Map<String, Integer> param);
	
	
}
