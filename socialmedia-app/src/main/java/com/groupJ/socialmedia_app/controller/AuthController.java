package com.groupJ.socialmedia_app.controller;

import com.groupJ.socialmedia_app.dto.PostDTO;
import com.groupJ.socialmedia_app.dto.RegisterDTO;
import com.groupJ.socialmedia_app.entity.Post;
import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.service.FriendService;
import com.groupJ.socialmedia_app.service.PostService;
import com.groupJ.socialmedia_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendService friendService;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("registerDTO",new RegisterDTO());
        return "register";
    }

    @PostMapping("/process-register")
    public String registerUser(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors())
            return "register";

        try {
            userService.registerUser(registerDTO);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal){

        String email = principal.getName();

        // Own posts
        List<Post> myPosts = postService.getPostByUser(email);

        // Friend posts
        List<User> friends = friendService.getAcceptedFriends(email);
        List<Post> friendPosts = postService.getPostsByUsers(friends);

        // Merge and sort
        List<Post> allPosts = new ArrayList<>();
        allPosts.addAll(myPosts);
        allPosts.addAll(friendPosts);

        allPosts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        model.addAttribute("posts", allPosts);
        model.addAttribute("postDTO", new PostDTO());

        return "home";
    }

    @PostMapping("/post")
    public String submitPost(@Valid @ModelAttribute("postDTO") PostDTO postDTO,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal){
        if (bindingResult.hasErrors()){
            String userEmail = principal.getName();
            List<Post> posts = postService.getPostByUser(userEmail);
            model.addAttribute("posts",posts);
            return "home";
        }

        postService.createPost(postDTO, principal.getName());
        return "redirect:/home";
    }
}
