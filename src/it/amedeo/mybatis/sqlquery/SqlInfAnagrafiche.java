package it.amedeo.mybatis.sqlquery;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import it.amedeo.mybatis.javaclient.InfanagraficheMapper;
import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.javamodel.InfanagraficheExample;
import it.amedeo.utils.MyBatisConnectionFactory;

public class SqlInfAnagrafiche {
	
	public void SelectInfAnagrafiche_Handler(ResultHandler<?> resultHandler, Infanagrafiche paramWhere, String orderBy) {
		InfanagraficheExample where = whereExample(paramWhere, orderBy);
		try {
			MyBatisConnectionFactory.getSqlSession().select("it.amedeo.mybatis.javaclient.InfanagraficheMapper.selectByExample", where, resultHandler);
		} catch (Exception e) {
		}
		return;
	}
	
	public Infanagrafiche getInfAnagraficheByPrimaryKey(Object where, String orderBy) {
		Infanagrafiche oggetto = null;
		List<Infanagrafiche> list = getInfAnagrafiche(where, null);
		if (list != null) {
			oggetto = list.get(0);
		}
		return oggetto;
	}

	public List<Infanagrafiche> getInfAnagrafiche(Object where, String orderBy) {
		InfanagraficheExample exampleWhere = whereExample((Infanagrafiche) where, orderBy);
		InfanagraficheMapper mapper = MyBatisConnectionFactory.getSqlSession().getMapper(InfanagraficheMapper.class);
		List<Infanagrafiche> list = mapper.selectByExample(exampleWhere);
		MyBatisConnectionFactory.closeSqlSession();
		return list;
	}

	public int insert(Infanagrafiche oggetto) {
		int ret = 0;
		try {		
			ret = MyBatisConnectionFactory.getSqlSession().getMapper(InfanagraficheMapper.class).insert((Infanagrafiche) oggetto);
		} catch (Exception e) {
		}
		return ret;
	}

	private InfanagraficheExample whereExample (Infanagrafiche object, String orderBy) {
		InfanagraficheExample where = new InfanagraficheExample();
		it.amedeo.mybatis.javamodel.InfanagraficheExample.Criteria criteria = where.createCriteria();
		
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
	
