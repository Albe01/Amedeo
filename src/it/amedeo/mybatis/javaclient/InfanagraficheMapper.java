package it.amedeo.mybatis.javaclient;

import it.amedeo.mybatis.javamodel.Infanagrafiche;
import it.amedeo.mybatis.javamodel.InfanagraficheExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfanagraficheMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int countByExample(InfanagraficheExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int deleteByExample(InfanagraficheExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Long progrriga);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int insert(Infanagrafiche record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int insertSelective(Infanagrafiche record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	List<Infanagrafiche> selectByExample(InfanagraficheExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	Infanagrafiche selectByPrimaryKey(Long progrriga);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") Infanagrafiche record,
			@Param("example") InfanagraficheExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") Infanagrafiche record, @Param("example") InfanagraficheExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(Infanagrafiche record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table infanagrafiche
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Infanagrafiche record);
}