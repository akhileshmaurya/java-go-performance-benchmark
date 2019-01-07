
package com.akhilesh.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadAndParseFile {
  private static String dirPath = "/tmp/javaGoPer/";

  public static void main(String[] args) throws Exception {
    if (args.length >= 1) {
      dirPath = args[0];
    }
    List<String> allfiles = listFilesForFolder(new File(dirPath));
    for (String str : allfiles) {
      System.out.println(str);
    }
  }

  public static List<String> listFilesForFolder(final File folder) {
    List<String> list = new ArrayList<>();
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isFile())
        list.add(fileEntry.getAbsolutePath());
      // System.out.println(fileEntry.getName());

    }
    return list;
  }
}
