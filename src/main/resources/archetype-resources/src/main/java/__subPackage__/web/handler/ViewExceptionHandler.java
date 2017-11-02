package ${groupId}.${subPackage}.web.handler;

import ${groupId}.${subPackage}.util.ServiceException;
import ${groupId}.${subPackage}.util.ToolKit;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice("${groupId}.${subPackage}.web.controller.view")
public class ViewExceptionHandler {
    private static final int DEFAULT_ERROR_CODE = 500;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public String handle(HttpServletRequest request, Exception exception)  {
        if(exception instanceof ServiceException){
            request.setAttribute("error", ((ServiceException)exception).getError());
        }else{
            request.setAttribute("error", DEFAULT_ERROR_CODE);
            request.setAttribute("stack", ToolKit.convertStackTrace(exception));
        }
        request.setAttribute("message", exception.getMessage());
        return "err";
    }
}
