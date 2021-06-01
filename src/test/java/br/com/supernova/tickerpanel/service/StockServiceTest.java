package br.com.supernova.tickerpanel.service;

import br.com.supernova.tickerpanel.mapper.StockMapper;
import br.com.supernova.tickerpanel.repository.StockRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    private StockMapper mapper = StockMapper.INSTANCE;
    private static final Long INVALID_ROOM_ID = 2L;
    private static final String CHECKED_TICKER = "WDKR12";

    @Mock
    private StockRepository repository;

    @InjectMocks
    private StockService service;
}
