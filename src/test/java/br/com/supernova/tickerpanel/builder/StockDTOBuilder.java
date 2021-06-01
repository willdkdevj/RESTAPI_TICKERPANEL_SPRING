package br.com.supernova.tickerpanel.builder;

import br.com.supernova.tickerpanel.model.dto.StockDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class StockDTOBuilder {

    @Builder.Default
    private static final Long id = 1L;

    @Builder.Default
    private static final String name = "SANB11";

    @Builder.Default
    private static final String company = "Banco Santander Brasil";

    @Builder.Default
    private static final Double price = 41.22D;

    @Builder.Default
    private static final LocalDate date = LocalDate.of(2021, 5, 31);

    @Builder.Default
    private static final Double variation = 1.03D;

    public StockDTO toStockDTO(){
        return new StockDTO(id, name, company, price, date, variation);
    }
}
