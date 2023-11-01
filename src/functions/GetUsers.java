package functions;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import models.User;

public class GetUsers {
	
	public static List<User> getUsers() {
		Path startPath = Paths.get("blockchain/users");
		List<User> userList = new ArrayList<>();
		
		try {
			Files.walkFileTree(startPath, EnumSet.noneOf(FileVisitOption.class), 1, new FileVisitor<Path>() {
				
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					if (file.toString().endsWith(".txt")) {
						User user = new User(file.toString());
						userList.add(user);
					}
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
}
