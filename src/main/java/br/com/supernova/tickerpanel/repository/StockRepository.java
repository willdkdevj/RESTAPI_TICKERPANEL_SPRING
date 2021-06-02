package br.com.supernova.tickerpanel.repository;

import br.com.supernova.tickerpanel.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByName(String name);

    Optional<Stock> findByNameAndDate(String name, LocalDate date);

    @Query(value = "SELECT stock " +
           "FROM tbl_stock stock" +
           "WHERE stock.nome = :name AND stock.data = :date AND stock.id <> :id ", nativeQuery = true)
    Optional<Stock> findByStockUpdate(String name, LocalDate date, Long id);

    @Query(value = "SELECT stock " +
            "FROM tbl_stock stock " +
            "WHERE stock.data = :date", nativeQuery = true)
    Optional<List<Stock>> findByToday(LocalDate date);
}
