package functions;

import java.io.File;

public class Cleanup {
	
	public static void deleteFilesInDirectory(String directoryPath) {
		
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.out.println("Failed to delete " + file);
                    }
                }
            }
        }
        else {
        	System.out.println(directoryPath + " does not exist or is not a directory");
        }
    }
	
}
