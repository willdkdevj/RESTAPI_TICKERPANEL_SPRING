package br.com.supernova.tickerpanel.controller;

import br.com.supernova.tickerpanel.builder.StockDTOBuilder;
import br.com.supernova.tickerpanel.exception.ResourceAlreadyRegisteredException;
import br.com.supernova.tickerpanel.exception.ResourceNotFoundException;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static br.com.supernova.tickerpanel.utils.UtilJasonToString.jsonAsString;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    private static final String URL_PATH = "/api/v1";
    private static final Long INVALID_ID = 2L;
    private static final String PRE_PATH_FIND_ALL = "/stocks";
    private static final String PRE_PATH_FIND_ALL_TODAY = "/stocks-today";
    private static final String PRE_PATH_FIND_BY_ID = "/stock-id";
    private static final String PRE_PATH_FIND_BY_NAME = "/stock-name";
    private static final String PRE_PATH_CREATE = "/stock-create";
    private static final String PRE_PATH_UPDATE = "/stock-update";
    private static final String PRE_PATH_DELETE = "/stock-delete";

    private MockMvc mockMvc;

    @InjectMocks
    private StockController controller;

    @Mock
    private StockService service;

    @BeforeEach
    void SetUp() {
        controller = new StockController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAStockDTOIsCreated() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.createStock(builderDTO)).thenReturn(builderDTO);

        mockMvc.perform(post(URL_PATH + PRE_PATH_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAsString(builderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.company", is(builderDTO.getCompany())))
                .andExpect(jsonPath("$.price", is(builderDTO.getPrice())))
                .andExpect(jsonPath("$.variation", is(builderDTO.getVariation())));
                
    }

    @Test
    void whenPOSTIsCalledThenAndExceptionThrown() throws Exception {
        StockDTO stockDTO = StockDTOBuilder.builder().build().toStockDTO();
        stockDTO.setName(null);

        mockMvc.perform(post(URL_PATH + PRE_PATH_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAsString(stockDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenGETIsCalledToListAllStocksThenIsReturnedOK() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.listAllStocks()).thenReturn(Collections.singletonList(builderDTO));

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_ALL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(builderDTO.getName())))
                .andExpect(jsonPath("$[0].company", is(builderDTO.getCompany())))
                .andExpect(jsonPath("$[0].price", is(builderDTO.getPrice())))
                .andExpect(jsonPath("$[0].variation", is(builderDTO.getVariation())));
    }

    @Test
    void whenGETIsCalledToListAllStocksTodayThenIsReturnedOK() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.listAllStocksToday()).thenReturn(Collections.singletonList(builderDTO));

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_ALL_TODAY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(builderDTO.getName())))
                .andExpect(jsonPath("$[0].company", is(builderDTO.getCompany())))
                .andExpect(jsonPath("$[0].price", is(builderDTO.getPrice())))
                .andExpect(jsonPath("$[0].variation", is(builderDTO.getVariation())));
    }

    @Test
    void whenGETIsCalledToListAllStocksTodayThenAnExceptionThrown() throws Exception {
        when(service.listAllStocksToday()).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_ALL_TODAY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETIsCalledByNameStockThenIsReturnedOK() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.checkStockName(builderDTO.getName())).thenReturn(builderDTO);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_NAME + "/" + builderDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.company", is(builderDTO.getCompany())))
                .andExpect(jsonPath("$.price", is(builderDTO.getPrice())))
                .andExpect(jsonPath("$.variation", is(builderDTO.getVariation())));
    }

    @Test
    void whenGETIsCalledByNameStockThenAnExceptionThrown() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.checkStockName(builderDTO.getName())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_NAME + "/" + builderDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenGETIsCalledByStockIDThenIsReturnedOK() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.checkStockID(builderDTO.getId())).thenReturn(builderDTO);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_ID + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.company", is(builderDTO.getCompany())))
                .andExpect(jsonPath("$.price", is(builderDTO.getPrice())))
                .andExpect(jsonPath("$.variation", is(builderDTO.getVariation())));
    }

    @Test
    void whenGETIsCalledByStockIDThenAnExceptionThrown() throws Exception {
        StockDTO builderDTO = StockDTOBuilder.builder().build().toStockDTO();

        when(service.checkStockID(builderDTO.getId())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_ID + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
