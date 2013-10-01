package com.CloudDisk;

import java.io.File;

public class FileCoder {
	int w;// 有限域GF(2^w)的大小
	int m;// 矩阵的行数
	int n;// 矩阵的列数
	int[][] encodeMatrix;// 编码矩阵
	int[][] decodeMatrix;// 译码矩阵

	/**
	 * 编码，将输入的文件进行编码后得到一系列（n个）的输出文件
	 * 
	 * @see 我的邮件
	 * @param inputFile
	 *            输入需要编码的文件
	 * @return 编码后输出的文件数组，一共有n个
	 */
	public File[] Encode(File inputFile) {

		// 读取inputFile到m个byte[]

		// 根据encodeMatrix计算n个输出的output byte[]

		// 将output byte[]写入到输出文件

		// 输出文件命名规则，假设输入文件为xxx,则输出文件为xxx.00, xxx.01, xxx.02 ......

		// 输出文件存储在与输入文件同一个目录下

		// 整个过程内存消耗控制在一定的范围内，这个范围可以通过byte[]进行调节

		return null;
	}

	/**
	 * 编码，将输入的文件进行编码后得到一系列（n个）的输出文件
	 * 
	 * 输出文件保存在outputDir
	 * 
	 */
	public File[] Encode(File inputFile, File outputDir) {

		// 输出文件保存在outputDir，其他同上

		// 另外，你还可以和杨一商量，设计其他的重载方法

		return null;
	}

	/**
	 * 译码，将输入的文件进行译码后得到的输出文件（原始文件）
	 * 
	 * @param inputFile
	 *            输入需要译码的文件 通过文件名进行识别
	 * @return 译码后输出的文件，如果译码失败则输出null
	 */
	public File Decode(File[] inputFile) {

		// 根据inputFile生成decodeMatrix

		// 分别读取inputFile到各个byte[]

		// 计算得到输出的byte[]

		// 写入到outputFile

		return null;
	}

	/**
	 * 译码，将输入的文件进行译码后得到的输出文件（原始文件）
	 * 
	 * @param inputFile
	 *            输入需要译码的文件 通过文件名进行识别
	 * @return 译码后输出的文件，如果译码失败则输出null, 输出文件保存在outputDir
	 */
	public File Decode(File[] inputFile, File outputDir) {

		// 根据inputFile生成decodeMatrix

		// 分别读取inputFile到各个byte[]

		// 计算得到输出的byte[]

		// 写入到outputFile

		return null;
	}

	/**
	 * 测试译码文件的一致性
	 * 
	 * 思考：为什么需要测试译码文件的一致性？
	 * 
	 * 思考：如何测试？
	 * 
	 * 思考：如何重载该方法，给出更强大的功能？
	 */
	public boolean testFilesConsistency() {
		return false;

	}

	/**
	 * 设置标准的范德蒙矩阵作为编码矩阵
	 * 
	 * @see https://zh.wikipedia.org/wiki/范德蒙矩阵
	 * @param m
	 *            矩阵的行数，n 矩阵的列数，a 范德蒙矩阵矩阵的ai
	 * @return 无
	 */
	public void setEncodeMatrix(int m, int n, int[] a) {

	}

	/**
	 * 设置简化的范德蒙矩阵作为编码矩阵
	 * 
	 * @see 我的邮件
	 * @param m
	 *            矩阵的行数，n 矩阵的列数，a 范德蒙矩阵矩阵的a0 其余的ai=a^i
	 * @return 无
	 */
	public void setEncodeMatrix(int m, int n, int a) {

	}

	/**
	 * 根据编码矩阵以及其中的某些行得到译码矩阵 简单的来说，就是对编码矩阵的这些行的方阵计算逆矩阵
	 * 
	 * @see 我的邮件
	 * @param rows
	 *            编码矩阵的n行，或者也可以看成是需要译码的那n个文件
	 * @return 无
	 */
	private void setDecodeMatrix(int[] rows) {

	}
}
