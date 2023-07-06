package de.maxkorte.inventorytracker.controller;

import de.maxkorte.inventorytracker.model.Item;
import de.maxkorte.inventorytracker.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/new")
    public String showItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "item-form";
    }

    @PostMapping
    public String createItem(@ModelAttribute("item") Item item) {
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping
    public String showItemList(Model model) {
        Set<Item> items = itemService.getAll();
        model.addAttribute("items", items);
        return "item-list";
    }

    @GetMapping("/{id}")
    public String showItemDetails(@PathVariable("id") Long id, Model model) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        return "item-details";
    }

    @GetMapping("/{id}/edit")
    public String showEditItemForm(@PathVariable("id") Long id, Model model) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        return "item-form";
    }

    @PostMapping("/{id}/edit")
    public String updateItem(@PathVariable("id") Long id, @ModelAttribute("item") Item item) {
        item.setId(id);
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/delete")
    public String deleteItem(@PathVariable("id") Long id) {
        itemService.delete(id);
        return "redirect:/items";
    }
}

