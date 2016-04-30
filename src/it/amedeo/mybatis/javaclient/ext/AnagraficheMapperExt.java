package it.amedeo.mybatis.javaclient.ext;

import it.amedeo.mybatis.javamodel.Anagrafiche;

import java.util.List;
import java.util.Map;

public interface AnagraficheMapperExt {

	/**
	 * QUERY PERSONALIZZATE
	 */
	void selectPippo();
	
	List<Anagrafiche> selectLimit(Map<String, Integer> map);
	List<Anagrafiche> selectKanagra(Map<String, String> map);
	Anagrafiche selectAnagra(Map<String, Long> map);
	List<Anagrafiche> selectIn(Map<String, Object> map);
}