package ${groupId}.${subPackage}.web.handler;

import ${groupId}.${subPackage}.util.ServiceException;
import ${groupId}.${subPackage}.util.ToolKit;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice("${groupId}.${subPackage}.web.controller.api")
public class APIExceptionHandler {
    private static final int DEFAULT_ERROR_CODE = 500;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handle(Exception exception){
        int error = DEFAULT_ERROR_CODE;
        if(exception instanceof ServiceException){
            error = ((ServiceException)exception).getError();
        }
        return ToolKit.assemblyResponseBody(error, exception.getMessage());
    }
}
