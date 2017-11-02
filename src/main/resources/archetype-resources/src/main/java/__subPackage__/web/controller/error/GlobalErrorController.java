package ${groupId}.${subPackage}.web.controller.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class GlobalErrorController implements ErrorController{
    static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
