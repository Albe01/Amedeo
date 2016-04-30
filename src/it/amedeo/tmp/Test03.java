package it.amedeo.tmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import it.amedeo.mybatis.javaclient.ext.AnagraficheMapperExt;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.utils.MyBatisConnectionFactory;

public abstract class Test03 {

	public static void main(String[] args) throws IOException {
		
		Map<String,Object> map =  new HashMap<String,Object>() ;
        List<String> list =  new ArrayList<String>();
        
        list.add("COPEREDIL");
        map.put("strnomi10", list);
        
        list =  new ArrayList<String>();
        list.add("SNC");
        list.add("DI");
		map.put("strnomi03", list);
		
		list =  new ArrayList<String>();
        list.add("CURRELI");
		map.put("strnomi07", list);
		
		list =  new ArrayList<String>();
        list.add("F.LLI");
		map.put("strnomi05", list);
		
		map.put("distinct", "distinct");
		
		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();

		AnagraficheMapperExt anagraficheMapperExt = sqlSession.getMapper(AnagraficheMapperExt.class);
		List<Anagrafiche> lstAnag = anagraficheMapperExt.selectIn(map);
		
		
		
		
		
		
		
		

//		Map<String, Object> paramQuery = new HashMap<String, Object>();
//		paramQuery.put("nomi10_01", "COPEREDIL");
//		paramQuery.put("nomi03_01", "SNC");
//		paramQuery.put("nomi03_02", "DI");
//		paramQuery.put("nomi07_01", "CURRELI");
//		paramQuery.put("nomi05_01", "F.LLI");
//
//		paramQuery.put("distinct", "distinct");
////		paramQuery.put("groupby", "an.kanagra");
//		MyBatisConnectionFactory.openSqlSessionFactory();
//		MyBatisConnectionFactory.openSqlSession();
//		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//
//		AnagraficheMapperExt anagraficheMapperExt = sqlSession.getMapper(AnagraficheMapperExt.class);
//		List<Anagrafiche> lstAnag = anagraficheMapperExt.selectIn(paramQuery);
		String nulla = null;
	}

}
