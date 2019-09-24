package controller;

import model.BlogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repository.BlogRepository;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    BlogRepository blogRepository;

    // 查看所有博文
    @RequestMapping(value = "/admin/blogs",method = RequestMethod.GET)
    public String showBlogs(ModelMap modelMap){
        List<BlogEntity> blogEntityList = blogRepository.findAll();
        modelMap.addAttribute("blogList",blogEntityList);
        return "admin/blogs";
    }
}
