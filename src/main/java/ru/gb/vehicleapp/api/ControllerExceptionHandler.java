package ru.gb.vehicleapp.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.vehicleapp.exceptions.ExistingValueException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ExistingValueException.class)
    public String handleException(ExistingValueException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleException(MethodArgumentNotValidException e, Model model) {
        String message = "Допускаются только государственные номера РФ (кириллица, " +
                "заглавные символы АВЕКМНОРСТУХ)";
        model.addAttribute("message", message);
        return "error";
    }
}