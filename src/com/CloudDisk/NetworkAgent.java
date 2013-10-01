package com.CloudDisk;

import java.io.File;
import java.util.Queue;

public class NetworkAgent implements Runnable {
	Queue<NetworkTask> uploadQueue;
	Queue<NetworkTask> downloadQueue;
	int maxConnection;

	public boolean upload() {
		while(uploadQueue.peek() != null){
			NetworkTask uploadFile = uploadQueue.poll();
		   if(uploadFile.disk.uploadFile()){
			   
		   }
		
		}
		return false;
	}

	public boolean download() {
		while(downloadQueue.peek() != null){
			NetworkTask downloadFile = downloadQueue.poll();
			if(downloadFile.disk.downloadFile()){
				
			}
		}
		return false;
	}

	public void run() {
		// 执行上面的两个队列
	}
}

class NetworkTask {
	String taskType;// upload or download
	// if upload, localFile >> remoteFile@disk
	// else remoteFile@disk >> localFile
	File localFile;
	String remoteFile;
	CloudDisk disk;
	CloudFile file;// 用于callback()
}