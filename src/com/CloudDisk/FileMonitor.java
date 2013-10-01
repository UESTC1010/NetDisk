package com.CloudDisk;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * 监视本地文件夹的变化，
 */
public class FileMonitor implements Runnable {

	File localDir;// 本地文件夹
	CloudDisk fileInfoDisk;

	public FileMonitor(File localDir, CloudDisk fileInfoDisk){
		this.localDir = localDir;
		this.fileInfoDisk = fileInfoDisk;
	}
	
	/**
	 * 获取本地和异地的文件列表信息，进行对比，产生对应上传下载策略
	 */
	public void scan() {
		scanDir(this.localDir);
		Set<CloudFile> localSet;//本地文件列表信息
		Set<CloudFile> remoteSet;//异地文件列表信息
		File localFileInfo = new File("FileInfo");
		Gson gson = new Gson();
		
		//下载服务器端文件信息列表
	    if(fileInfoDisk.downloadFile("remoteFileInfo")){
	    	try{
	    		//将服务器端文件列表信息由json转换成Set
				FileReader fr1 = new FileReader(remoteFileInfo);
				localSet = gson.fromJson(fr1,new TypeToken<Set<CloudFile>>(){}.getType());//将json转换成java对象
				fr1.close();
			}catch(IOException a){
				a.printStackTrace();
			}
	    }
		
		//检测本地文件信息列表存在
		if(!localFileInfo.canExecute()){//本地文件信息不存在，即建立新的文件
			try{
				localFileInfo.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				scanDir(localDir);//扫描文件夹
			}
			fileSetCompare(CloudFile.fileSet,remoteSet);
		}else{
			
			//本地文件信息列表存在则读取FileInfo文件获得localSet
			try{
				FileReader fr = new FileReader(localFileInfo);
				localSet = gson.fromJson(fr,new TypeToken<Set<CloudFile>>(){}.getType());//将json转换成java对象
				fr.close();
			}catch(IOException a){
				a.printStackTrace();
			}
			//比较本地文件信息和扫描的文件信息
			//本地新建文件：无影响
			//本地修改文件：若修改了文件名以及文件内容，则当本地文件被删除后又新建了一个文件
			//本地删除文件：要与服务器新建文件区别
			for(Iterator<CloudFile> localIt = localSet.iterator();localIt.hasNext();){
				CloudFile localFile = localIt.next();
				boolean flag = false;
				for(Iterator<CloudFile> newIt = CloudFile.fileSet.iterator();newIt.hasNext();){
					CloudFile newFile = newIt.next();
					if(localFile.filename == newFile.filename)
						flag = true;
				}
				if(flag == false){//表示locaFile已经在本地被删除
					localFile.setFlag(true);//将此文件标识为被删除
					//CloudFile.fileSet.add(localFile);
					
				}
			}
			
			fileSetCompare(CloudFile.fileSet,remoteSet);
		}
		

		//将新的文件信息转换为json写入文件FileInfo
		scanDir(localDir);
		String fileInf = gson.toJson(CloudFile.fileSet);
		try{
			FileWriter f = new FileWriter(localFileInfo);
			f.write(fileInf);
			f.close();
		}catch(IOException ea){
			ea.printStackTrace();
		}
	}

	/**
	 * 扫描本地文件夹，将本地文件信息存入CloudFile.fileSet中
	 * @param dir
	 */
	
	public void scanDir(File dir){
		File[] files = dir.listFiles();
		//CloudFile.fileSet = new HashSet<CloudFile>();
		for(int x = 0;x < files.length;x ++){
			if(files[x].isDirectory())//若是文件夹则递归调用scanDir
				scanDir(files[x]);
			else{
				CloudFile.fileSet.add(new CloudFile(files[x]));
			}
		}
	}
	
	/**
	 * 本地文件列表和服务器文件列表进行对比，确定处理策略
	 * 问题：本地删除文件和服务器新建文件需下载解决；
	 * 问题：异地删除了文件和本地需上传的冲突解决
	 * @param localSet
	 * @param remoteSet
	 */
	public void fileSetCompare(Set<CloudFile> localSet,Set<CloudFile> remoteSet){
		//对比本地文件列表和新的文件列表，得到文件变动信息
		for(Iterator<CloudFile> remoteIt = remoteSet.iterator();remoteIt.hasNext();){//遍历异地文件信息列表查找到与本地列表不同的表项
			CloudFile remoteNext = remoteIt.next();
			boolean comFlag = false;
			for(Iterator<CloudFile> localIt = localSet.iterator();localIt.hasNext();){
				CloudFile localNext = localIt.next();
				//if(remoteNext.filename.compareTo(localNext.filename) == 0){
				if(remoteNext.filename.getName().equals(localNext.filename.getName())){
					if(localNext.flag && (!remoteNext.flag)){
						//本地文件已经删除，则删除服务器相应文件
						
						localSet.remove(localNext);
						remoteSet.remove(remoteNext);
						remoteNext.flag = true;
						remoteSet.add(remoteNext);
						continue;
						}
					else if(remoteNext.flag && (!localNext.flag)){
						//服务器文件被删除
						if(localNext.lastModify > remoteNext.deleteTime){
							//本地修改时间在异地删除时间后，上传
							
						}
						else{
							//删除本地文件
						}
					}
					else if(remoteNext.lastModify > localNext.lastModify){
						//本地文件有修改，需要上传
					}else if(remoteNext.lastModify < localNext.lastModify){
						//服务器文件有修改，需要下载
					}
					
					comFlag = true;
				}
			}
			if((!remoteNext.flag)&&(!comFlag)){//该服务器文件在本地没有找到，且没被删除，下载
				
			}
		}
		for(Iterator<CloudFile> localIt = localSet.iterator();localIt.hasNext();){//遍历本地文件信息
			CloudFile localNext = localIt.next();
			boolean comFlag = false;
			for(Iterator<CloudFile> remoteIt = remoteSet.iterator();remoteIt.hasNext();){
				CloudFile remoteNext = remoteIt.next();
				if(remoteNext.filename == localNext.filename){
					comFlag = true;
				}
			}
			if(!comFlag){//该文件在异地不存在，上传
				
			}
		}
	}

	public void run() {
		scan();
	}

}
