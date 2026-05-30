package com.erguidos.ichor.enums;
 
public enum OrganType {
	LEFT_LUNG(6), RIGHT_LUNG(6), LEFT_KIDNEY(36), RIGHT_KIDNEY(36), LIVER(12), PANCREAS(15), HEART(4), INTESTINES(8);
	
	private int lifeTime;
	private OrganType(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	public int getTime() {
		return this.lifeTime;
	}
}