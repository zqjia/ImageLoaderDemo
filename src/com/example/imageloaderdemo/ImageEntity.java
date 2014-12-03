package com.example.imageloaderdemo;

public class ImageEntity {

	private String imagePath;
	private String imageName;
	private int imageSize;
	
	public ImageEntity() {
		
	}
	
	public ImageEntity(String path, String name, int size) {
		this.imagePath = path;
		this.imageName = name;
		this.imageSize = size;
	} 
	
	public void setPath(String path) {
		this.imagePath = path;
	}
	
	public void setName(String name) {
		this.imageName = name;
	}
	
	public void setSize(int size) {
		this.imageSize = size;
	}
	
	
	public String getPath() {
		return this.imagePath;
	}
	
	public String getName() {
		return this.imageName;
	}
	
	public int getSize() {
		return this.imageSize;
	}
}
