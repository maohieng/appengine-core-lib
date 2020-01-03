package com.jommobile.appengine.storage;

import com.google.cloud.storage.Blob;
import com.google.common.base.Strings;
import com.jommobile.appengine.image.ImageServices;
import com.jommobile.appengine.utils.Logs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by MAO Hieng on 12/22/18.
 * <p>
 *
 * </p>
 */
final class StorageFileUploadServiceImp implements StorageFileUploadService {

    private static final Logger log = Logs.makeLogger(StorageFileUploadServiceImp.class);

//    static final String AUDIO_DIR;
//    static final String IMAGE_DIR;
//    static final String DOCUMENT_DIR;
//
//    static {
//        if (Constants.LOCAL_DEV) {
//            AUDIO_DIR = "local_server/audios/";
//            IMAGE_DIR = "local_server/images/";
//            DOCUMENT_DIR = "local_server/documents/";
//        } else {
//            AUDIO_DIR = "server/audios/";
//            IMAGE_DIR = "server/images/";
//            DOCUMENT_DIR = "server/documents/";
//        }
//    }

    final String bucketName;
    final StorageServiceManager manager;

    StorageFileUploadServiceImp(String bucketName) {
        this.bucketName = bucketName;
        this.manager = StorageServiceManager.getInstance();
    }

    private String createFileName(@Nonnull String fullDir, @Nonnull String submittedFileName, long entityId) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("-YYYY-MM-dd-HHmmssSSS");
//        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of(Const.TIME_ZOME_ID_CAMBODIA));
//        String dtString = dateTime.format(dtf);

        String fileName;
        if (entityId == 0) {
            fileName = fullDir + submittedFileName;
        } else {
            fileName = fullDir + entityId + "-" + submittedFileName;
        }

