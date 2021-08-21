package az.hrportal.hrportalapi.error.exception;

public class FileExtensionNotAllowedException extends RuntimeException {
    public FileExtensionNotAllowedException(String extension) {
        super("Not allowed file extension!! EXTENSION : " + extension);
    }
}
