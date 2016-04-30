package it.amedeo.mybatis.sqlquery;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import it.amedeo.mybatis.javaclient.InfeffettiMapper;
import it.amedeo.mybatis.javamodel.Infeffetti;
import it.amedeo.mybatis.javamodel.InfeffettiExample;
import it.amedeo.utils.MyBatisConnectionFactory;

public class SqlInfEffetti {

	public void SelectInfEffetti_Handler(ResultHandler<?> resultHandler, Infeffetti paramWhere, String orderBy) {
		InfeffettiExample where = whereExample(paramWhere, orderBy);
		try {
			MyBatisConnectionFactory.getSqlSession().select("it.amedeo.mybatis.javaclient.InfeffettiMapper.selectByExample", where, resultHandler);
		} catch (Exception e) {
		}
		return;
	}

	public Infeffetti getInfEffettiByPrimaryKey(Object where, String orderBy) {
		Infeffetti oggetto = null;
		List<Infeffetti> list = getInfEffetti(where, null);
		if (list != null) {
			oggetto = list.get(0);
		}
		return oggetto;
	}
	
	public List<Infeffetti> getInfEffetti(Object where, String orderBy) {
		InfeffettiExample exampleWhere = whereExample((Infeffetti) where, orderBy);
		InfeffettiMapper mapper = MyBatisConnectionFactory.getSqlSession().getMapper(InfeffettiMapper.class);
		List<Infeffetti> list = mapper.selectByExample(exampleWhere);
		MyBatisConnectionFactory.closeSqlSession();
		return list;
	}
	
	public int insert(Infeffetti oggetto) {
		int ret = 0;
		try {		
			ret = MyBatisConnectionFactory.getSqlSession().getMapper(InfeffettiMapper.class).insert((Infeffetti) oggetto);
		} catch (Exception e) {
		}
		return ret;
	}

	
	private InfeffettiExample whereExample (Infeffetti object, String orderBy) {
		InfeffettiExample where = new InfeffettiExample();
		it.amedeo.mybatis.javamodel.InfeffettiExample.Criteria criteria = where.createCriteria();
		
		if (object.getProgrriga() != null && !"".equals(object.getProgrriga())) {
			criteria.andProgrrigaEqualTo(object.getProgrriga());
		}
		if (object.getDateflusso() != null && !"".equals(object.getDateflusso())) {
			if (object.getDateflusso().contains("%")) {
				criteria.andDateflussoLike(object.getDateflusso());				
			} else {
				criteria.andDateflussoEqualTo(object.getDateflusso());
			}
		}
		if (object.getIdflusso() != null && !"".equals(object.getIdflusso())) {
			if (object.getIdflusso().contains("%")) {
				criteria.andIdflussoLike(object.getIdflusso());				
			} else {
				criteria.andIdflussoEqualTo(object.getIdflusso());
			}
		}

		if (!"".equals(orderBy) && orderBy != null) {
			where.setOrderByClause(orderBy);
		}
		return where;
	}
}
	
