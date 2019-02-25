package bean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Info;
public class InfoBean {
	public List<Info> login(String id, String pass) {
		Connection conn = DBConn.getConnection();
		int flag = 0;
		List<Info> data = 
				new ArrayList<Info>();
		try {
			String sql = "select * from user_info where userId = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			String Pass;
			Info info = new Info();
			System.out.println("id= " + id + ",pass = "+pass);
			if(rs.next()) {
				Pass = rs.getString("pass");
				if(Pass.equals(pass)) {
					flag = 1;
					info.set_idnumber(rs.getString(1));
					info.set_name(rs.getString(2));
					info.set_imghead(rs.getString(4));
					info.setFan(rs.getString(5));
					info.setFollow(rs.getString(6));
					data.add(info);
				}
				System.out.println("id= " + id + ",pass = "+Pass);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("鏁版嵁搴撴煡璇㈠け璐ワ紒");
		}finally {
			if(conn != null) {
				try {
					conn.close();
				}catch (SQLException e) {
					System.out.println("鏁版嵁搴撳叧闂け璐ワ紒");
				}
			}
		}
		return data;
	}
	public int register(String id, String pass) {
		Connection conn = DBConn.getConnection();
		int flag = 0;
		try {
			String sql = "select * from user_info where userId = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {//璐﹀彿ID宸插瓨鍦� 闇�鏇存崲
				flag = 3;
			}
			else {
				if(pass.length() < 8)
					flag = 2;
				else {
					//娉ㄥ唽鐨勬椂鍊欏彧瀛樺叆id 鍜屽瘑鐮� 澶村儚涓洪粯璁ゅご鍍� 绮変笣鍜屽叧娉ㄧ殑浜轰负0
					String sql1 = "Insert into user_info values('"+id+"','','"+pass+"','nothing','0','0')";
					PreparedStatement st1 = conn.prepareStatement(sql1);
					int rs1 = st1.executeUpdate();
					
					String sql2 = "Insert into follow_info values('"+id+"','"+id+"')";
					PreparedStatement st2 = conn.prepareStatement(sql2);
					int rs2 = st2.executeUpdate();
					if(rs2 == 1 && rs1 == 1)
						flag = 1;
				}
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("鏁版嵁搴撴煡璇㈠け璐ワ紒");
		}finally {
			if(conn != null) {
				try {
					conn.close();
				}catch (SQLException e) {
					System.out.println("鏁版嵁搴撳叧闂け璐ワ紒");
				}
			}
		}
		return flag;
	}
	
	public int cmpTime(String before) {
		int flag = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String now = format.format(date);
		int b_year = Integer.parseInt(before.substring(0, 4));
		int n_year = Integer.parseInt(now.substring(0, 4));
		int b_month = Integer.parseInt(before.substring(5, 7));  
		int n_month = Integer.parseInt(now.substring(5, 7));  
		int b_day = Integer.parseInt(before.substring(8, 10)); 
		int n_day = Integer.parseInt(now.substring(8, 10));  
		if(b_year == n_year && b_month == n_month && b_day==n_day)//褰撳ぉ
			flag = 0;
		else if(b_year == n_year)//涓嶆槸褰撳ぉ 浣嗘槸鏄綋骞�
			flag = 1;
		else
			flag = 2;//涓嶆槸褰撳勾
		
		return flag;
	}
	public List<Info> getAll(String id){
	
		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			//String sql = "select * from major_info where _idnumber in (select id from follow where userId = ?) or _idnumber = ? order by _twiid DESC";
			/*String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,"
			+ "major_info.cmt,major_info.repost,major_info.like,major_info.exist,major_info.origin,"
			+ "user_info.headimage,user_info.name from major_info,user_info where major_info.userId = user_info.userId and userId in (select followId from follow_info where userId = ?) "
			+ "or userId = ? order by major_info.articleId DESC";*/
			String sql0 = "select * from user_info where userId = ?";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			st0.setString(1, id);
			ResultSet rs0 = st0.executeQuery();
			Info info1 = new Info();
			if(rs0.next()) {
				info1.set_idnumber(rs0.getString(1));
				info1.set_name(rs0.getString(2));
				info1.set_imghead(rs0.getString(4));
				info1.setFan(rs0.getString(5));
				info1.setFollow(rs0.getString(6));
				info1.setType(-3);
				data.add(info1);
				
			}
			

			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt," + 
					"major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name, pic from major_info,user_info" + 
					" where major_info.userId = user_info.userId and major_info.userId in (select followId from follow_info where userId = ?)"
					+ "order by articleId DESC ";
			PreparedStatement st = 
					conn.prepareStatement(sql);
			st.setString(1,id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int flag;
			
			while( rs.next() ){
				//鏈汉鐨勭殑鍐呭 鐩稿悓
				int articleId = rs.getInt(1);
				String _idnumber = rs.getString(2);
				String _content = rs.getString(3);
				String _wordTime = rs.getString(4);
				String _ctm = rs.getString(5);
				String _tn = rs.getString(6);
				String _lk = rs.getString(7);
				//绫诲瀷   鏄惁鏄浆鍙戠殑 0鏄師鍒� 涓嶆槸0鍒欎负杞彂鐨勬帹鏂囩殑id
			    int type = rs.getInt(8);
				String _imghead = rs.getString(9);
				String _name = rs.getString(10);
				String pic = rs.getString(11);
				//鏃堕棿杞崲
				flag = cmpTime(_wordTime);
				if(flag == 0)
					_wordTime = _wordTime.substring(12, 17);
				if(flag == 1)
					_wordTime = _wordTime.substring(5, 17);
				else if(flag == 2)
					_wordTime = _wordTime.substring(0, 17);
				if(type == 0) {
					//System.out.println(_wordTime);

					Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
			                  _ctm,  _tn,  _lk);
					if(!pic.equals("noPic")) {
						type = -4;
						info.setPic(pic);
					}
					info.setType(type);
					info.setArticleId(articleId);
					data.add( info );
				}
				else {
					//杞彂鍒汉鐨勫唴瀹�
				    //鍚嶅瓧2
				     String _name2;
				    //璐﹀彿2
				     String _idnumber2;
				    //鍙戣█鏃堕棿2
				     String _wordTime2;
				    //鍐呭2
				     String _content2;
				     String sql1 = "select major_info.userId,user_info.name,major_info.content,major_info.time" + 
								" from major_info,user_info" + 
								" where major_info.userId = user_info.userId and major_info.articleId = ?";
				     PreparedStatement st1 = conn.prepareStatement(sql1);
				     st1.setInt(1, type);
				     ResultSet rs1 = st1.executeQuery();
				     if(rs1.next()) {
				    	 _idnumber2 = rs1.getString(1);
				    	 _name2 = rs1.getString(2);
				    	 _content2 = rs1.getString(3);
				    	 _wordTime2 = rs1.getString(4);
				    	 flag = cmpTime(_wordTime2);
				    	 if(flag == 0)
							_wordTime2 = _wordTime2.substring(12, 17);
				    	 if(flag == 1)
							_wordTime2 = _wordTime2.substring(5, 17);
				    	 else if(flag == 2)
							_wordTime2 = _wordTime2.substring(0, 17);
							
				    	 Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
				    	 info.setType(type);
				    	 info.setArticleId(articleId);
				    	 data.add(info);
				     }
				     else {
				    	 type = -1;
				    	 Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk);
						info.setType(type);
						info.setArticleId(articleId);
						data.add(info);
				     }
				}				
			}
		
			
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
	}
	public List<Info> get_Recent(String id){
		
		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			String sql = "select major_info.userId,major_info.content," + 
					"user_info.headimage,user_info.name,major_info.articleId from major_info,user_info" + 
					" where major_info.userId = user_info.userId and major_info.userId "
					+ "in (select followId from follow_info where userId = ?)"
					+ "order by articleId DESC ";
			PreparedStatement st = 
					conn.prepareStatement(sql);
			st.setString(1,id);
			System.out.println(id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int flag = 1;
			
			while( rs.next() ){
				//
				String _idnumber = rs.getString(1);
				String _content = rs.getString(2);
				String _imghead = rs.getString(3);
				String _name = rs.getString(4);
				int articleId = rs.getInt(5);
				if(_name == null) {
					_name = _idnumber;
				}
				if(!_idnumber.equals(id)){
					Info info = new Info();
					info.set_name(_name);
					info.set_content(_content);
					info.set_imghead(_imghead);
					info.setArticleId(articleId);
					data.add(info);
					flag++;
					System.out.println(flag);
					if(flag > 5)
						break;
				}
				
				
			
						
			}
		
			
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
	}
	
	public int addTwitter(Info info ) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			String sql00 = "select name from user_info where userId = ?";
			PreparedStatement st00 = conn.prepareStatement(sql00);
			st00.setString(1, info.get_idnumber());
			ResultSet rs0 = st00.executeQuery();
			String name = "";
			if(rs0.next()) {
				name = rs0.getString(1);
			}
			info.set_name(name);
			
			//瑕佸厛鑾峰彇琛ㄤ腑鐨刟rticleId鐨勬渶澶у�� 鐒跺悗鍔犱竴 浣滀负褰撳墠鎺ㄦ枃鐨刬d1
			String sql0 = "select max(articleId) from major_info";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			ResultSet rs = st0.executeQuery();
		
			int articleId = 0;
			if(rs.next()) {
				articleId = rs.getInt(1);
			}
			articleId++;
			info.setArticleId(articleId);
			
			String sql = " insert into major_info "
					+ "  values(?,?,?,?,'0','0','0','0',?) ";
			PreparedStatement st = conn.prepareStatement( sql);
			int i = 1;

			st.setInt( i, info.getArticleId());
			++i;
			st.setString(i, info.get_idnumber());
			++i;
			st.setString(i, info.get_content());
			++i;
			st.setString(i, info.get_wordTime());
			++i;
			st.setString(i, info.getPic());
		
			System.out.printf("sql = %s\n", st.toString());
			
			flag = st.executeUpdate(  );
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "鍏抽棴杩炴帴澶辫触" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
		
	}
	public int addCmt(Info info ) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			String sql = "select * from major_info where articleId = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, info.getArticleId());
			
