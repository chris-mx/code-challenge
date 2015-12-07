package com.app.config;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

public class FileGenerator {
	
	private static final String[] CATEGORIES = {"Information_Technology", "Media", "Search_Engines", "Entertainment", "Social_Networks"};
	private static final Integer[] YEARS;
	
	static{
		YEARS = new Integer[16];
		for(int i=2000; i<=2015; i++)
			YEARS[i-2000] = i;
	}
	
	public static void main(String... args) throws IOException{
		final String imagesPath = "http://10.254.254.137:8083/sites/epam.gdl.drupal.dd/files/styles/large/public/field/image/";
		final String fileNames = "D:/fileNames.txt";
		try(BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/ImagesJSON.json"), "UTF-8"));
			BufferedWriter params = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/params.csv"), "UTF-8"));
			BufferedReader files = new BufferedReader(new FileReader(fileNames))){
			br.write("[");
			br.newLine();
			String fileName;
			int i = 1;
			File file = new File(fileNames);
			LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
			lineNumberReader.skip(Long.MAX_VALUE);
			int lines = lineNumberReader.getLineNumber();
			System.out.println(lines);
			while((fileName=files.readLine())!=null){
//				File file = new File(path+"/"+img);
//				URL url = new URL(imagesPath+fileName);
				String category = CATEGORIES[((int)(Math.random()*5))];
				Integer year =  YEARS[((int)(Math.random()*16))];
				br.write("{ \"_id\" : "+i+", \"url\" : \""+imagesPath+"/"+fileName+"\", \"category\" : \""+category+"\", \"year\" : \""+year+"\" }");
				if(i<lines){
					br.write(",");
					br.newLine();
				}
				BufferedImage bi = ImageIO.read(new URL(imagesPath+"/"+fileName));
				for(int k=0; k<3; k++){
					StringBuilder sb = new StringBuilder(category).append(",").append(year).append(",").append(fileName.substring(fileName.lastIndexOf(".")+1)).append(",").append(bi.getWidth()).append(",").append(bi.getHeight()).append(",").append(fileName.substring(0, fileName.lastIndexOf(".")));
					params.write(sb.toString());
					params.newLine();
				}
				i++;
			}
			br.write("]");
		}
		
//		System.out.println("Writting");
//		final String path = "D:/images";
//		String[] files = new File(path).list();
//		BufferedWriter fileNames = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/fileNames.txt"), "UTF-8"));
//		for(String file : files){
//			System.out.println(file);
//			fileNames.write(file);
//			fileNames.newLine();
//		}
//		fileNames.close();
	}
	
}