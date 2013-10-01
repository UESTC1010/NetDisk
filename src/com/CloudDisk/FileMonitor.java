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
 * ���ӱ����ļ��еı仯��
 */
public class FileMonitor implements Runnable {

	File localDir;// �����ļ���
	CloudDisk fileInfoDisk;

	public FileMonitor(File localDir, CloudDisk fileInfoDisk){
		this.localDir = localDir;
		this.fileInfoDisk = fileInfoDisk;
	}
	
	/**
	 * ��ȡ���غ���ص��ļ��б���Ϣ�����жԱȣ�������Ӧ�ϴ����ز���
	 */
	public void scan() {
		scanDir(this.localDir);
		Set<CloudFile> localSet;//�����ļ��б���Ϣ
		Set<CloudFile> remoteSet;//����ļ��б���Ϣ
		File localFileInfo = new File("FileInfo");
		Gson gson = new Gson();
		
		//���ط��������ļ���Ϣ�б�
	    if(fileInfoDisk.downloadFile("remoteFileInfo")){
	    	try{
	    		//�����������ļ��б���Ϣ��jsonת����Set
				FileReader fr1 = new FileReader(remoteFileInfo);
				localSet = gson.fromJson(fr1,new TypeToken<Set<CloudFile>>(){}.getType());//��jsonת����java����
				fr1.close();
			}catch(IOException a){
				a.printStackTrace();
			}
	    }
		
		//��Ȿ���ļ���Ϣ�б����
		if(!localFileInfo.canExecute()){//�����ļ���Ϣ�����ڣ��������µ��ļ�
			try{
				localFileInfo.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				scanDir(localDir);//ɨ���ļ���
			}
			fileSetCompare(CloudFile.fileSet,remoteSet);
		}else{
			
			//�����ļ���Ϣ�б�������ȡFileInfo�ļ����localSet
			try{
				FileReader fr = new FileReader(localFileInfo);
				localSet = gson.fromJson(fr,new TypeToken<Set<CloudFile>>(){}.getType());//��jsonת����java����
				fr.close();
			}catch(IOException a){
				a.printStackTrace();
			}
			//�Ƚϱ����ļ���Ϣ��ɨ����ļ���Ϣ
			//�����½��ļ�����Ӱ��
			//�����޸��ļ������޸����ļ����Լ��ļ����ݣ��򵱱����ļ���ɾ�������½���һ���ļ�
			//����ɾ���ļ���Ҫ��������½��ļ�����
			for(Iterator<CloudFile> localIt = localSet.iterator();localIt.hasNext();){
				CloudFile localFile = localIt.next();
				boolean flag = false;
				for(Iterator<CloudFile> newIt = CloudFile.fileSet.iterator();newIt.hasNext();){
					CloudFile newFile = newIt.next();
					if(localFile.filename == newFile.filename)
						flag = true;
				}
				if(flag == false){//��ʾlocaFile�Ѿ��ڱ��ر�ɾ��
					localFile.setFlag(true);//�����ļ���ʶΪ��ɾ��
					//CloudFile.fileSet.add(localFile);
					
				}
			}
			
			fileSetCompare(CloudFile.fileSet,remoteSet);
		}
		

		//���µ��ļ���Ϣת��Ϊjsonд���ļ�FileInfo
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
	 * ɨ�豾���ļ��У��������ļ���Ϣ����CloudFile.fileSet��
	 * @param dir
	 */
	
	public void scanDir(File dir){
		File[] files = dir.listFiles();
		//CloudFile.fileSet = new HashSet<CloudFile>();
		for(int x = 0;x < files.length;x ++){
			if(files[x].isDirectory())//�����ļ�����ݹ����scanDir
				scanDir(files[x]);
			else{
				CloudFile.fileSet.add(new CloudFile(files[x]));
			}
		}
	}
	
	/**
	 * �����ļ��б�ͷ������ļ��б���жԱȣ�ȷ���������
	 * ���⣺����ɾ���ļ��ͷ������½��ļ������ؽ����
	 * ���⣺���ɾ�����ļ��ͱ������ϴ��ĳ�ͻ���
	 * @param localSet
	 * @param remoteSet
	 */
	public void fileSetCompare(Set<CloudFile> localSet,Set<CloudFile> remoteSet){
		//�Աȱ����ļ��б���µ��ļ��б��õ��ļ��䶯��Ϣ
		for(Iterator<CloudFile> remoteIt = remoteSet.iterator();remoteIt.hasNext();){//��������ļ���Ϣ�б���ҵ��뱾���б�ͬ�ı���
			CloudFile remoteNext = remoteIt.next();
			boolean comFlag = false;
			for(Iterator<CloudFile> localIt = localSet.iterator();localIt.hasNext();){
				CloudFile localNext = localIt.next();
				//if(remoteNext.filename.compareTo(localNext.filename) == 0){
				if(remoteNext.filename.getName().equals(localNext.filename.getName())){
					if(localNext.flag && (!remoteNext.flag)){
						//�����ļ��Ѿ�ɾ������ɾ����������Ӧ�ļ�
						
						localSet.remove(localNext);
						remoteSet.remove(remoteNext);
						remoteNext.flag = true;
						remoteSet.add(remoteNext);
						continue;
						}
					else if(remoteNext.flag && (!localNext.flag)){
						//�������ļ���ɾ��
						if(localNext.lastModify > remoteNext.deleteTime){
							//�����޸�ʱ�������ɾ��ʱ����ϴ�
							
						}
						else{
							//ɾ�������ļ�
						}
					}
					else if(remoteNext.lastModify > localNext.lastModify){
						//�����ļ����޸ģ���Ҫ�ϴ�
					}else if(remoteNext.lastModify < localNext.lastModify){
						//�������ļ����޸ģ���Ҫ����
					}
					
					comFlag = true;
				}
			}
			if((!remoteNext.flag)&&(!comFlag)){//�÷������ļ��ڱ���û���ҵ�����û��ɾ��������
				
			}
		}
		for(Iterator<CloudFile> localIt = localSet.iterator();localIt.hasNext();){//���������ļ���Ϣ
			CloudFile localNext = localIt.next();
			boolean comFlag = false;
			for(Iterator<CloudFile> remoteIt = remoteSet.iterator();remoteIt.hasNext();){
				CloudFile remoteNext = remoteIt.next();
				if(remoteNext.filename == localNext.filename){
					comFlag = true;
				}
			}
			if(!comFlag){//���ļ�����ز����ڣ��ϴ�
				
			}
		}
	}

	public void run() {
		scan();
	}

}