        return fileName;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Upload Images
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Used with /fragment/input_images.jsp.
     */
    @Nullable
    public List<Image> uploadImages(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException {
        final int totalImage = Integer.parseInt(req.getParameter(ATTR_MAX_NUMB_IMAGE));

        List<Image> images = new ArrayList<>();

        for (int i = 1; i <= totalImage; i++) {
            Part imagePart = req.getPart("image" + i);
            String imageUrl = req.getParameter("imageUrl" + i);
            String thumbnail = req.getParameter("thumbnailUrl" + i);
            String blobName = req.getParameter("imageBlob" + i);

            Image image = uploadImage(moduleName, imagePart, imageUrl, thumbnail, blobName, entityId);

            if (image != null) {
                images.add(image);
            }
        }

        return images.isEmpty() ? null : images;
    }

    /**
     * Used with /fragment/input_image.jsp.
     */
    @Nullable
    public Image uploadImage(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException {
        Part imagePart = req.getPart("image");
        String imageUrl = req.getParameter("imageUrl");
        String thumbnail = req.getParameter("thumbnailUrl");
        String blobName = req.getParameter("imageBlob");

        return uploadImage(moduleName, imagePart, imageUrl, thumbnail, blobName, entityId);
    }

    @Nullable
    public Image uploadImage(String moduleName, @Nonnull Part filePart,
                             String imageUrl, String thumbnail, String blobName,
                             long entityId) throws IllegalArgumentException, IOException {
        // Check file extension
        checkImageFileExtension(filePart.getSubmittedFileName());

        // Create file name
        String fileName = createImageFileName(moduleName, filePart.getSubmittedFileName(), entityId);

        Image image = null;

        // Upload image
        if (filePart.getSize() > 0) {
            Blob blob = manager.uploadFile(filePart, fileName, bucketName);
            String name = blob.getName();

            // Create thumbnail
            try {
                thumbnail = ImageServices.getImageThumbnailURL(name, bucketName);
            } catch (IllegalArgumentException e) {
                // local dev server error
                e.printStackTrace();
            }

            image = new Image(blob, thumbnail);
            log.info("Image uploaded: " + image);
        } else {
            log.info("No file pick to upload.");
            // Use existing data
            if (imageUrl != null && !imageUrl.isEmpty())
                image = new Image(imageUrl, thumbnail, blobName);
        }

        return image;
    }

    /**
     * Checks and throws an exception if the given file name is not one of image extension.
     *
     * @param fileName
     * @throws IllegalArgumentException
     */
    private void checkImageFileExtension(String fileName) throws IllegalArgumentException {
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String[] allowedExt = {".jpg", ".jpeg", ".png", ".gif"};
            for (String ext : allowedExt) {
                if (fileName.endsWith(ext)) {
                    return;
                }
            }

            throw new IllegalArgumentException("file must be an image");
        }
    }

    private String createImageFileName(@Nullable String module, @Nonnull String submittedFileName, long entityId) {
        String reference = StorageFileUploadService.getImagesDir();
        if (module != null && !module.isEmpty()) {
            reference = reference + module + "/";
        }

        return createFileName(reference, submittedFileName, entityId);
    }

    /**
     * Used with /fragment/firebase_input_images.jsp, it uses javascript to upload files.
     * Extracts the parameters and creates image thumbnails.
     */
    @Nullable
    public List<Image> firebaseImagesUploaded(HttpServletRequest req) {
        final int totalImage = Integer.parseInt(req.getParameter(ATTR_MAX_NUMB_IMAGE));
        List<Image> images = new ArrayList<>();

        for (int i = 1; i <= totalImage; i++) {
            String imageUrl = req.getParameter("imageUrl" + i);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                String thumbnail = req.getParameter("thumbnailUrl" + i);
                String blobName = req.getParameter("imageBlob" + i);

                if (blobName != null && !blobName.isEmpty() && Strings.isNullOrEmpty(thumbnail)) {
                    try {
                        thumbnail = ImageServices.getImageThumbnailURL(blobName, bucketName);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }

                images.add(new Image(imageUrl, thumbnail, blobName));
            }
        }

        return images.isEmpty() ? null : images; // return null to avoid save empty array into DB
    }

    ///////////////////////////////////////////////////////////////////////////
    // Upload Audios
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Used with /fragment/input_audios.jsp.
     */
    @Nullable
    public List<Audio> uploadAudios(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException {
        final int totalAudio = Integer.parseInt(req.getParameter(ATTR_MAX_NUMB_AUDIO));

        List<Audio> audios = new ArrayList<>();

        for (int i = 1; i <= totalAudio; i++) {
            Part audioPart = req.getPart("audio" + i);
            String audioUrl = req.getParameter("audioUrl" + i);
            String blobName = req.getParameter("audioBlob" + i);

            Audio audio = uploadAudio(moduleName, audioPart, audioUrl, blobName, entityId);

            if (audio != null) {
                audios.add(audio);
            }
        }


        return audios.isEmpty() ? null : audios;
    }

    /**
     * Used with /fragment/input_audio.jsp.
     */
    @Nullable
    public Audio uploadAudio(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException {
        Part filePart = req.getPart("audio");
        String audioUrl = req.getParameter("audioUrl");
        String blobName = req.getParameter("audioBlob");

        return uploadAudio(moduleName, filePart, audioUrl, blobName, entityId);
    }

    @Nullable
    public Audio uploadAudio(String moduleName, @Nonnull Part filePart,
                             String downloadUrl, String blobName, long entityId) throws IllegalArgumentException, IOException {
        // Check file extension
        checkAudioFileExtension(filePart.getSubmittedFileName());

        // Create file name
        String fileName = createAudioFileName(moduleName, filePart.getSubmittedFileName(), entityId);

        Audio audio = null;

        // Upload file
        if (filePart.getSize() > 0) {
            Blob blob = manager.uploadFile(filePart, fileName, bucketName);
            audio = new Audio(blob);
        } else {
            // log.info("No file pick to upload.");
            // Use existing data
            if (downloadUrl != null && !downloadUrl.isEmpty())
                audio = new Audio(downloadUrl, blobName);
        }

        return audio;
    }

    /**
     * Checks acceptance file extension or throws an exception if the given file name is not one of audio extension.
     *
     * @param fileName
     * @throws ServletException
     */
    public static void checkAudioFileExtension(String fileName) throws IllegalArgumentException {
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String[] allowedExt = {".mp3", ".wav", ".aac", ".wma", ".webm"};
            for (String ext : allowedExt) {
                if (fileName.endsWith(ext)) {
                    return;
                }
            }

            throw new IllegalArgumentException("file must be an audio");
        }
    }

    /**
     * Creates a full path and file name (ref) of the given file.
     *
     * @param module
     * @param submittedFileName
     * @param entityId
     * @return
     */
    private String createAudioFileName(@Nullable String module, @Nonnull String submittedFileName, long entityId) {
        String reference = StorageFileUploadService.getAudiosDir();
        if (module != null && !module.isEmpty()) {
            reference = reference + module + "/";
        }

        return createFileName(reference, submittedFileName, entityId);
    }

    /**
     * Used with /fragment/firebase_input_audios.jsp, it uses javascript to upload files.
     */
    @Nullable
    public List<Audio> firebaseAudiosUploaded(HttpServletRequest req) {
        final int totalAudio = Integer.parseInt(req.getParameter(ATTR_MAX_NUMB_AUDIO));
        List<Audio> audios = new ArrayList<>();

        for (int i = 1; i <= totalAudio; i++) {
            String audioUrl = req.getParameter("audioUrl" + i);

            if (audioUrl != null && !audioUrl.isEmpty()) {
                String blobName = req.getParameter("audioBlob" + i);
                audios.add(new Audio(audioUrl, blobName));
            }
        }

        return audios.isEmpty() ? null : audios; // return null to avoid save empty array into DB
    }

    ///////////////////////////////////////////////////////////////////////////
    // Any files
    ///////////////////////////////////////////////////////////////////////////

    public StorageBlob uploadFile(String fullDirRef, @Nonnull Part filePart,
                                  String downloadUrl, String blobName, long entityId) throws IllegalArgumentException, IOException {

        // Create file name
        String fileName = createFileName(fullDirRef, filePart.getSubmittedFileName(), entityId);

        StorageBlob fileBlob = null;

        // Upload file
        if (filePart.getSize() > 0) {
            Blob blob = manager.uploadFile(filePart, fileName, bucketName);
            fileBlob = new StorageBlob(blob);
        } else {
            // log.info("No file pick to upload.");
            // Use existing data
            fileBlob = new StorageBlob(downloadUrl, blobName);
        }

        return fileBlob;
    }

}
