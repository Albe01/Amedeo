package it.amedeo.mybatis.javaclient;

import it.amedeo.mybatis.javamodel.Strnomi08;
import it.amedeo.mybatis.javamodel.Strnomi08Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import it.amedeo.mybatis.javamodel.Strnomi08Key;

public interface Strnomi08Mapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int countByExample(Strnomi08Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int deleteByExample(Strnomi08Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Strnomi08Key key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int insert(Strnomi08 record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int insertSelective(Strnomi08 record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	List<Strnomi08> selectByExample(Strnomi08Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	Strnomi08 selectByPrimaryKey(Strnomi08Key key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") Strnomi08 record, @Param("example") Strnomi08Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") Strnomi08 record, @Param("example") Strnomi08Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(Strnomi08 record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table strnomi08
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Strnomi08 record);
}