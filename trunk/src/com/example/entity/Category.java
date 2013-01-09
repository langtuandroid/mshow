package com.example.entity;

import java.io.Serializable;

public class Category implements Serializable {
	Integer id;
	String namecate;
	String url;
	Integer countView;
	Integer countDown;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNamecate() {
		return namecate;
	}

	public void setNamecate(String namecate) {
		this.namecate = namecate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCountView() {
		return countView;
	}

	public void setCountView(Integer countView) {
		this.countView = countView;
	}

	public Integer getCountDown() {
		return countDown;
	}

	public void setCountDown(Integer countDown) {
		this.countDown = countDown;
	}

}
