package de.maxkorte.inventorytracker.service;

import de.maxkorte.inventorytracker.model.Item;
import de.maxkorte.inventorytracker.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Set<Item> getAll() {
        Set<Item> items = new HashSet<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Item getById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
