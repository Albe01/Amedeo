package it.amedeo.mybatis.javaclient.ext;

import it.amedeo.mybatis.javamodel.Effetti;

import java.util.List;
import java.util.Map;


public interface EffettiMapperExt {
	/**
	 * QUERY PERSONALIZZATE
	 */
	
	List<Effetti> selectLimit(Map<String, Integer> param);
	
	
}
