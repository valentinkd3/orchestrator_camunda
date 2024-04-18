package ru.kozhevnikov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kozhevnikov.marketplace.domain.Stock;

@Repository
public interface ItemRepository extends JpaRepository<Stock, Long> {
}
