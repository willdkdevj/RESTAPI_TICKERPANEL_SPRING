package br.com.supernova.tickerpanel.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private Long id;

    @NotNull
    @Size(min = 5, max = 6)
    private String name;

    @NotNull
    @Size(min = 5, max = 40)
    private String company;

    @NotNull
    private Double price;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    private Double variation;
}
