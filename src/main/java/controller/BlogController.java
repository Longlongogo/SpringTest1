package controller;

import model.BlogEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repository.BlogRepository;
import repository.UserRepository;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;
    // 查看所有博文
    @RequestMapping(value = "/admin/blogs",method = RequestMethod.GET)
    public String showBlogs(ModelMap modelMap){
        List<BlogEntity> blogEntityList = blogRepository.findAll();
        modelMap.addAttribute("blogList",blogEntityList);
        return "admin/blogs";
    }

    @RequestMapping(value = "/admin/blogs/add",method = RequestMethod.GET)
    public String addBlog(ModelMap modelMap){
        List<UserEntity> userEntities = userRepository.findAll();
        modelMap.addAttribute("userList",userEntities);
        return "admin/addBlog";
    }

    @RequestMapping(value = "/admin/blogs/addPost",method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") BlogEntity blogEntity){
        System.out.println(blogEntity.getTitle());
        System.out.println(blogEntity.getUserByUserId().getFirstName());
        blogRepository.saveAndFlush(blogEntity);
        return "redirect:/admin/blogs";
    }

    @RequestMapping(value = "admin/blogs/show/{id}")
    public String showBlog(@PathVariable("id") int id,ModelMap modelMap){
        BlogEntity blog = blogRepository.findById(id).get();
        modelMap.addAttribute("blog",blog);
        return "admin/blogDetail";
    }

    @RequestMapping(value = "/admin/blogs/update/{id}")
    public String updateBlog(@PathVariable("id") int id,ModelMap modelMap){
        BlogEntity blogEntity = blogRepository.findById(id).get();
        List<UserEntity> userEntities = userRepository.findAll();
        modelMap.addAttribute("blog",blogEntity);
        modelMap.addAttribute("userList",userEntities);
        return  "admin/updateBlog";
    }

    @RequestMapping(value = "admin/blogs/updateP",method = RequestMethod.POST)
    public String updateBlogPost(@ModelAttribute("blogP") BlogEntity blogEntity){
        blogRepository.updateBlogs(blogEntity.getTitle(),blogEntity.getUserByUserId().getId(),blogEntity.getContent()
                ,blogEntity.getPubDate(),blogEntity.getId());
        blogRepository.flush();
        return "redirect:/admin/blogs"; 
    }

    @RequestMapping(value = "admin/blogs/delete/{id}",method = RequestMethod.GET)
    public String deleteBlog(@PathVariable("id") int id){
        blogRepository.deleteById(id);
        blogRepository.flush();
        return "redirect:/admin/blogs";
    }

}
