package Exception;

/**
 * Класс для обработки исключения когда директория указана неправильна или не существует
 */
public class DirectoryNotFoundException extends Exception {
    public  DirectoryNotFoundException(String exeption)
    {
        super("Директории " + exeption+ " не существует");
    }
}
