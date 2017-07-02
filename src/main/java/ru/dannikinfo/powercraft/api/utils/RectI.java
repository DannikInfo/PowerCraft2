package ru.dannikinfo.powercraft.api.utils;

public class RectI {
	public int x, y, width, height;
	
	public RectI() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}
	
	public RectI(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public RectI averageQuantity(RectI rect) {
		RectI fin = new RectI();
		int v1, v2;
		
		if (x > rect.x) {
			fin.x = x;
		} else {
			fin.x = rect.x;
		}
		
		if (y > rect.y) {
			fin.y = y;
		} else {
			fin.y = rect.y;
		}
		
		v1 = x + width;
		v2 = rect.x + rect.width;
		
		if (v1 > v2) {
			fin.width = v2 - fin.x;
		} else {
			fin.width = v1 - fin.x;
		}
		
		v1 = y + height;
		v2 = rect.y + rect.height;
		
		if (v1 > v2) {
			fin.height = v2 - fin.y;
		} else {
			fin.height = v1 - fin.y;
		}
		
		return fin;
	}
	
	public RectI copy() {
		return new RectI(x, y, width, height);
	}
}
