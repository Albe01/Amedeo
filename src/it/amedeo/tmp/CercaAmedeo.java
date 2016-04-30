package it.amedeo.tmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import it.amedeo.mybatis.javaclient.StrcodcomresMapper;
import it.amedeo.mybatis.javaclient.Strindir10Mapper;
import it.amedeo.mybatis.javaclient.Strnomi10Mapper;
import it.amedeo.mybatis.javamodel.Strcodcomres;
import it.amedeo.mybatis.javamodel.StrcodcomresExample;
import it.amedeo.mybatis.javamodel.Strindir10;
import it.amedeo.mybatis.javamodel.Strindir10Example;
import it.amedeo.mybatis.javamodel.Strnomi10;
import it.amedeo.mybatis.javamodel.Strnomi10Example;
import it.amedeo.utils.MyBatisConnectionFactory;

public class CercaAmedeo {

	public static void main(String[] args) throws IOException {
		SqlSessionFactory sqlSessionFactory = null;		
		SqlSession sqlSession = null;
		
		List<Strnomi10> listNomi10 = null;
		List<Strcodcomres> listCodComRes = null;
		List<Strindir10> listIndir10 = null;
		
		ArrayList<Long> listKanagra1 = null;
		ArrayList<Long> listKanagra2 = null;	
		
		
		Strnomi10Example strnomi10Example = null;
		Strindir10Example strindir10Example = null;
		StrcodcomresExample strcodcomresExample = null;
		
//		sqlSessionFactory = (SqlSessionFactory) MyBatisConnectionFactory.myBatisConnectionFactory();
//		sqlSession = sqlSessionFactory.openSession();

		MyBatisConnectionFactory.openSqlSessionFactory();
		MyBatisConnectionFactory.openSqlSession();
		sqlSession = MyBatisConnectionFactory.getSqlSession();

		
		
		Strnomi10Mapper strnomi10Mapper = sqlSession.getMapper(Strnomi10Mapper.class);
		strnomi10Example = new Strnomi10Example();
		strnomi10Example.createCriteria().andParolaEqualTo("ditta");
		strnomi10Example.or(strnomi10Example.createCriteria().andParolaEqualTo("ferrante"));
		listNomi10 = strnomi10Mapper.selectByExample(strnomi10Example);
		listKanagra1 = new ArrayList<Long>();
		for (int i = 0; i < listNomi10.size(); i++) {
			listKanagra1.add(listNomi10.get(i).getKanagra());
		}
		
		
		StrcodcomresMapper strcodcomresMapper = sqlSession.getMapper(StrcodcomresMapper.class);
		strcodcomresExample = new StrcodcomresExample();
		strcodcomresExample.createCriteria().andParolaEqualTo("pa006");
		listCodComRes = strcodcomresMapper.selectByExample(strcodcomresExample);
		listKanagra2 = new ArrayList<Long>();
		for (int i = 0; i < listCodComRes.size(); i++) {
			listKanagra2.add(listCodComRes.get(i).getKanagra());
		}
		if (listKanagra1.size() < listKanagra2.size()) {
			listKanagra1.retainAll(listKanagra2);
		} else {
			listKanagra2.retainAll(listKanagra1);
			listKanagra1.clear();
			listKanagra1.addAll(listKanagra2);
		}

		
		
		Strindir10Mapper strindir10Mapper = sqlSession.getMapper(Strindir10Mapper.class);
		strindir10Example = new Strindir10Example();
		strindir10Example.createCriteria().andParolaEqualTo("arco");
		strindir10Example.or(strindir10Example.createCriteria().andParolaEqualTo("angio'"));
		listIndir10 = strindir10Mapper.selectByExample(strindir10Example);
		listKanagra2 = new ArrayList<Long>();
		for (int i = 0; i < listIndir10.size(); i++) {
			
			if (listIndir10.get(i).getKanagra() == 11910003) {
				String albe = null;
			}
			
			listKanagra2.add(listIndir10.get(i).getKanagra());
		}
		if (listKanagra1.size() < listKanagra2.size()) {
			listKanagra1.retainAll(listKanagra2);
		} else {
			listKanagra2.retainAll(listKanagra1);
			listKanagra1.clear();
			listKanagra1.addAll(listKanagra2);
		}
		
		
    	sqlSession.close();
		String albe = null;
		
		
	}

}
//public class Prova01 {
//	static DateFormat dF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
//	static int x=0;
//	static int numElem1 = 15000;
//	static int numElem2 = 150000;
//	
//	public static void main(String[] args) {
//		
//		ArrayList<Integer> arr1 = new ArrayList<Integer>();
//		ArrayList<Integer> arr2 = new ArrayList<Integer>();
//		
//		for(int i=0;i<numElem1;i++){
//			arr1.add((int) (Math.random() * numElem1));
//			}
//		System.out.println("a " + dF.format(new Date()));
//		Collections.sort(arr1);
//		System.out.println("b " + dF.format(new Date()));
//		
//
//		for(int i=0;i<numElem2;i++){
//			arr2.add((int) (Math.random() * numElem2));			
//			}
//		System.out.println("c " + dF.format(new Date()));
//		Collections.sort(arr2);
//		System.out.println("d " + dF.format(new Date()));
//		
//		if (arr1.size() > arr2.size()) {
//			arr1.retainAll(arr2);
//		} else {
//			arr2.retainAll(arr1);
//		}
//		x=1;
//		System.out.println("e " + dF.format(new Date()));
//		System.out.println("f " + arr1.size());
//		System.out.println("g " + arr2.size());
//	}
//}
