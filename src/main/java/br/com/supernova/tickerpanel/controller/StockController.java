package br.com.supernova.tickerpanel.controller;

import br.com.supernova.tickerpanel.exception.ResourceAlreadyRegisteredException;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping(value = "/stocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockDTO>> returnAllStocks(){
        return ResponseEntity.ok(service.listAllStocks());
    }

    @GetMapping(value = "/stock-name/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> searchStockDTOByName(@PathVariable String name) {
        return ResponseEntity.ok(service.checkStockName(name));
    }

    @GetMapping(value = "/stock-id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> searchStockDTOByID(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkStockID(id));
    }

    @PostMapping(value = "/stock-create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StockDTO> registerStockDTO(@Valid @RequestBody StockDTO stockDTO) throws ResourceAlreadyRegisteredException {
        return ResponseEntity.ok(service.createStock(stockDTO));
    }

    @PutMapping(value = "/stock-update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> updateStockDTO(@Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(service.updateStock(stockDTO));
    }

    @DeleteMapping(value = "/stock-delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> deleteStockDTO(@PathVariable Long id) {
        Map<String, Boolean> mapDelete = new HashMap<>();
        mapDelete.put("Record deleted", true);
        service.deleteStock(id);
        return ResponseEntity.ok(mapDelete);
    }
}
