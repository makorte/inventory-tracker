package de.maxkorte.inventorytracker.repository;

import de.maxkorte.inventorytracker.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findByNameContainingIgnoreCaseOrCustomerContainingIgnoreCase(String name, String customer);
}
