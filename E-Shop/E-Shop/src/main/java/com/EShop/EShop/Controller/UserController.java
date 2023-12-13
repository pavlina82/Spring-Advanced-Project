package com.EShop.EShop.controller;

import com.EShop.EShop.model.binding.UserRegisterBindingModel;
import com.EShop.EShop.model.service.UserServiceModel;
import com.EShop.EShop.model.view.UsersViewModel;
import com.EShop.EShop.service.UserService;
import com.EShop.EShop.web.anotacions.PageTitle;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView login(@RequestParam(required = false) String error, ModelAndView modelAndView) {

        if (error != null) {
            modelAndView.addObject("error", error);
        }
        return view("/login", modelAndView);
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("register")
    public ModelAndView register(@ModelAttribute(name = "model") UserRegisterBindingModel model,
                                 ModelAndView modelAndView) {
        modelAndView.addObject("model", model);
        return view("register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute(name = "model") UserRegisterBindingModel model,
                                 BindingResult bindingResult, ModelAndView modelAndView) {

        if (!model.getPassword().equals(model.getConfirmPassword()) || bindingResult.hasErrors()
                || this.userService.register(modelMapper.map(model, UserServiceModel.class)) == null) {
            modelAndView.addObject("model", model);
            return view("register", modelAndView);
        }


        return redirect("/login");
    }

    @GetMapping("/user/profile/{username}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("User Profile")
    public ModelAndView renderProfilePageByUsername(@PathVariable("username")
                                                    String username, ModelAndView modelAndView) {

        UserServiceModel userServiceModel = this.userService.findByUsername(username);
        UsersViewModel usersViewModel = this.modelMapper.map(userServiceModel, UsersViewModel.class);
        modelAndView.addObject("viewModel", usersViewModel);
        return view("/profile", modelAndView);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("users")
    public ModelAndView renderAllUsersPage() {

        return view("/users-all");
    }

    @PostMapping("/users/edit/role/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateUserRole(@PathVariable("id") Long id, String role, Principal principal) {
        UserServiceModel currentLoggedUser = this.userService.findUserByUserName(principal.getName());
        UserServiceModel targetUser = userService.findById(id);
        if (role == null) {
            return redirect("/user/profile/" + targetUser.getUsername());
        }
        if (currentLoggedUser.getId().equals(id)) {
            return redirect("/user/profile/" + principal.getName());
        }
        try {
            this.userService.updateRole(id, role);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return redirect("/user/profile/" + targetUser.getUsername());

    }

    @GetMapping("/api/users")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UsersViewModel> allUsers() {

        return this.userService.findAllUsers()
                .stream()
                .map(serviceModel -> this.modelMapper.map(serviceModel, UsersViewModel.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/api/users/find")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UsersViewModel allUsers(@RequestParam("username") String username) {
        UserServiceModel byUsername = this.userService.findByUsername(username);
        return byUsername == null ? new UsersViewModel()
                : this.modelMapper.map(byUsername, UsersViewModel.class);

    }

    private String htmlEscape(String input) {
        input = input.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;");
        return input;
    }
}
