package it.amedeo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFilesInDir {

	public ArrayList<String> ReadFilesInDir(String dir) {
        ArrayList<String> listFiles = new ArrayList<String>();
		File[] filesInDir = null;;
		File file = new File(dir);
		if(file.isDirectory()) {
			filesInDir = file.listFiles();
			Arrays.sort(filesInDir);
			for(File f : filesInDir) {
				String prefix = "";
				if(f.isFile())
					prefix = "[f] ";
				else if(f.isDirectory())
					prefix = "[d] ";
				System.out.println(prefix + f.toString());
				
				if (f.isFile()) {
					listFiles.add(f.getName());
				}
			}
		}
		return listFiles;
	}
}