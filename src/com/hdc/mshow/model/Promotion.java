package com.hdc.mshow.model;

import android.graphics.Bitmap;

public class Promotion {
	public String link;
	public String src;
	public Bitmap img;

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
