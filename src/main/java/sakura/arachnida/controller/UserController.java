package sakura.arachnida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sakura.arachnida.models.User;
import sakura.arachnida.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // List users with pagination, sorting, and filtering
    @GetMapping
    public String listUsers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "login") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDirection,
                            @RequestParam(required = false) String filterRole,
                            @RequestParam(required = false) Boolean isDeleted,
                            Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<User> users = userService.findAllWithPaginationAndFilters(pageable, filterRole, isDeleted);

        model.addAttribute("users", users.getContent());
        model.addAttribute("currentPage", users.getNumber());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("filterRole", filterRole);
        model.addAttribute("isDeleted", isDeleted);

        return "userPage"; // This is your Thymeleaf template for users
    }



    // Create a new user
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    // Edit/update user
    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user) {
        userService.saveUser(user); // This saves the updated user
        return "redirect:/users"; // Redirects to the current page after edit
    }



    // Single logical deletion
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUserPermanently(id);
        return "redirect:/users";
    }

    // Restore a user
    @PostMapping("/restore")
    public String restoreUser(@RequestParam("id") Integer id) {
        userService.restoreMultipleUsers(List.of(id));
        return "redirect:/users";
    }

    // Multiple logical deletion
    @PostMapping("/deleteSelected")
    public String deleteSelectedUsers(@RequestParam("ids") List<Integer> ids) {
        userService.softDeleteMultipleUsers(ids);
        return "redirect:/users";
    }

    // Multiple physical deletion
    @PostMapping("/deleteSelectedPermanently")
    public String deleteSelectedUsersPermanently(@RequestParam("ids") List<Integer> ids) {
        userService.deleteMultipleUsersPermanently(ids);
        return "redirect:/users";
    }

    // Single physical deletion
    @PostMapping("/deletePermanently")
    public String deleteUserPermanently(@RequestParam("id") Integer id) {
        userService.deleteUserPermanently(id);
        return "redirect:/users";
    }
}