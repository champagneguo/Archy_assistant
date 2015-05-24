package com.archy_assistant_software;

import java.io.Serializable;

public class BundleObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String pkgName;// 应用程序的包名；
	private String activityName; //应用程序的主activity入口；
	
	public BundleObject() {}
	
	public String getPkgName() {
		return pkgName;
	}
	
	public void setPkgName(String PkgName) {
		this.pkgName = PkgName;
	}
	
	public void setActivityName(String ActivityName) {
		this.activityName = ActivityName;
	}
	
	public String getActivityName() {
		return activityName;
	}

}
