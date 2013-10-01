package com.CloudDisk;

import java.io.File;
import java.util.Set;

public class CloudFile {
	static Set<CloudFile> fileSet;

	File filename;
	long lastModify;
	boolean flag = false;//ɾ����ʶ
	long deleteTime;
	int m = 3;// ����������Ĭ��ֵ
	int n = 5;// 3��5��ζ�Ŷ�ÿ���ļ������5�顢��������3����������Ļָ���ԭ�����ļ�
	CloudDisk[] location;
	boolean[] consistency;// һ���ԡ�����uploadʱ�е�clouddisk���ܻ�offline����ɸ����ϵĿ��������̵Ĳ�һ��
	String hash;// �ļ��Ĺ�ϣֵ
	String[] blockHash;// �ļ����������ֿ�Ĺ�ϣֵ

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
		// ͨ����������������õ�i���ļ����location
		this.location[i] = location;
	}

	public void autoSetLocation(CloudDisk location) {
		// ͨ��������������Զ������ļ����location
		// ��Ҫ�ο����̵�QOS����
	}

	public boolean upload() {
		// ���ȶԸ��ļ������n���飬Ȼ��ֱ��ϴ���location�ĸ��������С�
		// ע�⣬��Ҫ������ֱ���ϴ�����Ȼ�����Թ���
		// ���һ��NetAgent����������ȫ���е��ϴ������ع���
		// ������ֻ�ǲ���agent���ϴ����񼴿�
		// ���agent����̫�࣬�����ϴ�����ʧ�ܣ�����false
		FileCoder upFile = new FileCoder();
		upFile.m = this.m;
		upFile.n = this.n;
		File[] upfileblocks = new File[n];
		upfileblocks = upFile.Encode(filename);
		
		
		return true;
	}

	public boolean download() {
		// ���ز�����
		// ע�⣬��Ҫ������ֱ�����أ���Ȼ�����Թ���
		// ���һ��NetAgent����������ȫ���е��ϴ������ع���
		// ������ֻ�ǲ���agent���������񼴿�
		// ���agent����̫�࣬������������ʧ�ܣ�����false
		// �����첽���ã�NetAgent������ɺ��ѱ��̣߳���ɽ���
		NetworkTask downloadFile = new NetworkTask();
		downloadFile.localFile = this.filename;
		
		return true;
	}

	public boolean fullCheck() {
		// �������location�ķֿ��Ƿ���ȷ
		for(int i = 0;i < this.n; i ++){
			if(location[i].available() != true)
				return false;
		}
		return true;
	}

	public boolean availableCheck() {
		// ����Ƿ����㹻��location�ϵķֿ�
		// �������е�n���ֿ鶼��ȷ���ã�fullCheck����true
		// ֻҪ��m���ֿ���ȷ���ã�availableCheck����true
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
		// ���Ե�i���Ƿ����;
		if(location[i].available() != true)
			return false;
		return true;
	}

	public boolean consistencyMaintain() {
		// ������һ���ԣ�
		return true;
	}

	public void callback() {
		// ����NetworkAgent���з��ص���
		// ������Լ����
	}

}
