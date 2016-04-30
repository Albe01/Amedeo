package it.amedeo.mybatis.sqlquery;

import java.util.List;

import it.amedeo.mybatis.javaclient.AnagraficheMapper;
import it.amedeo.mybatis.javamodel.Anagrafiche;
import it.amedeo.mybatis.javamodel.AnagraficheExample;
import it.amedeo.utils.MyBatisConnectionFactory;

public class SqlAnagrafiche {
	
	
	public Anagrafiche selectByPrimaryKey(Object where, String orderBy) {
		Anagrafiche oggetto = null;
		List<Anagrafiche> list = selectByExample(where, null);
		if (list != null) {
			oggetto = list.get(0);
		}
		return oggetto;
	}

	public List<Anagrafiche> selectByExample(Object where, String orderBy) {
		AnagraficheExample exampleWhere = whereExample((Anagrafiche) where, orderBy);
		AnagraficheMapper mapper = MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class);
		List<Anagrafiche> list = mapper.selectByExample(exampleWhere);
//		MyBatisConnectionFactory.closeSqlSession();
		return list;
	}

	public int insert(Anagrafiche oggetto) {
		int ret = 0;
		try {		
			ret = MyBatisConnectionFactory.getSqlSession().getMapper(AnagraficheMapper.class).insert((Anagrafiche) oggetto);
		} catch (Exception e) {
		}
		return ret;
	}

	private AnagraficheExample whereExample (Anagrafiche object, String orderBy) {
		AnagraficheExample where = new AnagraficheExample();
		it.amedeo.mybatis.javamodel.AnagraficheExample.Criteria criteria = where.createCriteria();
		
		if (object.getKanagra() != null) {
			criteria.andKanagraEqualTo(object.getKanagra());
		}
		if (object.getDateflusso() != null && !"".equals(object.getDateflusso())) {
			if (object.getDateflusso().contains("%")) {
				criteria.andDateflussoLike(object.getDateflusso());				
			} else {
				criteria.andDateflussoEqualTo(object.getDateflusso());
			}
		}

		if (!"".equals(orderBy) && orderBy != null) {
			where.setOrderByClause(orderBy);
		}
		return where;
	}


}
	
