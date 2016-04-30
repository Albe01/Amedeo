package it.amedeo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javaclient.LogelabMapper;
import it.amedeo.mybatis.javamodel.Logelab;

public class CreaLogElab {

	public CreaLogElab(String riga, String pgmElab) {
//		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
//		LogelabMapper logelabMapper = MyBatisConnectionFactory.getSqlSession().getMapper(LogelabMapper.class);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date dataOggi= new Date();
		Logelab logelab = new Logelab();
		logelab.setDatatime(dateFormat.format(dataOggi));
		logelab.setPgmelab(pgmElab.trim());
		logelab.setRiga(riga.trim());
//		logelabMapper.insert(logelab);
		MyBatisConnectionFactory.getSqlSession().getMapper(LogelabMapper.class).insert(logelab);
//		MyBatisConnectionFactory.commitSqlSession();
//		MyBatisConnectionFactory.closeSqlSession();
		System.out.println(dateFormat.format(dataOggi) + " " + riga);
	}

}
