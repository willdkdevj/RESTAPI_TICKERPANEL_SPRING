package br.com.supernova.tickerpanel.service;

import br.com.supernova.tickerpanel.builder.StockDTOBuilder;
import br.com.supernova.tickerpanel.exception.ResourceAlreadyRegisteredException;
import br.com.supernova.tickerpanel.exception.ResourceNotFoundException;
import br.com.supernova.tickerpanel.mapper.StockMapper;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.model.entity.Stock;
import br.com.supernova.tickerpanel.repository.StockRepository;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void whenStockDTOProvidedThenReturnResponseEntityOK() throws ResourceAlreadyRegisteredException {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByNameAndDate(builderDTO.getName(), builderDTO.getDate())).thenReturn(Optional.empty());
        when(repository.save(any(Stock.class))).thenReturn(stockEntity);

        StockDTO savedStock = service.createStock(builderDTO);

        assertThat(savedStock.getName(), is(equalTo(stockEntity.getName())));
        assertThat(savedStock.getCompany(), is(equalTo(stockEntity.getCompany())));
    }

    @Test
    void whenAlreadyRegisteredStockDTOThenAndExceptionShouldBeThrow() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByNameAndDate(builderDTO.getName(), builderDTO.getDate())).thenReturn(Optional.of(stockEntity));

        assertThrows(ResourceAlreadyRegisteredException.class, () -> service.createStock(builderDTO));
    }

    @Test
    void whenEnteringValidStockNameThenReturnResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByName(builderDTO.getName())).thenReturn(Optional.of(stockEntity));

        StockDTO returnedStockDTO = service.checkStockName(builderDTO.getName());

        assertThat(returnedStockDTO.getName(), is(equalTo(stockEntity.getName())));
        assertThat(returnedStockDTO.getCompany(), is(equalTo(stockEntity.getCompany())));
    }

    @Test
    void whenEnteringInvalidStockNameThenAnExceptionShouldBeThrow() {
        when(repository.findByName(CHECKED_TICKER)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.checkStockName(CHECKED_TICKER));
    }

    @Test
    void whenEnteringValidStockIDThenReturnResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findById(builderDTO.getId())).thenReturn(Optional.of(stockEntity));

        StockDTO returnedStockDTO = service.checkStockID(builderDTO.getId());

        assertThat(returnedStockDTO.getId(), is(equalTo(stockEntity.getId())));
    }

    @Test
    void whenEnteringInvalidStockIDThenAnExceptionShouldBeThrow() {
        when(repository.findById(INVALID_ROOM_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.checkStockID(INVALID_ROOM_ID));
    }

    @Test
    void whenAskedToPresentAllStocksThenResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findAll()).thenReturn(Collections.singletonList(stockEntity));

        List<StockDTO> listDTOS = service.listAllStocks();

        assertThat(listDTOS, is(not(empty())));
        assertThat(listDTOS.get(0), is(equalTo(builderDTO)));
    }

    @Test
    void whenAskedToPresentAllStocksTodayThenResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByAllToday(LocalDate.now())).thenReturn(Optional.of(Collections.singletonList(stockEntity)));

        List<StockDTO> listDTOS = service.listAllStocksToday();

        assertThat(listDTOS, is(not(empty())));
        assertThat(listDTOS.get(0), is(equalTo(builderDTO)));
    }

    @Test
    void whenAskedToPresentAllStocksTodayThenAnNotFoundExceptionShouldBeThrow() {
        when(repository.findByAllToday(LocalDate.now())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.listAllStocksToday());
    }

    @Test
    void whenUpdateStockDTOProvidedThenReturnResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByStockUpdate(builderDTO.getName(), builderDTO.getDate(), builderDTO.getId())).thenReturn(Optional.empty());
        when(repository.save(any(Stock.class))).thenReturn(stockEntity);

        StockDTO updatedStock = service.updateStock(builderDTO);

        assertThat(updatedStock.getName(), is(equalTo(stockEntity.getName())));
        assertThat(updatedStock.getCompany(), is(equalTo(stockEntity.getCompany())));
    }

    @Test
    void whenStockInvalidIDAndStockDTOProvidedThenAnNotFoundException() throws ResourceNotFoundException {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findByStockUpdate(builderDTO.getName(), builderDTO.getDate(), builderDTO.getId())).thenReturn(Optional.of(stockEntity));

        assertThrows(ResourceNotFoundException.class, () -> service.updateStock(builderDTO));
    }

    @Test
    void whenStockDTOIDProvidedToDeleteThenReturnResponseEntityOK() {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();
        Stock stockEntity = mapper.toEntity(builderDTO);

        when(repository.findById(builderDTO.getId())).thenReturn(Optional.of(stockEntity));
        doNothing().when(repository).delete(stockEntity);

        service.deleteStock(builderDTO.getId());

        verify(repository, times(1)).findById(builderDTO.getId());
        verify(repository, times(1)).delete(stockEntity);
    }

    @Test
    void whenStockDTOInvalidIDProvidedToDeleteThenAnNotFoundException() {
        when(repository.findById(INVALID_ROOM_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteStock(INVALID_ROOM_ID));
    }
}
