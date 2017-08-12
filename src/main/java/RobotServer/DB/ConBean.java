package RobotServer.DB;

public class ConBean {
	private String con;
	private String u_name;
	//测试
	private String u_pass;
	
	public void setCon(String con){
		this.con = con;
	} 

	public void setU_name(String u_name){
		this.u_name = u_name;
	}

	public void setU_pass(String u_pass){
		this.u_pass = u_pass;
	}

	public String getCon(){
		return con;
	}

	public String getU_name(){
	
		return u_name;
		//5656
	}

	public String getU_pass(){
		return u_pass;
	}

}
