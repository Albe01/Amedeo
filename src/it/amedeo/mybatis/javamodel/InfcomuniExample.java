package it.amedeo.mybatis.javamodel;

import java.util.ArrayList;
import java.util.List;

public class InfcomuniExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public InfcomuniExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andCodregioneIsNull() {
			addCriterion("codregione is null");
			return (Criteria) this;
		}

		public Criteria andCodregioneIsNotNull() {
			addCriterion("codregione is not null");
			return (Criteria) this;
		}

		public Criteria andCodregioneEqualTo(String value) {
			addCriterion("codregione =", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneNotEqualTo(String value) {
			addCriterion("codregione <>", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneGreaterThan(String value) {
			addCriterion("codregione >", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneGreaterThanOrEqualTo(String value) {
			addCriterion("codregione >=", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneLessThan(String value) {
			addCriterion("codregione <", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneLessThanOrEqualTo(String value) {
			addCriterion("codregione <=", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneLike(String value) {
			addCriterion("codregione like", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneNotLike(String value) {
			addCriterion("codregione not like", value, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneIn(List<String> values) {
			addCriterion("codregione in", values, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneNotIn(List<String> values) {
			addCriterion("codregione not in", values, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneBetween(String value1, String value2) {
			addCriterion("codregione between", value1, value2, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodregioneNotBetween(String value1, String value2) {
			addCriterion("codregione not between", value1, value2, "codregione");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaIsNull() {
			addCriterion("codiceprovincia is null");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaIsNotNull() {
			addCriterion("codiceprovincia is not null");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaEqualTo(String value) {
			addCriterion("codiceprovincia =", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaNotEqualTo(String value) {
			addCriterion("codiceprovincia <>", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaGreaterThan(String value) {
			addCriterion("codiceprovincia >", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaGreaterThanOrEqualTo(String value) {
			addCriterion("codiceprovincia >=", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaLessThan(String value) {
			addCriterion("codiceprovincia <", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaLessThanOrEqualTo(String value) {
			addCriterion("codiceprovincia <=", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaLike(String value) {
			addCriterion("codiceprovincia like", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaNotLike(String value) {
			addCriterion("codiceprovincia not like", value, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaIn(List<String> values) {
			addCriterion("codiceprovincia in", values, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaNotIn(List<String> values) {
			addCriterion("codiceprovincia not in", values, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaBetween(String value1, String value2) {
			addCriterion("codiceprovincia between", value1, value2, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodiceprovinciaNotBetween(String value1, String value2) {
			addCriterion("codiceprovincia not between", value1, value2, "codiceprovincia");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneIsNull() {
			addCriterion("codicecomune is null");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneIsNotNull() {
			addCriterion("codicecomune is not null");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneEqualTo(String value) {
			addCriterion("codicecomune =", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneNotEqualTo(String value) {
			addCriterion("codicecomune <>", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneGreaterThan(String value) {
			addCriterion("codicecomune >", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneGreaterThanOrEqualTo(String value) {
			addCriterion("codicecomune >=", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneLessThan(String value) {
			addCriterion("codicecomune <", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneLessThanOrEqualTo(String value) {
			addCriterion("codicecomune <=", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneLike(String value) {
			addCriterion("codicecomune like", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneNotLike(String value) {
			addCriterion("codicecomune not like", value, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneIn(List<String> values) {
			addCriterion("codicecomune in", values, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneNotIn(List<String> values) {
			addCriterion("codicecomune not in", values, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneBetween(String value1, String value2) {
			addCriterion("codicecomune between", value1, value2, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodicecomuneNotBetween(String value1, String value2) {
			addCriterion("codicecomune not between", value1, value2, "codicecomune");
			return (Criteria) this;
		}

		public Criteria andCodistatIsNull() {
			addCriterion("codistat is null");
			return (Criteria) this;
		}

		public Criteria andCodistatIsNotNull() {
			addCriterion("codistat is not null");
			return (Criteria) this;
		}

		public Criteria andCodistatEqualTo(String value) {
			addCriterion("codistat =", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatNotEqualTo(String value) {
			addCriterion("codistat <>", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatGreaterThan(String value) {
			addCriterion("codistat >", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatGreaterThanOrEqualTo(String value) {
			addCriterion("codistat >=", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatLessThan(String value) {
			addCriterion("codistat <", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatLessThanOrEqualTo(String value) {
			addCriterion("codistat <=", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatLike(String value) {
			addCriterion("codistat like", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatNotLike(String value) {
			addCriterion("codistat not like", value, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatIn(List<String> values) {
			addCriterion("codistat in", values, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatNotIn(List<String> values) {
			addCriterion("codistat not in", values, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatBetween(String value1, String value2) {
			addCriterion("codistat between", value1, value2, "codistat");
			return (Criteria) this;
		}

		public Criteria andCodistatNotBetween(String value1, String value2) {
			addCriterion("codistat not between", value1, value2, "codistat");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaIsNull() {
			addCriterion("siglaprovincia is null");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaIsNotNull() {
			addCriterion("siglaprovincia is not null");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaEqualTo(String value) {
			addCriterion("siglaprovincia =", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaNotEqualTo(String value) {
			addCriterion("siglaprovincia <>", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaGreaterThan(String value) {
			addCriterion("siglaprovincia >", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaGreaterThanOrEqualTo(String value) {
			addCriterion("siglaprovincia >=", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaLessThan(String value) {
			addCriterion("siglaprovincia <", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaLessThanOrEqualTo(String value) {
			addCriterion("siglaprovincia <=", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaLike(String value) {
			addCriterion("siglaprovincia like", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaNotLike(String value) {
			addCriterion("siglaprovincia not like", value, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaIn(List<String> values) {
			addCriterion("siglaprovincia in", values, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaNotIn(List<String> values) {
			addCriterion("siglaprovincia not in", values, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaBetween(String value1, String value2) {
			addCriterion("siglaprovincia between", value1, value2, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andSiglaprovinciaNotBetween(String value1, String value2) {
			addCriterion("siglaprovincia not between", value1, value2, "siglaprovincia");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneIsNull() {
			addCriterion("infcodicecomune is null");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneIsNotNull() {
			addCriterion("infcodicecomune is not null");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneEqualTo(String value) {
			addCriterion("infcodicecomune =", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneNotEqualTo(String value) {
			addCriterion("infcodicecomune <>", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneGreaterThan(String value) {
			addCriterion("infcodicecomune >", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneGreaterThanOrEqualTo(String value) {
			addCriterion("infcodicecomune >=", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneLessThan(String value) {
			addCriterion("infcodicecomune <", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneLessThanOrEqualTo(String value) {
			addCriterion("infcodicecomune <=", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneLike(String value) {
			addCriterion("infcodicecomune like", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneNotLike(String value) {
			addCriterion("infcodicecomune not like", value, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneIn(List<String> values) {
			addCriterion("infcodicecomune in", values, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneNotIn(List<String> values) {
			addCriterion("infcodicecomune not in", values, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneBetween(String value1, String value2) {
			addCriterion("infcodicecomune between", value1, value2, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andInfcodicecomuneNotBetween(String value1, String value2) {
			addCriterion("infcodicecomune not between", value1, value2, "infcodicecomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneIsNull() {
			addCriterion("denomcomune is null");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneIsNotNull() {
			addCriterion("denomcomune is not null");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneEqualTo(String value) {
			addCriterion("denomcomune =", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneNotEqualTo(String value) {
			addCriterion("denomcomune <>", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneGreaterThan(String value) {
			addCriterion("denomcomune >", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneGreaterThanOrEqualTo(String value) {
			addCriterion("denomcomune >=", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneLessThan(String value) {
			addCriterion("denomcomune <", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneLessThanOrEqualTo(String value) {
			addCriterion("denomcomune <=", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneLike(String value) {
			addCriterion("denomcomune like", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneNotLike(String value) {
			addCriterion("denomcomune not like", value, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneIn(List<String> values) {
			addCriterion("denomcomune in", values, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneNotIn(List<String> values) {
			addCriterion("denomcomune not in", values, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneBetween(String value1, String value2) {
			addCriterion("denomcomune between", value1, value2, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomcomuneNotBetween(String value1, String value2) {
			addCriterion("denomcomune not between", value1, value2, "denomcomune");
			return (Criteria) this;
		}

		public Criteria andDenomregioneIsNull() {
			addCriterion("denomregione is null");
			return (Criteria) this;
		}

		public Criteria andDenomregioneIsNotNull() {
			addCriterion("denomregione is not null");
			return (Criteria) this;
		}

		public Criteria andDenomregioneEqualTo(String value) {
			addCriterion("denomregione =", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneNotEqualTo(String value) {
			addCriterion("denomregione <>", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneGreaterThan(String value) {
			addCriterion("denomregione >", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneGreaterThanOrEqualTo(String value) {
			addCriterion("denomregione >=", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneLessThan(String value) {
			addCriterion("denomregione <", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneLessThanOrEqualTo(String value) {
			addCriterion("denomregione <=", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneLike(String value) {
			addCriterion("denomregione like", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneNotLike(String value) {
			addCriterion("denomregione not like", value, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneIn(List<String> values) {
			addCriterion("denomregione in", values, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneNotIn(List<String> values) {
			addCriterion("denomregione not in", values, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneBetween(String value1, String value2) {
			addCriterion("denomregione between", value1, value2, "denomregione");
			return (Criteria) this;
		}

		public Criteria andDenomregioneNotBetween(String value1, String value2) {
			addCriterion("denomregione not between", value1, value2, "denomregione");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table infcomuni
	 * @mbggenerated
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table infcomuni
     *
     * @mbggenerated do_not_delete_during_merge Mon Feb 18 16:23:50 CET 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}