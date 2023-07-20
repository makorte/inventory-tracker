package de.maxkorte.inventorytracker.controller;

import de.maxkorte.inventorytracker.model.Item;
import de.maxkorte.inventorytracker.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    public String createItem(@ModelAttribute("item") Item item, @RequestParam("images") List<MultipartFile> images) throws IOException {
        List<String> imageFileNames = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = saveImageFile(image);
                imageFileNames.add(fileName);
            }
        }

        item.setImageFileNames(imageFileNames);
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
        List<String> itemImages = itemService.getItemImages(id);
        model.addAttribute("item", item);
        model.addAttribute("itemImages", itemImages);
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
        item.setImageFileNames(itemService.getById(id).getImageFileNames());
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/delete")
    public String deleteItem(@PathVariable("id") Long id) {
        List<String> imageFileNames = itemService.getItemImages(id);

        // Delete the images from the server
        for (String fileName : imageFileNames) {
            Path filePath = Path.of(System.getProperty("user.home") + "/inventory-manager-img", fileName);
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                // Handle the exception if the file deletion fails
                e.printStackTrace();
            }
        }

        // Delete the item from the database
        itemService.delete(id);

        return "redirect:/items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam("keyword") String keyword, Model model) {
        List<Item> searchResults = itemService.searchItems(keyword);
        model.addAttribute("items", searchResults);
        return "item-list";
    }

    private String saveImageFile(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Path.of(System.getProperty("user.home") + "/inventory-manager-img/", fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
