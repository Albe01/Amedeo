package it.amedeo.mybatis.sqlquery;

import java.util.List;

import it.amedeo.mybatis.javaclient.InfcomuniMapper;
import it.amedeo.mybatis.javamodel.Infcomuni;
import it.amedeo.mybatis.javamodel.InfcomuniExample;
import it.amedeo.utils.MyBatisConnectionFactory;

public class SqlInfComuni {
	
	
	public Infcomuni selectByPrimaryKey(Object where, String orderBy) {
		Infcomuni oggetto = null;
		List<Infcomuni> list = selectByExample(where, null);
		if (list != null) {
			oggetto = list.get(0);
		}
		return oggetto;
	}

	public List<Infcomuni> selectByExample(Object where, String orderBy) {
		InfcomuniExample exampleWhere = whereExample((Infcomuni) where, orderBy);
		InfcomuniMapper mapper = MyBatisConnectionFactory.getSqlSession().getMapper(InfcomuniMapper.class);
		List<Infcomuni> list = mapper.selectByExample(exampleWhere);
		MyBatisConnectionFactory.closeSqlSession();
		return list;
	}

	public List<Infcomuni> selectCodRegioneNot00() {
		InfcomuniExample where = new InfcomuniExample();
		it.amedeo.mybatis.javamodel.InfcomuniExample.Criteria criteria = where.createCriteria();
		criteria.andCodregioneNotEqualTo("00");
		InfcomuniMapper mapper = MyBatisConnectionFactory.getSqlSession().getMapper(InfcomuniMapper.class);
		List<Infcomuni> list = mapper.selectByExample(where);
		MyBatisConnectionFactory.closeSqlSession();
		return list;
	}

	public int insert(Infcomuni oggetto) {
		int ret = 0;
		try {		
			ret = MyBatisConnectionFactory.getSqlSession().getMapper(InfcomuniMapper.class).insert((Infcomuni) oggetto);
		} catch (Exception e) {
		}
		return ret;
	}

	private InfcomuniExample whereExample (Infcomuni object, String orderBy) {
		InfcomuniExample where = new InfcomuniExample();
		it.amedeo.mybatis.javamodel.InfcomuniExample.Criteria criteria = where.createCriteria();
		
		if (object.getCodregione() != null && !"".equals(object.getCodregione())) {
			if (object.getCodregione().contains("%")) {
				criteria.andCodregioneLike(object.getCodregione());				
			} else {
				criteria.andCodregioneEqualTo(object.getCodregione());
			}
		}

		if (!"".equals(orderBy) && orderBy != null) {
			where.setOrderByClause(orderBy);
		}
		return where;
	}


}
	
