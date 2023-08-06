package de.maxkorte.inventorytracker.controller;

import com.google.zxing.WriterException;
import de.maxkorte.inventorytracker.model.Item;
import de.maxkorte.inventorytracker.service.ItemService;
import de.maxkorte.inventorytracker.util.FileUtil;
import de.maxkorte.inventorytracker.util.QRCodeUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    @Value("${inventorytracker.baseUrl}")
    private String baseUrl;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/new")
    public String showItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "item-form";
    }

    @PostMapping
    public String createItem(@ModelAttribute("item") Item item, @RequestParam("images") List<MultipartFile> images) {
        List<String> imageFileNames = FileUtil.saveImageFiles(images);

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
        FileUtil.deleteImageFiles(itemService.getItemImages(id));
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam("keyword") String keyword, Model model) {
        List<Item> searchResults = itemService.searchItems(keyword);
        model.addAttribute("items", searchResults);
        return "item-list";
    }

    @GetMapping("/{id}/qr")
    public void downloadQRCode(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, WriterException {
        Item item = itemService.getById(id);

        String content = baseUrl + "/items/" + item.getId();
        String fileName = item.getName() + ".png";

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("image/png");

        QRCodeUtil.generateQRCode(content, response.getOutputStream());
    }
}
