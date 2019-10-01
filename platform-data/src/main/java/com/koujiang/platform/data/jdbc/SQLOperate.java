package com.koujiang.platform.data.jdbc;

import com.unknown.platform.util.VNETookit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * <br>(c) Copyright 2017 koujiang901123@sina.com
 * <br>@description	:数据访问对象(抽象类)
 * <br>@file_name	:BaseSQLOperate.java
 * <br>@author		:koujiang 
 * <br>@create_time	:2017年3月10日 下午6:31:38
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Repository
public class SQLOperate extends JdbcDaoSupport implements ISQLOperate {

	@Autowired
	public void initDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	/**
	 * description getConnection:获取连接(待实现)
	 * <br>author ：koujiang
	 * <br>return :Connection
	 * <br>update ：2017年3月10日 下午6:31:45
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	//public abstract Connection getConnection();
	
	/**
	 * description resourceRelease:返回连接用于释放(待实现)
	 * <br>author ：koujiang
	 * <br>@param connection 
	 * <br>return :void
	 * <br>update ：2017年3月10日 下午6:35:45
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	public void resourceRelease(Connection connection) {
		if (connection != null) this.releaseConnection(connection);
	}

	/**
	 * description resourceRelease:释放资源
	 * <br>author ：koujiang
	 * <br>@param rs
	 * <br>@param pstmt
	 * <br>@param connection
	 * <br>@throws RuntimeException 
	 * <br>return :void
	 * <br>update ：2017年3月10日 下午6:53:51
	 * <br>mender ：(Please add the modifier name)
	 * <br>Modified ：(Please add modification date)
	 * <br>varsion ：v1.0.0
	 */
	private void resourceRelease(ResultSet rs, PreparedStatement pstmt, Connection connection) throws RuntimeException {
		try {
			if(null != rs) rs.close();
			if(null != pstmt) pstmt.close();
			resourceRelease(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int executeSQL(String sql, Object... params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("params: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = this.getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	public List<Object[]> querySQLForArray(String sql, Object... params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			List<Object[]> arrays = new ArrayList<Object[]>();
			ResultSetMetaData rsm =rs.getMetaData();
			int count=rsm.getColumnCount();
			while(rs.next()){
				Object[] values = new Object[count];
				for(int i=0;i<count;i++){
					String columnName = rsm.getColumnName(i+1)/*.toLowerCase()*/;
					Object value = rs.getObject(columnName);
					values[i] = value;
				}
				arrays.add(values);
			}
			return arrays;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(rs, pstmt, connection);
		}
	}

	public List<Map<String, Object>> querySQLForMap(String sql, Object[] params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
			ResultSetMetaData rsm =rs.getMetaData(); 
			int count=rsm.getColumnCount();
			while(rs.next()){
				Map<String,Object> map = new HashMap<String, Object>();
				for(int i=0;i<count;i++){
					//rsm.getColumnName(i+1)
					String columnName = rsm.getColumnLabel(i+1)/*.toLowerCase()*/;
					Object value = rs.getObject(columnName);
					map.put(columnName, value);
				}
				maps.add(map);
			}
			return maps;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	public Map<String, Object> querySQLMap(String sql, Object[] params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			Map<String,Object> maps = new LinkedHashMap<>();
			ResultSetMetaData rsm =rs.getMetaData();
			int count=rsm.getColumnCount();
			while(rs.next()){
				Map<String,Object> map = new HashMap<String, Object>();
				for(int i=0;i<count;i++){
					//rsm.getColumnName(i+1)
					String columnName = rsm.getColumnLabel(i+1)/*.toLowerCase()*/;
					map.put(columnName, rs.getObject(columnName));
				}
				return map;
			}
			return maps;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> E insertObject(E e) throws RuntimeException {
		if (VNETookit.isNull(e)) throw new NullPointerException("parameter: [e] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = this.getConnection();

			Object value = Analytic.getPkValue(e);
			if(!VNETookit.isNull(value) && !"0".equals(value.toString())) {
				//如果主键有值就不做处理
			}
			SQLHelper.SQLValueHandler svh = SQLHelper.insertSQL(e);
			pstmt = connection.prepareStatement(svh.sql);
			for (int i = 0; i < svh.values.length; i++) {
				pstmt.setObject(i + 1, svh.values[i]);
			}
			pstmt.executeUpdate();
			return e;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> List<E> insertObject(List<E> es) throws RuntimeException {
		if (VNETookit.isNull(es)) {
			throw new NullPointerException("parameter: [dtoList] can't be null");
		}
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = this.getConnection();

			E e = es.get(0);
			//TODO:如果主键有值就不处理
			/*
			 * 批量新增时，dto对象的属性值 转换为一个二维数组，
			 */
			SQLHelper.BatchSQLValueHandler bsvh = SQLHelper.insertSQL(es);//bsvh
			pstmt = connection.prepareStatement(bsvh.sql);
			boolean ifExecute = true;
			for (int i = 0; i < bsvh.values.length; i++) {
				for (int j = 0; j < bsvh.values[i].length; j++) {
					pstmt.setObject(j + 1, bsvh.values[i][j]);
				}
				pstmt.addBatch();
				if (i % 500 == 0) {
					pstmt.executeBatch();
					ifExecute = false;
				} else {
					if (!ifExecute) ifExecute = true;
				}
			}
			if (ifExecute) {
				pstmt.executeBatch();
			}
			return es;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> int updateObject(E e, String... fields) throws RuntimeException {
		if (VNETookit.isNull(e)) throw new NullPointerException("parameter: [dto] can't be null");
		/*
		 * 验证  dto 主键值是否为空
		 */
		if (VNETookit.isNull(Analytic.getPkValue(e))) throw new NullPointerException("bean primary key value is null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			SQLHelper.SQLValueHandler svh = SQLHelper.updateSQL(e, fields);
			connection = this.getConnection();
			pstmt = connection.prepareStatement(svh.sql);
			for (int i = 0; i < svh.values.length; i++) {
				pstmt.setObject(i + 1, svh.values[i]);
			}
			return pstmt.executeUpdate();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> int updateObject(List<E> es, String... fields) throws RuntimeException {
		if (VNETookit.isNull(es)) throw new NullPointerException("parameter: [es] can't be null");
		/*
		 * 验证  dto 主键值是否为空
		 */
		Connection connection = null;
		PreparedStatement pstmt = null;
		int changeRows = 0;
		try {
			for (E e : es) {
				if (VNETookit.isNull(Analytic.getPkValue(e))) throw new NullPointerException("bean primary key value is null");
			}
			SQLHelper.BatchSQLValueHandler bsvh = SQLHelper.updateSQL(es, fields);
			connection = this.getConnection();
			pstmt = connection.prepareStatement(bsvh.sql);
			for (int i = 0; i < bsvh.values.length; i++) {
				for (int j = 0; j < bsvh.values[i].length; j++) {
					pstmt.setObject(j + 1, bsvh.values[i][j]);
				}
				pstmt.addBatch();
				if ((i + 1) % 500 == 0) {
					int[] tmp = pstmt.executeBatch();
					for (int j : tmp) {
						changeRows += j;
					}
				}
			}
			if (bsvh.values.length % 500 != 0) {
				int[] tmp = pstmt.executeBatch();
				for (int j : tmp) {
					changeRows += j;
				}	
			}
			return changeRows;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> int updateObjectNep(E e) throws RuntimeException {
		if (VNETookit.isNull(e)) throw new NullPointerException("parameter: [e] can't be null");
		if (VNETookit.isNull(Analytic.getPkValue(e))) throw new NullPointerException("bean primary key value is null");
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			SQLHelper.SQLValueHandler svh = SQLHelper.updateNepSQL(e);
			connection = this.getConnection();
			pstmt = connection.prepareStatement(svh.sql);
			for (int i = 0; i < svh.values.length; i++) {
				pstmt.setObject(i + 1, svh.values[i]);
			}
			return pstmt.executeUpdate();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> int updateObjectNep(List<E> es) throws RuntimeException {
		if (VNETookit.isNull(es)) throw new NullPointerException("parameter: [dtoList] can't be null");
		/*
		 * 验证  dto 主键值是否为空
		 */
		Connection connection = null;
		PreparedStatement pstmt = null;
		int changeRows = 0;
		try {
			for (E e : es) {
				if (VNETookit.isNull(Analytic.getPkValue(e))) throw new NullPointerException("bean primary key value is null");
			}
			SQLHelper.BatchSQLValueHandler bsvh = SQLHelper.updateNepSQL(es);
			connection = this.getConnection();
			pstmt = connection.prepareStatement(bsvh.sql);
			for (int i = 0; i < bsvh.values.length; i++) {
				for (int j = 0; j < bsvh.values[i].length; j++) {
					pstmt.setObject(j + 1, bsvh.values[i][j]);
				}
				pstmt.addBatch();
				if ((i + 1) % 500 == 0) {
					int[] tmp = pstmt.executeBatch();
					for (int j : tmp) {
						changeRows += j;
					}
				}
			}
			if (bsvh.values.length % 500 != 0) {
				int[] tmp = pstmt.executeBatch();
				for (int j : tmp) {
					changeRows += j;
				}	
			}
			return changeRows;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> int deleteObject(E e) throws RuntimeException {
		if (VNETookit.isNull(e)) throw new NullPointerException("parameter: [e] can't be null");
		/*验证  dto 主键值是否为空*/
		if(VNETookit.isNull(Analytic.getPkValue(e))) throw new NullPointerException("e primary key value is null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			SQLHelper.SQLValueHandler svh = SQLHelper.deleteSQL(e);
			connection = this.getConnection();
			pstmt = connection.prepareStatement(svh.sql);
			pstmt.setObject(1, svh.values[0]);
			return pstmt.executeUpdate();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	@Deprecated
	public <E> int delateObject(List<E> es) throws RuntimeException {
		if (VNETookit.isNull(es)) throw new NullPointerException("parameter: [dtoList] can't be null");
		/*验证  dto 主键值是否为空*/
		for (E e : es) {
			if(VNETookit.isNull(Analytic.getPkValue(e))) throw new NullPointerException("e primary key value is null");
		}
		Connection connection = null;
		PreparedStatement pstmt = null;
		int changeRows = 0;
		try {
			SQLHelper.BatchSQLValueHandler bsvh = SQLHelper.deleteSQL(es);
			connection = this.getConnection();
			pstmt = connection.prepareStatement(bsvh.sql);
			for (int i = 0; i < bsvh.values.length; i++) {
				for (int j = 0; j < bsvh.values[i].length; j++) {
					pstmt.setObject(j + 1, bsvh.values[i][j]);
				}
				pstmt.addBatch();
				if ((i + 1) % 500 == 0) {
					int[] tmp = pstmt.executeBatch();
					for (int j = 0; j < tmp.length; j++) {
						changeRows += tmp[j];
					}
				}
			}
			if (bsvh.values.length % 500 != 0) {
				int[] tmp = pstmt.executeBatch();
				for (int i = 0; i < tmp.length; i++) {
					changeRows += tmp[i];
				}
			}
			return changeRows;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(null, pstmt, connection);
		}
	}

	public String queryForString(String sql, Object... params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(rs, pstmt, connection);
		}
	}

	public Double queryForDouble(String sql, Object... params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(rs, pstmt, connection);
		}
	}

	public int count(String sql, Object... params) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(rs, pstmt, connection);
		}
	}

	public <E> E queryForBean(String sql, Object[] params, Class<E> cls) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		if (cls == null) throw new NullPointerException("parameter: [cls] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			pstmt = connection.prepareStatement(sql.toString());
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			return ResultHelp.transToBean(rs, cls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(rs, pstmt, connection);
		}
	}

	public <E> List<E> queryForList(String sql, Object[] params, Class<E> cls) throws RuntimeException {
		if (VNETookit.isNull(sql)) throw new NullPointerException("parameter: [sql] can't be null");
		if (cls == null) throw new NullPointerException("parameter: [cls] can't be null");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			if (!VNETookit.isNull(params)) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			return ResultHelp.transToList(rs, cls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resourceRelease(rs, pstmt, connection);
		}
	}
}
