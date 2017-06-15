package com.dashubio.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import android.util.Log;

public class FileSizeUtil {
		public static final int SIZETYPE_B = 1;//��ȡ�ļ���С��λΪB��doubleֵ
		public static final int SIZETYPE_KB = 2;//��ȡ�ļ���С��λΪKB��doubleֵ
		public static final int SIZETYPE_MB = 3;//��ȡ�ļ���С��λΪMB��doubleֵ
		public static final int SIZETYPE_GB = 4;//��ȡ�ļ���С��λΪGB��doubleֵ
		/**
		 * ��ȡ�ļ�ָ���ļ���ָ����λ�Ĵ�С
		 * @param filePath �ļ�·��
		 * @param sizeType ��ȡ��С������1ΪB��2ΪKB��3ΪMB��4ΪGB
		 * @return doubleֵ�Ĵ�С
		 */
		public static double getFileOrFilesSize(String filePath,int sizeType){
			File file=new File(filePath);
			long blockSize=0;
			try {
				if(file.isDirectory()){
					blockSize = getFileSizes(file);
				}else{
					blockSize = getFileSize(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("��ȡ�ļ���С","��ȡʧ��!");
			}
			return FormetFileSize(blockSize, sizeType);
		}
		/**
		 * ���ô˷����Զ�����ָ���ļ���ָ���ļ��еĴ�С
		 * @param filePath �ļ�·��
		 * @return ����õĴ�B��KB��MB��GB���ַ���
		 */
		public static String getAutoFileOrFilesSize(String filePath){
			File file=new File(filePath);
			long blockSize=0;
			try {
				if(file.isDirectory()){
					blockSize = getFileSizes(file);
				}else{
					blockSize = getFileSize(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("��ȡ�ļ���С","��ȡʧ��!");
			}
			return FormetFileSize(blockSize);
		}
		/**
		 * ��ȡָ���ļ���С
		 * @param f
		 * @return
		 * @throws Exception
		 */
		private static long getFileSize(File file) throws Exception
		{
			long size = 0;
			if (file.exists()){
				FileInputStream fis = null;
				fis = new FileInputStream(file);
				size = fis.available();
			}
			else{
				file.createNewFile();
				Log.e("��ȡ�ļ���С","�ļ�������!");
			}
			return size;
		}
 
		/**
		 * ��ȡָ���ļ���
		 * @param f
		 * @return
		 * @throws Exception
		 */
		private static long getFileSizes(File f) throws Exception
		{
			long size = 0;
			File flist[] = f.listFiles();
			for (int i = 0; i < flist.length; i++){
				if (flist[i].isDirectory()){
					size = size + getFileSizes(flist[i]);
				}
				else{
					size =size + getFileSize(flist[i]);
				}
			}
			return size;
		}
		/**
		 * ת���ļ���С
		 * @param fileS
		 * @return
		 */
		private static String FormetFileSize(long fileS)
		{
			DecimalFormat df = new DecimalFormat("#.00");
			String fileSizeString = "";
			String wrongSize="0B";
			if(fileS==0){
				return wrongSize;
			}
			if (fileS < 1024){
				fileSizeString = df.format((double) fileS) + "B";
			}
			else if (fileS < 1048576){
				fileSizeString = df.format((double) fileS / 1024) + "KB";
			}
			else if (fileS < 1073741824){
				fileSizeString = df.format((double) fileS / 1048576) + "MB";
			}
			else{
				fileSizeString = df.format((double) fileS / 1073741824) + "GB";
			}
			return fileSizeString;
		}
		/**
		 * ת���ļ���С,ָ��ת��������
		 * @param fileS 
		 * @param sizeType 
		 * @return
		 */
		private static double FormetFileSize(long fileS,int sizeType)
		{
			DecimalFormat df = new DecimalFormat("#.00");
			double fileSizeLong = 0;
			switch (sizeType) {
			case SIZETYPE_B:
				fileSizeLong=Double.valueOf(df.format((double) fileS));
				break;
			case SIZETYPE_KB:
				fileSizeLong=Double.valueOf(df.format((double) fileS / 1024));
				break;
			case SIZETYPE_MB:
				fileSizeLong=Double.valueOf(df.format((double) fileS / 1048576));
				break;
			case SIZETYPE_GB:
				fileSizeLong=Double.valueOf(df.format((double) fileS / 1073741824));
				break;
			default:
				break;
			}
			return fileSizeLong;
		}
}
