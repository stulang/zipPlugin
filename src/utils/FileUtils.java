package com.inspur.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileUtils {  
	
	private int FILESIZE = 8 * 1024;

	private FileOutputStream fos; 
	
	 
	
	public FileUtils(){
		 
	}
	
	/**
	 * 在SD卡上创建文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String dirfileName) throws IOException{
		Log.d("dirfileName<<", ""+dirfileName);
		File file = new File(dirfileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * 在SD卡上创建目录
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName){
		Log.d("dirName<<", ""+dirName);
		File dir = new File(dirName);
		dir.mkdirs();
		return dir;
	}
	
	/**
	 * 判断SD卡上的文件夹是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileDirExist(String filepath){
		
		File file = new File(filepath);
		return file.exists();
	}
	/**
	 * 判断SD卡上的文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String filepath,String fileName){
		File file = new File( filepath+"/"+fileName);
		return file.exists();
	}
	
	/**
	 * 删除指定文件
	 * @param strFileName  文件路径加名字
	 * @return
	 */
	public static boolean delFile(String strFileName) {
		File myFile = new File(strFileName);
		if (myFile.exists()) {
			return myFile.delete();
		}else
			return false;
	}
	
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	
	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		 
		try {
			createSDDir(path);  
			file = createSDFile(path +"/"+ fileName);
			if (file.exists()) { 
				fos = new FileOutputStream(path + "/"+fileName);
				byte[] buffer = new byte[FILESIZE];
				int count = 0;
				// 开始复制文件
				while ((count = input.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				} 
				fos.flush(); 
			} 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		 
		return file;
	}
	
	public static String read2StringFromSdHtml(String fileName){
		StringBuffer sb = new StringBuffer();
		String encoding="UTF-8";
		try {
			File file=new File(fileName);
			if(file.isFile() && file.exists()){
				InputStreamReader read = new InputStreamReader(
				        new FileInputStream(file),encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
                    sb.append(lineTxt);
                }
				read.close();
			}else{
				Log.e("read2StringFromSdHtml", "找不到指定文件");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}
	public static String getSDPath(){
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		if (sdCardExist){
			sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		}
		return sdDir.toString();
	}

    public static void CopyAssets(Context ctx,String assetDir, String dir) {
		String[] files;
		try {
			files = ctx.getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return;
		}
		File mWorkingPath = new File(dir);
		// if this directory does not exists, make one.
		if (!mWorkingPath.exists()) {
			if (!mWorkingPath.mkdirs()) {
				Log.e("--CopyAssets--", "cannot create directory.");
			}
		}
		 for(String fileName : files) {
			try { 
				// we make sure file name not contains '.' to be a folder.
				if (!fileName.contains(".")) {
					if (0 == assetDir.length()) {
						CopyAssets( ctx,fileName, dir + fileName + "/");
					} else {
						CopyAssets(ctx, assetDir + "/" + fileName, dir +"/"+ fileName
								+ "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (outFile.exists())
					outFile.delete();
				InputStream in = null;
				if (0 != assetDir.length())
					in = ctx.getAssets().open(assetDir + "/" + fileName);
				else
					in = ctx.getAssets().open(fileName);
				OutputStream out = new FileOutputStream(outFile);
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
