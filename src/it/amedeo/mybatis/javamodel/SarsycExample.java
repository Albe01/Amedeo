package it.amedeo.mybatis.javamodel;

import java.util.ArrayList;
import java.util.List;

public class SarsycExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public SarsycExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sarsyc
	 * @mbggenerated
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sarsyc
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

		public Criteria andRegioneIsNull() {
			addCriterion("regione is null");
			return (Criteria) this;
		}

		public Criteria andRegioneIsNotNull() {
			addCriterion("regione is not null");
			return (Criteria) this;
		}

		public Criteria andRegioneEqualTo(String value) {
			addCriterion("regione =", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneNotEqualTo(String value) {
			addCriterion("regione <>", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneGreaterThan(String value) {
			addCriterion("regione >", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneGreaterThanOrEqualTo(String value) {
			addCriterion("regione >=", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneLessThan(String value) {
			addCriterion("regione <", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneLessThanOrEqualTo(String value) {
			addCriterion("regione <=", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneLike(String value) {
			addCriterion("regione like", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneNotLike(String value) {
			addCriterion("regione not like", value, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneIn(List<String> values) {
			addCriterion("regione in", values, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneNotIn(List<String> values) {
			addCriterion("regione not in", values, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneBetween(String value1, String value2) {
			addCriterion("regione between", value1, value2, "regione");
			return (Criteria) this;
		}

		public Criteria andRegioneNotBetween(String value1, String value2) {
			addCriterion("regione not between", value1, value2, "regione");
			return (Criteria) this;
		}

		public Criteria andIstatIsNull() {
			addCriterion("istat is null");
			return (Criteria) this;
		}

		public Criteria andIstatIsNotNull() {
			addCriterion("istat is not null");
			return (Criteria) this;
		}

		public Criteria andIstatEqualTo(String value) {
			addCriterion("istat =", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatNotEqualTo(String value) {
			addCriterion("istat <>", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatGreaterThan(String value) {
			addCriterion("istat >", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatGreaterThanOrEqualTo(String value) {
			addCriterion("istat >=", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatLessThan(String value) {
			addCriterion("istat <", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatLessThanOrEqualTo(String value) {
			addCriterion("istat <=", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatLike(String value) {
			addCriterion("istat like", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatNotLike(String value) {
			addCriterion("istat not like", value, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatIn(List<String> values) {
			addCriterion("istat in", values, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatNotIn(List<String> values) {
			addCriterion("istat not in", values, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatBetween(String value1, String value2) {
			addCriterion("istat between", value1, value2, "istat");
			return (Criteria) this;
		}

		public Criteria andIstatNotBetween(String value1, String value2) {
			addCriterion("istat not between", value1, value2, "istat");
			return (Criteria) this;
		}

		public Criteria andProgrminIsNull() {
			addCriterion("progrmin is null");
			return (Criteria) this;
		}

		public Criteria andProgrminIsNotNull() {
			addCriterion("progrmin is not null");
			return (Criteria) this;
		}

		public Criteria andProgrminEqualTo(String value) {
			addCriterion("progrmin =", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminNotEqualTo(String value) {
			addCriterion("progrmin <>", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminGreaterThan(String value) {
			addCriterion("progrmin >", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminGreaterThanOrEqualTo(String value) {
			addCriterion("progrmin >=", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminLessThan(String value) {
			addCriterion("progrmin <", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminLessThanOrEqualTo(String value) {
			addCriterion("progrmin <=", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminLike(String value) {
			addCriterion("progrmin like", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminNotLike(String value) {
			addCriterion("progrmin not like", value, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminIn(List<String> values) {
			addCriterion("progrmin in", values, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminNotIn(List<String> values) {
			addCriterion("progrmin not in", values, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminBetween(String value1, String value2) {
			addCriterion("progrmin between", value1, value2, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrminNotBetween(String value1, String value2) {
			addCriterion("progrmin not between", value1, value2, "progrmin");
			return (Criteria) this;
		}

		public Criteria andProgrmaxIsNull() {
			addCriterion("progrmax is null");
			return (Criteria) this;
		}

		public Criteria andProgrmaxIsNotNull() {
			addCriterion("progrmax is not null");
			return (Criteria) this;
		}

		public Criteria andProgrmaxEqualTo(String value) {
			addCriterion("progrmax =", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxNotEqualTo(String value) {
			addCriterion("progrmax <>", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxGreaterThan(String value) {
			addCriterion("progrmax >", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxGreaterThanOrEqualTo(String value) {
			addCriterion("progrmax >=", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxLessThan(String value) {
			addCriterion("progrmax <", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxLessThanOrEqualTo(String value) {
			addCriterion("progrmax <=", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxLike(String value) {
			addCriterion("progrmax like", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxNotLike(String value) {
			addCriterion("progrmax not like", value, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxIn(List<String> values) {
			addCriterion("progrmax in", values, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxNotIn(List<String> values) {
			addCriterion("progrmax not in", values, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxBetween(String value1, String value2) {
			addCriterion("progrmax between", value1, value2, "progrmax");
			return (Criteria) this;
		}

		public Criteria andProgrmaxNotBetween(String value1, String value2) {
			addCriterion("progrmax not between", value1, value2, "progrmax");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sarsyc
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
     * This class corresponds to the database table sarsyc
     *
     * @mbggenerated do_not_delete_during_merge Mon Feb 18 16:23:50 CET 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}