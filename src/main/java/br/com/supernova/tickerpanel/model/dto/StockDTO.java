package br.com.supernova.tickerpanel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private Long id;

    @NotEmpty
    @Size(min = 5, max = 6)
    private String name;

    @NotEmpty
    private String company;

    @NotEmpty
    private Double price;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private Double variation;
}
