package com.CloudDisk;

import java.io.File;

public class FileCoder {
	int w;// ������GF(2^w)�Ĵ�С
	int m;// ���������
	int n;// ���������
	int[][] encodeMatrix;// �������
	int[][] decodeMatrix;// �������

	/**
	 * ���룬��������ļ����б����õ�һϵ�У�n����������ļ�
	 * 
	 * @see �ҵ��ʼ�
	 * @param inputFile
	 *            ������Ҫ������ļ�
	 * @return �����������ļ����飬һ����n��
	 */
	public File[] Encode(File inputFile) {

		// ��ȡinputFile��m��byte[]

		// ����encodeMatrix����n�������output byte[]

		// ��output byte[]д�뵽����ļ�

		// ����ļ��������򣬼��������ļ�Ϊxxx,������ļ�Ϊxxx.00, xxx.01, xxx.02 ......

		// ����ļ��洢���������ļ�ͬһ��Ŀ¼��

		// ���������ڴ����Ŀ�����һ���ķ�Χ�ڣ������Χ����ͨ��byte[]���е���

		return null;
	}

	/**
	 * ���룬��������ļ����б����õ�һϵ�У�n����������ļ�
	 * 
	 * ����ļ�������outputDir
	 * 
	 */
	public File[] Encode(File inputFile, File outputDir) {

		// ����ļ�������outputDir������ͬ��

		// ���⣬�㻹���Ժ���һ������������������ط���

		return null;
	}

	/**
	 * ���룬��������ļ����������õ�������ļ���ԭʼ�ļ���
	 * 
	 * @param inputFile
	 *            ������Ҫ������ļ� ͨ���ļ�������ʶ��
	 * @return �����������ļ����������ʧ�������null
	 */
	public File Decode(File[] inputFile) {

		// ����inputFile����decodeMatrix

		// �ֱ��ȡinputFile������byte[]

		// ����õ������byte[]

		// д�뵽outputFile

		return null;
	}

	/**
	 * ���룬��������ļ����������õ�������ļ���ԭʼ�ļ���
	 * 
	 * @param inputFile
	 *            ������Ҫ������ļ� ͨ���ļ�������ʶ��
	 * @return �����������ļ����������ʧ�������null, ����ļ�������outputDir
	 */
	public File Decode(File[] inputFile, File outputDir) {

		// ����inputFile����decodeMatrix

		// �ֱ��ȡinputFile������byte[]

		// ����õ������byte[]

		// д�뵽outputFile

		return null;
	}

	/**
	 * ���������ļ���һ����
	 * 
	 * ˼����Ϊʲô��Ҫ���������ļ���һ���ԣ�
	 * 
	 * ˼������β��ԣ�
	 * 
	 * ˼����������ظ÷�����������ǿ��Ĺ��ܣ�
	 */
	public boolean testFilesConsistency() {
		return false;

	}

	/**
	 * ���ñ�׼�ķ����ɾ�����Ϊ�������
	 * 
	 * @see https://zh.wikipedia.org/wiki/�����ɾ���
	 * @param m
	 *            �����������n �����������a �����ɾ�������ai
	 * @return ��
	 */
	public void setEncodeMatrix(int m, int n, int[] a) {

	}

	/**
	 * ���ü򻯵ķ����ɾ�����Ϊ�������
	 * 
	 * @see �ҵ��ʼ�
	 * @param m
	 *            �����������n �����������a �����ɾ�������a0 �����ai=a^i
	 * @return ��
	 */
	public void setEncodeMatrix(int m, int n, int a) {

	}

	/**
	 * ���ݱ�������Լ����е�ĳЩ�еõ�������� �򵥵���˵�����ǶԱ���������Щ�еķ�����������
	 * 
	 * @see �ҵ��ʼ�
	 * @param rows
	 *            ��������n�У�����Ҳ���Կ�������Ҫ�������n���ļ�
	 * @return ��
	 */
	private void setDecodeMatrix(int[] rows) {

	}
}
