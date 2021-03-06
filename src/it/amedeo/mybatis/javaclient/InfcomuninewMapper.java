package it.amedeo.mybatis.javaclient;

import it.amedeo.mybatis.javamodel.Infcomuninew;
import it.amedeo.mybatis.javamodel.InfcomuninewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfcomuninewMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	int countByExample(InfcomuninewExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	int deleteByExample(InfcomuninewExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	int insert(Infcomuninew record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	int insertSelective(Infcomuninew record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	List<Infcomuninew> selectByExample(InfcomuninewExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") Infcomuninew record, @Param("example") InfcomuninewExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infcomuninew
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") Infcomuninew record, @Param("example") InfcomuninewExample example);
}