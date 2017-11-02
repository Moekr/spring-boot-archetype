package ${groupId}.${subPackage}.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.*;

public abstract class ToolKit {
    public static Map<String, Object> assemblyResponseBody(Object res){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", 0);
        responseBody.put("res", res);
        return responseBody;
    }

    public static Map<String, Object> assemblyResponseBody(int error, String message){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", error);
        responseBody.put("message", message);
        return responseBody;
    }

    public static <T> List<T> iterableToList(Iterable<T> iterable){
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static <T> List<T> sort(List<T> list, Comparator<T> comparator){
        list.sort(comparator);
        return list;
    }

    public static <I extends Serializable, E> void assertNotNull(I id, E entity){
        if(entity == null){
            throw new ServiceException(ServiceException.NOT_FOUND, "Entity Not Found: " + "[id = " + id + "]");
        }
    }

    public static String convertStackTrace(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public static HttpStatus httpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
