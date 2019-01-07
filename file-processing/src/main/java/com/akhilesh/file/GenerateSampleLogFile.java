
package com.akhilesh.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateSampleLogFile {

  private static String seprator = ",";
  private static int linesPerFiles = 20000;
  private static String dirPath = "/tmp/javaGoPer/";

  public static void main(String[] args) throws Exception {
    if (args.length >= 1) {
      dirPath = args[0];
    }
    GenerateSampleLogFile gn = new GenerateSampleLogFile();
    System.out.println("DirPath :" + dirPath);
    gn.generateSampleFile(dirPath);
  }

  protected void generateSampleFile(String dirPath) throws Exception {
    List<String> records = new ArrayList<String>();
    File file = new File(dirPath + "SampleLog.txt");

    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
      System.out.println("file :" + file.getAbsolutePath());
      FileWriter writer = new FileWriter(file);
      for (int i = 0; i < linesPerFiles; i++) {
        StringBuffer str = new StringBuffer(getRandomString());
        str.append(seprator).append(getRandomNumber());
        str.append(seprator).append(getRandomNumber());
        str.append(seprator).append(getRandomNumber());
        // System.out.println(str.toString());
        records.add(str.toString());
        if (records.size() >= 2000) {
          write(records, writer);
          records.clear();
        }
      }
      if (records.size() > 0)
        write(records, writer);
      writer.flush();
      writer.close();
    } catch (Exception e) {
    } finally {
    }

  }

  protected String getRandomString() {
    String SALTCHARS = "ABCD";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 3) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;

  }

  protected int getRandomNumber() {
    Random rand = new Random();
    int n = rand.nextInt(50) + 1;
    return n;
  }

  private static void write(List<String> records, Writer writer) throws IOException {
    long start = System.currentTimeMillis();
    for (String record : records) {
      writer.write(record + "\n");
    }
    long end = System.currentTimeMillis();
    System.out.println((end - start) / 1000f + " seconds");
  }
}
