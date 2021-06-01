package br.com.supernova.tickerpanel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.time.LocalDate;

@Entity(name = "Stock")
@Table(name = "tbl_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String name;

    @Column(name = "companhia", nullable = false)
    private String company;

    @DecimalMin(value = "0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "preco", nullable = false)
    private Double price;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @Digits(integer = 5, fraction = 2)
    @Column(name = "variacao", nullable = false)
    private Double variation;
}

/*
 * CREATE DATABASE tickerpanel;
 *
 * USE tickerpanel;
 *
 * CREATE TABLE tbl_stock(
 *  id INT NOT NULL AUTO_INCREMENT,
 *  nome VARCHAR(6) NOT NULL,
 *  companhia VARCHAR(40) NOT NULL,
 *  preco FLOAT(8,2) NOT NULL,
 *  data DATE NOT NULL,
 *  variacao FLOAT(5,2) NOT NULL,
 *  PRIMARY KEY (id));
 *
 * SET character_set_client = utf8;
 * SET character_set_connection = utf8;
 * SET character_set_results = utf8;
 * SET collation_connection = utf8_general_ci;
 *
 */