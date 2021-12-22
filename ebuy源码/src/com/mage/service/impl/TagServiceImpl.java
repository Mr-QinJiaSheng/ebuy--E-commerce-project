package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mage.service.TagService;
import com.mage.util.DBUtil;
import com.mage.vo.Tag;

//查询标签内容放到集合中供给上一层使用！
public class TagServiceImpl implements TagService {
	@Override
	public List<Tag> findAll() {
		List<Tag> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_tag";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Tag tag = new Tag();
				tag.setId(rs.getInt("id"));
				tag.setName(rs.getString("name"));
				tag.setUrl(rs.getString("url"));
				list.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return list;
	}
}
