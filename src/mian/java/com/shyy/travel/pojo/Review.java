package com.shyy.travel.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="review")
public class Review implements Serializable{

	@Id
	private String id;//


	
	private String name;//
	private String comment;//
	private java.util.Date uptime;//

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {		
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public java.util.Date getUptime() {		
		return uptime;
	}
	public void setUptime(java.util.Date uptime) {
		this.uptime = uptime;
	}


	
}
