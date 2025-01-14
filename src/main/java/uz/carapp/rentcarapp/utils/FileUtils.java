package uz.carapp.rentcarapp.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;

public class FileUtils {


    /**
     * Generate unique name to avoid duplicate file name
     *
     * @param originalFileName Name of the file
     * @return  unique file name
     */
    public static String generateUniqueName(String originalFileName) {

        String originalName = encodeFileName(originalFileName);

        // Get file extension/suffix from fileName (MyPhoto.jpg => jpg).
        String suffix = FilenameUtils.getExtension(originalName);

        // Generate unique name for file and ID for Image.
        UUID uniqueName = UUID.randomUUID();

        // Make dot + suffix.
        String dotSuffix = suffix.isEmpty() ? "" : '.' + suffix;

        // Make unique file name + suffix.
        String fileName = uniqueName + dotSuffix;

        return fileName;
    }
    /**
     * Encoding name of the file to avoid duplicate file names
     *
     * @param originalFilename Name of the file
     * @return encoded file name in UTF-8 encoding
     */
    public static String encodeFileName(String originalFilename) {
        String fileName = null;
        try {
            fileName = URLEncoder.encode(originalFilename, "UTF-8");
            fileName = URLDecoder.decode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /** convert from multipart to file
     *
     * @param multipart type
     * @return file type
     * @throws IllegalStateException
     * @throws IOException
     */
    private static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(Objects.requireNonNull(multipart.getOriginalFilename()));
        multipart.transferTo(convFile);
        return convFile;
    }
}