			ResultSet rs = st.executeQuery();
			System.out.println(info.getArticleId());
			if(!rs.next()) {
				flag = -1;//原文已被删除
			}
			else {
				//瑕佸厛鑾峰彇琛ㄤ腑鐨刟rticleId鐨勬渶澶у�� 鐒跺悗鍔犱竴 浣滀负褰撳墠鎺ㄦ枃鐨刬d1
				String sql0 = "select max(cmtId) from cmt_info";
				PreparedStatement st0 = conn.prepareStatement(sql0);
				ResultSet rs0 = st0.executeQuery();
			
				int cmtId = 0;
				if(rs0.next()) {
					cmtId = rs0.getInt(1);
				}
				cmtId++;
				System.out.println(info.get_content());
				String sql1 = " insert into cmt_info "
						+ "  values(?,?,?,?,?) ";
				PreparedStatement st1 = conn.prepareStatement(sql1);
				int i = 1;

				st1.setInt( i, info.getArticleId());
				++i;
				st1.setString(i, info.get_idnumber());
				++i;
				st1.setString(i, info.get_content());
				++i;
				st1.setString(i, info.get_wordTime());
				++i;
				st1.setInt(i,cmtId);
				++i;
			
				System.out.printf("sql = %s\n", st1.toString());
				
				flag = st1.executeUpdate();
				
				if(flag == 1) {
					System.out.println("asfnlafafnaksnf");
					String sql2 = "update major_info set cmt = cmt +1 where articleId = ?";
					PreparedStatement st2 = conn.prepareStatement(sql2);
					st2.setInt(1, info.getArticleId());
					flag = st2.executeUpdate();
				}
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "鍏抽棴杩炴帴澶辫触" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
		
	}
	public List<Info> getCmt(String id){
		
		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt," + 
					"major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name, pic from major_info,user_info" + 
					" where major_info.userId = user_info.userId and articleId = ?";
			PreparedStatement st = 
					conn.prepareStatement(sql);
			st.setString(1,id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int flag;
			
			if( rs.next() ){
				//本人的的内容 相同
				int articleId = rs.getInt(1);
				String _idnumber = rs.getString(2);
				String _content = rs.getString(3);
				String _wordTime = rs.getString(4);
				String _ctm = rs.getString(5);
				String _tn = rs.getString(6);
				String _lk = rs.getString(7);
				//类型   是否是转发的 0是原创 不是0则为转发的推文的id
			    int type = rs.getInt(8);
				String _imghead = rs.getString(9);
				String _name = rs.getString(10);
				String pic = rs.getString(11);
				//时间转换
				flag = cmpTime(_wordTime);
				if(flag == 0)
					_wordTime = _wordTime.substring(12, 17);
				if(flag == 1)
					_wordTime = _wordTime.substring(5, 17);
				else if(flag == 2)
					_wordTime = _wordTime.substring(0, 17);
				if(type == 0) {
					//System.out.println(_wordTime);

					Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
			                  _ctm,  _tn,  _lk);
					if(!pic.equals("noPic")) {
						info.setPic(pic);
						type = -4;
					}
					info.setType(type);
					info.setArticleId(articleId);
					data.add( info );
				}
				else {
					//转发别人的内容
				    //名字2
				     String _name2;
				    //账号2
				     String _idnumber2;
				    //发言时间2
				     String _wordTime2;
				    //内容2
				     String _content2;
				     String sql1 = "select major_info.userId,user_info.name,major_info.content,major_info.time" + 
								" from major_info,user_info" + 
								" where major_info.userId = user_info.userId and major_info.articleId = ?";
				     PreparedStatement st1 = conn.prepareStatement(sql1);
				     st1.setInt(1, type);
				     ResultSet rs1 = st1.executeQuery();
				     if(rs1.next()) {
				    	 _idnumber2 = rs1.getString(1);
				    	 _name2 = rs1.getString(2);
				    	 _content2 = rs1.getString(3);
				    	 _wordTime2 = rs1.getString(4);
				    	 flag = cmpTime(_wordTime2);
				    	 if(flag == 0)
							_wordTime2 = _wordTime2.substring(12, 17);
				    	 if(flag == 1)
							_wordTime2 = _wordTime2.substring(5, 17);
				    	 else if(flag == 2)
							_wordTime2 = _wordTime2.substring(0, 17);
							
				    	 Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
				    	 info.setType(type);
				    	 info.setArticleId(articleId);
				    	 data.add(info);
				     }
				     else {
				    	 type = -1;
				    	 Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk);
						info.setType(type);
						info.setArticleId(articleId);
						data.add(info);
				     }
				}				
			}
			
