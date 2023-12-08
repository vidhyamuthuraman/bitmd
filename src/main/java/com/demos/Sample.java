package com.demos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Sample {
	public static String driver="com.mysql.cj.jdbc.Driver";
	public static String url="jdbc:mysql://a2nlmysql19plsk.secureserver.net:3306/bitdatadump";
	public static String username="bitstream";
	public static String password="Trebo@123";
		
		
		

	public static void main(String args[])
	{
		Connection conn=null;
		
		try
		{
			Class.forName(driver);
			conn=DriverManager.getConnection(url,username,password);
			Statement st=conn.createStatement();
			System.out.println("connection established");
			/*String query="select * from client";
			ResultSet rs=st.executeQuery(query);
			
			
			while(rs.next())
			{
				System.out.println(rs.getString(1)+ " " +rs.getString(2));
			}*/
			conn.close();
		}
		catch(SQLException e)
		{System.out.println("error");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public static String driver="com.mysql.cj.jdbc.Driver";
public static String url="jdbc:mysql://localhost:3306/learning ";
public static String username="root";
public static String password="learnmysql";
	
	
	

public static void main(String args[])
{
	Connection conn=null;
	
	try
	{
		Class.forName(driver);
		conn=DriverManager.getConnection(url,username,password);
		Statement st=conn.createStatement();
		String query="select * from client";
		ResultSet rs=st.executeQuery(query);
		
		while(rs.next())
		{
			System.out.println(rs.getString(1)+ " " +rs.getString(2));
		}
		conn.close();
	}
	catch(SQLException e)
	{System.out.println("error");
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}*/
	
	
	

}
