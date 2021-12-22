package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mage.service.ProductSmallTypeService;
import com.mage.util.DBUtil;
import com.mage.vo.ProductSmallType;

public class ProductSmallTypeServiceImpl implements ProductSmallTypeService {
	@Override
	public List<ProductSmallType> findByBigTypeId(int bigTypeId) {
		List<ProductSmallType> list = new ArrayList<>();
		//声明连接
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
		conn = DBUtil.getConn();
		String sql = "select * from t_smalltype where bigTypeId = ?";//占位
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bigTypeId);
		rs = pstmt.executeQuery();
		//处理结果集
		 while(rs.next()) {
			 ProductSmallType pst = new ProductSmallType();
			 pst.setId(rs.getInt("id"));
			 pst.setName(rs.getString("name"));
			 pst.setRemarks(rs.getString("remarks"));
			 pst.setBigTypeId(rs.getInt("bigTypeId"));
			 list.add(pst);
		 }
		 return list;
		 
		} catch (SQLException e) {
			System.out.println("List<ProductSmallType> ERROR");
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

}