			/*String sql1 = "select cmt_info.articleId, cmt_info.userId, cmt_info.cmtContent, cmt_info.cmtTime, cmt_info.cmtId,"
					+ "user_info.userId, user_info.name, user_info.headimage"
					+ "from cmt_info,user_info where cmt_info.userId = user_info.userId"
					+ " and articleId = ? order by cmtId DESC";*/
			String sql1 = "select cmt_info.articleId, cmt_info.userId, cmt_info.cmtContent, cmt_info.cmtTime, cmt_info.cmtId," + 
					"user_info.userId, user_info.name, user_info.headimage " + 
					"from cmt_info,user_info where cmt_info.userId = user_info.userId " + 
					"and articleId = ?  order by cmtId DESC";
			PreparedStatement st1 = conn.prepareStatement(sql1);
			//st1.setInt(1, Integer.parseInt(id));
			st1.setString(1, id);
			ResultSet rs1 = st1.executeQuery();
			while (rs1.next()) {
				String _articleId = rs1.getString(1);
			//	String _cmtuserId = rs1.getString(2);
				String _cmtContent = rs1.getString(3);
				String _cmtTime = rs1.getString(4);
				String _cmtId = rs1.getString(5);
				String _userId = rs1.getString(6);
				String _userName = rs1.getString(7);
				String _userHeadimage = rs1.getString(8);
				flag = cmpTime(_cmtTime);
		    	 if(flag == 0)
		    		 _cmtTime = _cmtTime.substring(12, 17);
		    	 if(flag == 1)
		    		 _cmtTime = _cmtTime.substring(5, 17);
		    	 else if(flag == 2)
		    		 _cmtTime = _cmtTime.substring(0, 17);
				//String _imghead, String _name, String _idnumber, String _wordTime, String _content
				Info info = new Info(_userHeadimage,  _userName,  _userId,  _cmtTime,  _cmtContent);
			//	info.setType(type);
			//	info.setArticleId(articleId);
				info.setType(-2);
				data.add( info );
				
			}
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
	}
	public List<Info> follow(String id){
		Connection conn = DBConn.getConnection();
		int flag=0;
		List<Info> data = 
				new ArrayList<Info>();
		try {
			String sql0 = "select * from user_info where userId = ?";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			st0.setString(1, id);
			ResultSet rs0 = st0.executeQuery();
			Info info1 = new Info();
			if(rs0.next()) {
				info1.set_idnumber(rs0.getString(1));
				info1.set_name(rs0.getString(2));
				info1.set_imghead(rs0.getString(4));
				info1.setFan(rs0.getString(5));
				info1.setFollow(rs0.getString(6));
				info1.setType(0);
				data.add(info1);
				
			}
			
			String sql = "select user_info.userId, name, headimage, fan, follow" + 
					" from user_info,follow_info "+
					" where user_info.userId = follow_info.followId and follow_info.userId = ?";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, id);
				ResultSet rs = st.executeQuery();
				while (rs.next()){
					String userId = rs.getString("user_info.userId");
					String imgId = rs.getString( "headimage" );
					String name = rs.getString( "name" );		
					String fan = rs.getString( "fan" );
					String follow= rs.getString( "follow" );
					if (!userId.equals(id)) {
						Info info = new Info();
						info.set_idnumber(userId);
						info.set_imghead(imgId);
						info.set_name(name);
						info.setFan(fan);
						info.setFollow(follow);
						info.setType(-1);
						data.add( info );
					}
				}

			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "关闭数据库失败" + e.getMessage()  );
				}// try
			}// if
		}
		return data;
	}
	
	public int unfollow(String userId,String followId) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			String sql = "delete  from follow_info where userId = ? and followId = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, userId);
			st.setString(2, followId);
			flag = st.executeUpdate();
			if(flag == 1) {
				//关注数量减一
				String sql2 = "update user_info set follow = follow -1 where userId = ?";
				PreparedStatement st2 = conn.prepareStatement(sql2);
				st2.setString(1, userId);
				flag = st2.executeUpdate();
				if(flag == 1) {
					//被关注者粉丝数减一
					String sql3 = "update user_info set fan = fan -1 where userId = ?";
					PreparedStatement st3 = conn.prepareStatement(sql3);
					st3.setString(1, userId);
					flag = st3.executeUpdate();
				}
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "关闭数据库失败" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
	}
	public int dofollow(String userId,String followId) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			String sql = "insert into follow_info values(?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, userId);
			st.setString(2, followId);
			flag = st.executeUpdate();
			if(flag == 1) {
				String sql2 = "update user_info set follow = follow + 1 where userId = ?";
				PreparedStatement st2 = conn.prepareStatement(sql2);
				st2.setString(1, userId);
				flag = st2.executeUpdate();
				if(flag == 1) {
					//被关注者粉丝数加一
					String sql3 = "update user_info set fan = fan + 1 where userId = ?";
					PreparedStatement st3 = conn.prepareStatement(sql3);
					st3.setString(1, userId);
					flag = st3.executeUpdate();
				}
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "关闭数据库失败" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
	}
	public int gettype(String userId, String other) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			int gz = 0;//关注
			int bgz = 0;//被关注
			String sql0 = "select * from follow_info where userId = ? and followId = ";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			st0.setString(1, userId);
			st0.setString(2, other);
			ResultSet rs0 = st0.executeQuery();
			if(rs0.next()) {
				gz = 1;
			}
			String sql1 = "select * from follow_info where userId = ? and followId = ?";
			PreparedStatement st1 = conn.prepareStatement(sql1);
			st1.setString(1, other);
			st1.setString(2, userId);
			ResultSet rs1=st1.executeQuery();
			
			if(rs1.next()) {
				bgz = 1;
			}
			if(gz == 1 && bgz == 1)
				flag = 0;
			else if(gz == 1)
				flag = -1;
			else 
				flag = -2;

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "鍏抽棴杩炴帴澶辫触" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
	}
	public List<Info> fan(String id){
		Connection conn = DBConn.getConnection();
		int flag=0;
		List<Info> data = 
				new ArrayList<Info>();
		try {
			String sql0 = "select * from user_info where userId = ?";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			st0.setString(1, id);
			ResultSet rs0 = st0.executeQuery();
			Info info1 = new Info();
			if(rs0.next()) {
				info1.set_idnumber(rs0.getString(1));
				info1.set_name(rs0.getString(2));
				info1.set_imghead(rs0.getString(4));
				info1.setFan(rs0.getString(5));
				info1.setFollow(rs0.getString(6));
				info1.setType(0);
				data.add(info1);
				
			}
			String sql1 = "select followId from follow_info where userId = ?";
			PreparedStatement st1 = conn.prepareStatement(sql1);
			st1.setString(1, id);
			ResultSet rs1=st1.executeQuery();
			rs1.last();
			int n = rs1.getRow();
			String []mf = new String [n];
			rs1.beforeFirst();
			int i = 0,j;
			System.out.println(n);
			while(rs1.next()) {
				mf[i++] = rs1.getString(1);
			}
			String sql = "select user_info.userId, name, headimage, fan, follow" + 
					" from user_info,follow_info "+
					" where user_info.userId = follow_info.userId and follow_info.followId = ?";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, id);
				ResultSet rs = st.executeQuery();
				
				while (rs.next()){
				
					String userId = rs.getString("user_info.userId");
					
					String imgId = rs.getString( "headimage" );
					String name = rs.getString( "name" );		
					String fan = rs.getString( "fan" );
					String follow= rs.getString( "follow" );
					if (!userId.equals(id)) {
						Info info = new Info();
						info.set_idnumber(userId);
						info.set_imghead(imgId);
						info.set_name(name);
						info.setFan(fan);
						info.setFollow(follow);
						info.setType(-2);
						
						/*st1 = conn.prepareStatement(sql1);
						st1.setString(1, userId);
						st1.setString(2, id);
						rs1 = st.executeQuery();
						if(rs1.next())
							info.setType(0);*/
						for(j = 0; j < i; j++) {
							if(userId.equals(mf[j])) {
								info.setType(-1);
								break;
							}
						}
						data.add( info );
						
					}
				}
			

			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "鍏抽棴杩炴帴澶辫触" + e.getMessage()  );
				}// try
			}// if
		}
		return data;
	}
	
	public int doLike(Info info) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			String sql00 = "select * from like_info where userId = ? and articleId = ?";
			PreparedStatement st00 = conn.prepareStatement(sql00);
			st00.setString(1, info.get_idnumber());
			st00.setInt(2, info.getArticleId());
			ResultSet rs0 = st00.executeQuery();

			//找的到  说明已经点过赞 则要取消赞
			if(rs0.next()) {
				String sql1 = "delete from like_info where userId = ? and articleId = ? ";
				PreparedStatement st1 = conn.prepareStatement(sql1);
				st1.setString(1, info.get_idnumber());
				st1.setInt(2, info.getArticleId());
				flag = st1.executeUpdate();
				if(flag == 1) {
					String sql2 = "update major_info set kudo = kudo-1 where articleId = ? ";
					PreparedStatement st2 = conn.prepareStatement(sql2);
					st2.setInt(1, info.getArticleId());
					flag = st2.executeUpdate();
				}
			}
			//未点过赞  要点赞
			else {
				String sql1 = "insert into like_info values(?,?)";
				PreparedStatement st1 = conn.prepareStatement(sql1);
				st1.setString(1, info.get_idnumber());
				st1.setInt(2, info.getArticleId());
				flag = st1.executeUpdate();
				if(flag == 1) {
					String sql2 = "update major_info set kudo = kudo +1 where articleId = ? ";
					PreparedStatement st2 = conn.prepareStatement(sql2);
					st2.setInt(1, info.getArticleId());
					flag = st2.executeUpdate();
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "鍏抽棴杩炴帴澶辫触" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
	}
	public int repost(Info info) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {
			String sql = "select * from major_info where articleId = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, info.getArticleId());
			
			ResultSet rs = st.executeQuery();
			System.out.println(info.getArticleId());
			if(!rs.next()) {
				flag = -1;//原文已被删除
			}
			else {
				String sql0 = "select max(articleId) from major_info";
				PreparedStatement st0 = conn.prepareStatement(sql0);
				ResultSet rs0 = st0.executeQuery();
			
				int articleId = 0;
				if(rs0.next()) {
					articleId = rs0.getInt(1);
				}
				articleId++;
				System.out.println(info.get_content());
				String sql1 = " insert into major_info "
						+ "  values(?,?,?,?,'0','0','0',?,?) ";
				PreparedStatement st1 = conn.prepareStatement(sql1);
				int i = 1;

				st1.setInt( i, articleId);
				++i;
				st1.setString(i, info.get_idnumber());
				++i;
				st1.setString(i, info.get_content());
				++i;
				st1.setString(i, info.get_wordTime());
				++i;
				st1.setInt(i,info.getArticleId());
				++i;
				st1.setString(i,info.getPic());
			
				System.out.printf(info.getPic() + "sql = %s\n", st1.toString());
				
				flag = st1.executeUpdate();
				
				if(flag == 1) {
					System.out.println("asfnlafafnaksnf");
					String sql2 = "update major_info set repost = repost +1 where articleId = ?";
					PreparedStatement st2 = conn.prepareStatement(sql2);
					st2.setInt(1, info.getArticleId());
					flag = st2.executeUpdate();
				}
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "关闭数据库失败" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
	}
	public List<Info> getmyAll(String id){
		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			//String sql = "select * from major_info where _idnumber in (select id from follow where userId = ?) or _idnumber = ? order by _twiid DESC";
			/*String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,"
			+ "major_info.cmt,major_info.repost,major_info.like,major_info.exist,major_info.origin,"
			+ "user_info.headimage,user_info.name from major_info,user_info where major_info.userId = user_info.userId and userId in (select followId from follow_info where userId = ?) "
			+ "or userId = ? order by major_info.articleId DESC";*/
			String sql0 = "select * from user_info where userId = ?";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			st0.setString(1, id);
			ResultSet rs0 = st0.executeQuery();
			Info info1 = new Info();
			if(rs0.next()) {
				info1.set_idnumber(rs0.getString(1));
				info1.set_name(rs0.getString(2));
				info1.set_imghead(rs0.getString(4));
				info1.setFan(rs0.getString(5));
				info1.setFollow(rs0.getString(6));
				info1.setType(-3);
				data.add(info1);
				
			}
			

			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt," + 
					"major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name, pic from major_info,user_info" + 
					" where major_info.userId = user_info.userId and major_info.userId in (select followId from follow_info where userId = ?)"
					+ "order by articleId DESC ";
			PreparedStatement st = 
					conn.prepareStatement(sql);
			st.setString(1,id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int flag;
			
			while( rs.next() ){
				//鏈汉鐨勭殑鍐呭 鐩稿悓
				int articleId = rs.getInt(1);
				String _idnumber = rs.getString(2);
				String _content = rs.getString(3);
				String _wordTime = rs.getString(4);
				String _ctm = rs.getString(5);
				String _tn = rs.getString(6);
				String _lk = rs.getString(7);
				//绫诲瀷   鏄惁鏄浆鍙戠殑 0鏄師鍒� 涓嶆槸0鍒欎负杞彂鐨勬帹鏂囩殑id
			    int type = rs.getInt(8);
				String _imghead = rs.getString(9);
				String _name = rs.getString(10);
				String pic = rs.getString(11);
				//鏃堕棿杞崲
				flag = cmpTime(_wordTime);
				if(flag == 0)
					_wordTime = _wordTime.substring(12, 17);
				if(flag == 1)
					_wordTime = _wordTime.substring(5, 17);
				else if(flag == 2)
					_wordTime = _wordTime.substring(0, 17);
				if(type == 0) {
					//System.out.println(_wordTime);
					
					if (_idnumber.equals(id)) {
						Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk);
						if(!pic.equals("noPic")) {
							info.setPic(pic);
							type = -4;
						}
						info.setType(type);
						info.setArticleId(articleId);
						data.add( info );
					}
					
				}
				else {
					//杞彂鍒汉鐨勫唴瀹�
				    //鍚嶅瓧2
				     String _name2;
				    //璐﹀彿2
				     String _idnumber2;
				    //鍙戣█鏃堕棿2
				     String _wordTime2;
				    //鍐呭2
				     String _content2;
				     String sql1 = "select major_info.userId,user_info.name,major_info.content,major_info.time" + 
								" from major_info,user_info" + 
								" where major_info.userId = user_info.userId and major_info.articleId = ?";
				     PreparedStatement st1 = conn.prepareStatement(sql1);
				     st1.setInt(1, type);
				     ResultSet rs1 = st1.executeQuery();
				     if(rs1.next()) {
				    	 _idnumber2 = rs1.getString(1);
				    	 _name2 = rs1.getString(2);
				    	 _content2 = rs1.getString(3);
				    	 _wordTime2 = rs1.getString(4);
				    	 flag = cmpTime(_wordTime2);
				    	 if(flag == 0)
							_wordTime2 = _wordTime2.substring(12, 17);
				    	 if(flag == 1)
							_wordTime2 = _wordTime2.substring(5, 17);
				    	 else if(flag == 2)
							_wordTime2 = _wordTime2.substring(0, 17);
				    	 if (_idnumber.equals(id)) {
				    		 Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
					                  _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
					    	 info.setType(type);
					    	 info.setArticleId(articleId);
					    	 data.add(info);
				    	 }
				    	 
				     }
				     else {
				    	 type = -1;
				    	 if (_idnumber.equals(id)) {
				    		 Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
					                  _ctm,  _tn,  _lk);
							info.setType(type);
							info.setArticleId(articleId);
							data.add(info);
				    	 }
				    	 
				     }
				}				
			}
		
			
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
	}
	public int updateimage(String userId, String headimage ){
		Connection conn = DBConn.getConnection();
		int count = 0;
		
		try{
			if(headimage == null){
				count = 0;
			}
			else {
				String sql = " update user_info "
						+ "  set headimage = ? "
						+ "  where userId = ?  ";
				PreparedStatement st = conn.prepareStatement(sql );
				int i = 1;
				st.setString(i, headimage);
				++i;
				st.setString(i, userId );
				++i;
				
				System.out.printf("sql = %s\n", st.toString());
				
				count = st.executeUpdate();
				System.out.printf( "change%d", count );
			}
			
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "add failed:" + e.getMessage() );
			
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}// try
			}// if
			
		}// finally
		
		return count;
	}
	public boolean updatename(String userId, String name ){
		Connection conn = DBConn.getConnection();
		int count = -1;
		
		try{
			String sql = " update user_info "
					+ "  set name = ? "
					+ "  where userId = ?  ";
			PreparedStatement st = conn.prepareStatement(sql );
			int i = 1;
			st.setString(i, name);
			++i;
			st.setString(i, userId );
			++i;
			
			
			System.out.printf("sql = %s\n", st.toString());
			
			count = st.executeUpdate();
			System.out.printf( "change%d", count );
		}
		catch( SQLException e ){
			System.out.printf( "add failed:" + e.getMessage() );
			return false;
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}// try
			}// if
			
		}// finally
		
		return true;
	}
	public List<String> getHistory(String id){
		
		List<String> data = 
				new ArrayList<String>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			
			String sql1 = "select count(*) from history_info where userId = ? order by num DESC";
			System.out.println(id);
			PreparedStatement st1 = conn.prepareStatement(sql1);
			
			st1.setString(1, id);
			
			ResultSet rs1 = st1.executeQuery();
			
			int n = 0;
			if(rs1.next())
				n = rs1.getInt(1);
			
			String sql = "select history_info.content from history_info where userId = ? order by num DESC";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,  id);
			ResultSet rs = st.executeQuery();
			
			int i = 5;
			String [] s = new String[5];
			if (n < i) {
				while(rs.next()) {
					String _content = rs.getString(1);		
					n--;
					s[n] = _content;
					System.out.println(s[n]);
					data.add(s[n]);
				}
			}
			else {
				while (rs.next() && i > 0) {
					String _content = rs.getString(1);		
					i--;
					s[i] = _content;
					System.out.println(s[i]);
					data.add(s[i]);
				}
			}
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
		
	}
	
	public int addHistroy(String id, String searchText) {
		Connection conn = DBConn.getConnection();
		int flag=0;
		try {

			System.out.printf(id+searchText);
			//要先获取表中的articleId的最大值 然后加一 作为当前推文的id1
			String sql0 = "select max(num) from history_info";
			PreparedStatement st0 = conn.prepareStatement(sql0);
			ResultSet rs = st0.executeQuery();
		
			int num = 0;
			if(rs.next()) {
				num = rs.getInt(1);
			}
			num++;
			String sql = " insert into history_info "
					+ "  values(?,?,?) ";
			PreparedStatement st = conn.prepareStatement( sql);
			int i = 1;

			st.setString( i, id);
			++i;
			st.setInt(i, num);
			++i;
			st.setString(i, searchText);
			++i;
		
			System.out.printf("sql = %s\n", st.toString());
			
			flag = st.executeUpdate(  );
			System.out.printf( "添加了%d条记录", flag );
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "关闭连接失败" + e.getMessage()  );
				}// try
			}// if
		}
		return flag;
	}
	public List<Info> getHot(){
		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			
			String sql11 = "select count(*) from major_info";
		//	System.out.println(id);
			PreparedStatement st11 = conn.prepareStatement(sql11);
			
		//	st1.setString(1, id);
			
			ResultSet rs11 = st11.executeQuery();
			
			int n = 0;
			if(rs11.next())
				n = rs11.getInt(1);
			
		//	String sql = "select articleId from major_info order by like DESC";
			/*
			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt," + 
					"major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name from major_info,user_info" + 
					" where major_info.userId = user_info.userId and major_info.userId order by major_info.kudo DESC ";*/
			String sql = "select major_info.articleId,major_info.userId,"
					+ "major_info.content,major_info.time,major_info.cmt,"
					+ "major_info.repost,major_info.kudo,major_info.origin,"
					+ "user_info.headimage,user_info.name, pic from major_info,user_info "
					+ "where major_info.userId = user_info.userId order by cast(major_info.kudo as UNSIGNED INTEGER) DESC";
			PreparedStatement st = conn.prepareStatement(sql);
		//	st.setString(1,  id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int i = 4, flag;
			
			while( rs.next() && i >= 0 && n >= 0){
				i--;
				n--;
				//本人的的内容 相同
				int articleId = rs.getInt(1);
				String _idnumber = rs.getString(2);
				String _content = rs.getString(3);
				String _wordTime = rs.getString(4);
				String _ctm = rs.getString(5);
				String _tn = rs.getString(6);
				String _lk = rs.getString(7);
				//类型   是否是转发的 0是原创 不是0则为转发的推文的id
			    int type = rs.getInt(8);
				String _imghead = rs.getString(9);
				String _name = rs.getString(10);
				String pic = rs.getString(11);
				//时间转换
				flag = cmpTime(_wordTime);
				if(flag == 0)
					_wordTime = _wordTime.substring(12, 17);
				if(flag == 1)
					_wordTime = _wordTime.substring(5, 17);
				else if(flag == 2)
					_wordTime = _wordTime.substring(0, 17);
				if(type == 0) {
					//System.out.println(_wordTime);

					Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
			                  _ctm,  _tn,  _lk);
					if(!pic.equals("noPic")) {
						info.setPic(pic);
						type = -4;
					}
					info.setType(type);
					info.setArticleId(articleId);
					data.add( info );
				}
				
				}				
			
		
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
		
	}
	public List<Info> getYH(String id){
		
		//ID为所获取的content searchText

		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
			
			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt,"
					+ "major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name "
					+ "from major_info,user_info where major_info.userId = user_info.userId order by articleId DESC";
			
			PreparedStatement st = 
					conn.prepareStatement(sql);
		//	st.setString(1,id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int flag;
			int n1, n2;
			
			while( rs.next() ){
				//本人的的内容 相同
				int articleId = rs.getInt(1);
				String _idnumber = rs.getString(2);
				String _content = rs.getString(3);
				String _wordTime = rs.getString(4);
				String _ctm = rs.getString(5);
				String _tn = rs.getString(6);
				String _lk = rs.getString(7);
				//类型   是否是转发的 0是原创 不是0则为转发的推文的id
			    int type = rs.getInt(8);
				String _imghead = rs.getString(9);
				String _name = rs.getString(10);
				
				n1 = _name.indexOf(id);
				
			//	System.out.println( "result45245:???");
				
				//时间转换
				flag = cmpTime(_wordTime);
				if(flag == 0)
					_wordTime = _wordTime.substring(12, 17);
				if(flag == 1)
					_wordTime = _wordTime.substring(5, 17);
				else if(flag == 2)
					_wordTime = _wordTime.substring(0, 17);
				
			
				if(type == 0) {
					//System.out.println(_wordTime);
			//		System.out.println(n1);
					if (n1 >= 0) {
					Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
			                  _ctm,  _tn,  _lk);
					info.setType(type);
					info.setArticleId(articleId);
					data.add( info );
					}
				}	
				else {
	
					//转发别人的内容
				    //名字2
				     String _name2;
				    //账号2
				     String _idnumber2;
				    //发言时间2
				     String _wordTime2;
				    //内容2
				     String _content2;
				     String sql1 = "select major_info.userId,user_info.name,major_info.content,major_info.time" + 
								" from major_info,user_info" + 
								" where major_info.userId = user_info.userId";
				     PreparedStatement st1 = conn.prepareStatement(sql1);
				 //    st1.setInt(1, type);
				     ResultSet rs1 = st1.executeQuery();
				     if(rs1.next()) {
				    	 _idnumber2 = rs1.getString(1);
				    	 _name2 = rs1.getString(2);
				    	 _content2 = rs1.getString(3);
				    	 _wordTime2 = rs1.getString(4);
				    	 flag = cmpTime(_wordTime2);
				    	 n2 = _name2.indexOf(id);
				    	 if(flag == 0)
							_wordTime2 = _wordTime2.substring(12, 17);
				    	 if(flag == 1)
							_wordTime2 = _wordTime2.substring(5, 17);
				    	 else if(flag == 2)
							_wordTime2 = _wordTime2.substring(0, 17);
				    	 
						if (n2 >= 0)	{
				    	 Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
				    	 info.setType(type);
				    	 info.setArticleId(articleId);
				    	 data.add(info);
						}
				     }
				     /*
				     else {
				    	 type = -1;
				    	 if (n2 > 0) {
					    	 Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
					                  _ctm,  _tn,  _lk);
							info.setType(type);
							info.setArticleId(articleId);
							data.add(info);
				    	 }
				     }*/
				}				
			}
		
			
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
		
	}
	public List<Info> getRM(String id){
		
		//ID为所获取的content searchText

		List<Info> data = 
				new ArrayList<Info>();
		
		Connection conn = DBConn.getConnection(); 
		try{
/*
			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt," + 
					"major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name from major_info,user_info" + 
					" where major_info.userId = user_info.userId and major_info.userId in (select followId from follow_info where userId = ?)"
					+ "order by articleId DESC ";
			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt,"
					+ "major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name from major_info,user_info"
					+ "where major_info.userId = user_info.userId order by articleId DESC";*/
			
			
			String sql = "select major_info.articleId,major_info.userId,major_info.content,major_info.time,major_info.cmt,"
					+ "major_info.repost,major_info.kudo,major_info.origin,user_info.headimage,user_info.name "
					+ "from major_info,user_info where major_info.userId = user_info.userId order by articleId DESC";
			
			PreparedStatement st = 
					conn.prepareStatement(sql);
		//	st.setString(1,id);
			ResultSet rs = st.executeQuery();
			System.out.println( "result45245:");
			int flag;
			int n1, n2;
			
			while( rs.next() ){
				//本人的的内容 相同
				int articleId = rs.getInt(1);
				String _idnumber = rs.getString(2);
				String _content = rs.getString(3);
				String _wordTime = rs.getString(4);
				String _ctm = rs.getString(5);
				String _tn = rs.getString(6);
				String _lk = rs.getString(7);
				//类型   是否是转发的 0是原创 不是0则为转发的推文的id
			    int type = rs.getInt(8);
				String _imghead = rs.getString(9);
				String _name = rs.getString(10);
				
				n1 = _content.indexOf(id);
				
			//	System.out.println( "result45245:???");
				
				//时间转换
				flag = cmpTime(_wordTime);
				if(flag == 0)
					_wordTime = _wordTime.substring(12, 17);
				if(flag == 1)
					_wordTime = _wordTime.substring(5, 17);
				else if(flag == 2)
					_wordTime = _wordTime.substring(0, 17);
				
			
				if(type == 0) {
					//System.out.println(_wordTime);
			//		System.out.println(n1);
					if (n1 >= 0) {
					Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
			                  _ctm,  _tn,  _lk);
					info.setType(type);
					info.setArticleId(articleId);
					data.add( info );
					}
				}	
				else {
	
					//转发别人的内容
				    //名字2
				     String _name2;
				    //账号2
				     String _idnumber2;
				    //发言时间2
				     String _wordTime2;
				    //内容2
				     String _content2;
				     String sql1 = "select major_info.userId,user_info.name,major_info.content,major_info.time" + 
								" from major_info,user_info" + 
								" where major_info.userId = user_info.userId";
				     PreparedStatement st1 = conn.prepareStatement(sql1);
				 //    st1.setInt(1, type);
				     ResultSet rs1 = st1.executeQuery();
				     if(rs1.next()) {
				    	 _idnumber2 = rs1.getString(1);
				    	 _name2 = rs1.getString(2);
				    	 _content2 = rs1.getString(3);
				    	 _wordTime2 = rs1.getString(4);
				    	 flag = cmpTime(_wordTime2);
				    	 n2 = _content2.indexOf(id);
				    	 if(flag == 0)
							_wordTime2 = _wordTime2.substring(12, 17);
				    	 if(flag == 1)
							_wordTime2 = _wordTime2.substring(5, 17);
				    	 else if(flag == 2)
							_wordTime2 = _wordTime2.substring(0, 17);
				    	 
						if (n2 >= 0)	{
				    	 Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
				                  _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
				    	 info.setType(type);
				    	 info.setArticleId(articleId);
				    	 data.add(info);
						}
				     }
				     /*
				     else {
				    	 type = -1;
				    	 if (n2 > 0) {
					    	 Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
					                  _ctm,  _tn,  _lk);
							info.setType(type);
							info.setArticleId(articleId);
							data.add(info);
				    	 }
				     }*/
				}				
			}
		
			
		}
		catch( SQLException e ){
			e.printStackTrace();
			System.out.printf( "failed\n" + e.getMessage()  );
		}
		finally{
			if( conn != null ){
				try{
					conn.close();
				}
				catch( SQLException e ){
					System.out.printf( "close failed\n" + e.getMessage()  );
				}
			}
			
		}

		return data;
		
	}
	
}
