package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mage.service.ProductBigTypeService;
import com.mage.service.ProductSmallTypeService;
import com.mage.util.DBUtil;
import com.mage.vo.ProductBigType;
import com.mage.vo.ProductSmallType;

public class ProductBigTypeServiceImpl implements ProductBigTypeService {
	private ProductSmallTypeService smallTypeService = new ProductSmallTypeServiceImpl();
	
	
	@Override // 查询所有的大类别信息
	public List<ProductBigType> findAllBigType() {
		List<ProductBigType> list = new ArrayList<ProductBigType>();
		// 声明连接
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 创建连接
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_bigtype";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//遍历结果集
			while(rs.next()) {
				//创建实体类对象
				ProductBigType pbt = new ProductBigType();
				pbt.setId(rs.getInt("id"));
				pbt.setName(rs.getString("name"));
				pbt.setRemarks(rs.getString("remarks"));
	List<ProductSmallType> pst = smallTypeService.findByBigTypeId(rs.getInt("id"));
			pbt.setSmallTypeList(pst);
			list.add(pbt);
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
