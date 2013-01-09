package com.hdc.mshow.model;

import android.graphics.Bitmap;

public class Album {
	public String id;
	public String title;
	public String total_image;
	public String hit;
	public String src;
	public Bitmap img;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTotal_image() {
		return total_image;
	}

	public void setTotal_image(String total_image) {
		this.total_image = total_image;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}
}
