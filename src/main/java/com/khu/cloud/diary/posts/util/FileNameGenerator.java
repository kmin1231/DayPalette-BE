// posts/util/FileNameGenerator.java

package com.khu.cloud.diary.posts.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameGenerator {
    public static String generateFileName(String userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        String fileName = "user_" + userId + "_" + formattedDateTime + ".png";
        return fileName;
    }
}