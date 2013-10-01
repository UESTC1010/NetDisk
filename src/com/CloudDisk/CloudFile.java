package com.CloudDisk;

import java.io.File;
import java.util.Set;

public class CloudFile {
	static Set<CloudFile> fileSet;

	File filename;
	long lastModify;
	boolean flag = false;//删除标识
	long deleteTime;
	int m = 3;// 基本参数的默认值
	int n = 5;// 3、5意味着对每个文件编码成5块、其中任意3块可以完整的恢复出原来的文件
	CloudDisk[] location;
	boolean[] consistency;// 一致性。由于upload时有的clouddisk可能会offline，造成该盘上的块与其他盘的不一致
	String hash;// 文件的哈希值
	String[] blockHash;// 文件各个编码后分块的哈希值

	public CloudFile(File filename) {
		this.filename = filename;
		this.lastModify = this.filename.lastModified();
		location = new CloudDisk[n];
		consistency = new boolean [n];
		blockHash = new String[n];
	}

	public CloudFile(File filename, int m, int n) {
		this.filename = filename;
		this.m = m;
		this.n = n;
		location = new CloudDisk[n];
		consistency = new boolean [n];
		blockHash = new String[n];
	}

	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public void setDeleteTime(){
		
	}
	
	public void setLocation(CloudDisk location, int i) {
		// 通过这个方法可以设置第i个文件块的location
		this.location[i] = location;
	}

	public void autoSetLocation(CloudDisk location) {
		// 通过这个方法可以自动设置文件块的location
		// 主要参考云盘的QOS属性
	}

	public boolean upload() {
		// 首先对该文件编码成n个块，然后分别上传到location的各个云盘中。
		// 注意，不要在这里直接上传，不然很难以管理
		// 设计一个NetAgent，在里面完全所有的上传和下载工作
		// 在这里只是产生agent的上传任务即可
		// 如果agent任务太多，则建立上传任务失败，返回false
		FileCoder upFile = new FileCoder();
		upFile.m = this.m;
		upFile.n = this.n;
		File[] upfileblocks = new File[n];
		upfileblocks = upFile.Encode(filename);
		
		
		return true;
	}

	public boolean download() {
		// 下载并解码
		// 注意，不要在这里直接下载，不然很难以管理
		// 设计一个NetAgent，在里面完全所有的上传和下载工作
		// 在这里只是产生agent的下载任务即可
		// 如果agent任务太多，则建立下载任务失败，返回false
		// 采用异步调用，NetAgent下载完成后唤醒本线程，完成解码
		NetworkTask downloadFile = new NetworkTask();
		downloadFile.localFile = this.filename;
		
		return true;
	}

	public boolean fullCheck() {
		// 检测所有location的分块是否正确
		for(int i = 0;i < this.n; i ++){
			if(location[i].available() != true)
				return false;
		}
		return true;
	}

	public boolean availableCheck() {
		// 检测是否有足够的location上的分块
		// 区别：所有的n个分块都正确可用，fullCheck返回true
		// 只要有m个分块正确可用，availableCheck返回true
		int sum = 0;
		for (int i = 0;i < this.n;i ++){
			if(location[i].available() == true){
				sum++;
			}
		}
		if (sum < this.m)
			return false;
		return true;
	}

	public boolean availableCheck(int i) {
		// 测试第i块是否可用;
		if(location[i].available() != true)
			return false;
		return true;
	}

	public boolean consistencyMaintain() {
		// 消除不一致性；
		return true;
	}

	public void callback() {
		// 用于NetworkAgent进行返回调用
		// 具体的自己设计
	}

}
