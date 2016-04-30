package it.amedeo.mybatis.sqlquery;

import java.util.List;

import it.amedeo.mybatis.javaclient.EffettiMapper;
import it.amedeo.mybatis.javamodel.Effetti;
import it.amedeo.mybatis.javamodel.EffettiExample;
import it.amedeo.utils.MyBatisConnectionFactory;

public class SqlEffetti {

	public Effetti getEffettiByPrimaryKey(Object where, String orderBy) {
		Effetti effetti = null;
		List<Effetti> listEffetti = getEffetti(where, null);
		if (listEffetti != null) {
			effetti = listEffetti.get(0);
		}
		return effetti;
	}
	
	public List<Effetti> getEffetti(Object where, String orderBy) {
		EffettiExample exampleWhere = whereExample((Effetti) where, orderBy);
		EffettiMapper effettiMapper = MyBatisConnectionFactory.getSqlSession().getMapper(EffettiMapper.class);
		List<Effetti> listEffetti = effettiMapper.selectByExample(exampleWhere);
		MyBatisConnectionFactory.closeSqlSession();
		return listEffetti;
	}
	
	public int insert(Effetti oggetto) {
		int ret = 0;
		try {		
			ret = MyBatisConnectionFactory.getSqlSession().getMapper(EffettiMapper.class).insert((Effetti) oggetto);
		} catch (Exception e) {
		}
		return ret;
	}

	
	private EffettiExample whereExample (Effetti object, String orderBy) {
		EffettiExample where = new EffettiExample();
		it.amedeo.mybatis.javamodel.EffettiExample.Criteria criteria = where.createCriteria();
		
		if (object.getKanagra() != null && !"".equals(object.getKanagra())) {
			criteria.andKanagraEqualTo(object.getKanagra());
		}
		if (object.getCciaapubblicazione() != null && !"".equals(object.getCciaapubblicazione())) {
			if (object.getCciaapubblicazione().contains("%")) {
				criteria.andCciaapubblicazioneLike(object.getCciaapubblicazione());				
			} else {
				criteria.andCciaapubblicazioneEqualTo(object.getCciaapubblicazione());
			}
		}

		if (!"".equals(orderBy) && orderBy != null) {
			where.setOrderByClause(orderBy);
		}
		return where;
	}
}
	
