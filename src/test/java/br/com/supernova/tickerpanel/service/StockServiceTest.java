package br.com.supernova.tickerpanel.service;

import br.com.supernova.tickerpanel.builder.StockDTOBuilder;
import br.com.supernova.tickerpanel.mapper.StockMapper;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.model.entity.Stock;
import br.com.supernova.tickerpanel.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    private StockMapper mapper = StockMapper.INSTANCE;
    private static final Long INVALID_ROOM_ID = 2L;
    private static final String CHECKED_TICKER = "WDKR12";

    @Mock
    private StockRepository repository;

    @InjectMocks
    private StockService service;

    @Test
    void whenStockDTOProvidedThenReturnResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByName(builderDTO.getName())).thenReturn(Optional.empty());
        when(repository.save(any(Stock.class))).thenReturn(stockEntity);


    }
}
