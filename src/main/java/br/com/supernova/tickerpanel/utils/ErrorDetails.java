package br.com.supernova.tickerpanel.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private LocalDate timestamp;
    private String message;
    private String details;
}
