package com.aidouc.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice(annotations = {RestControllerAdvice.class, Controller.class})
@ResponseBody
public class ExpttionError {
    @ExceptionHandler()
    public Result<String> extionthrow(SQLIntegrityConstraintViolationException sq) {
        log.info(sq.getMessage());
        String eorrStr = sq.getMessage();
        if (eorrStr.contains("Duplicate entry")) {
            String s = eorrStr.split(" ")[2] + "重复";
            log.info(s);
            return Result.error(s);
        }
        return Result.error("未知错误");
    }

    @ExceptionHandler(CustomExption.class)
    public Result<String> CustomExpt(CustomExption ex) {
        log.info(ex.getMessage());
        String eorrStr = ex.getMessage();
        return Result.error(eorrStr);
    }
}
