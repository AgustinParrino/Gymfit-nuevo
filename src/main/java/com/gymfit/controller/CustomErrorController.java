package com.gymfit.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        // Obtener el código de error
        Object status = model.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "Página no encontrada");
                model.addAttribute("errorCode", 404);
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorMessage", "Error interno del servidor");
                model.addAttribute("errorCode", 500);
            } else {
                model.addAttribute("errorMessage", "Error desconocido");
                model.addAttribute("errorCode", statusCode);
            }
        }
        return "error";
    }
}