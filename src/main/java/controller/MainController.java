package controller;

import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repository.UserRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//@Controller注解：采用注解的方式，可以明确地定义该类为处理请求的Controller类；
@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

//    @RequestMapping()注解：用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post；
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";//处理完该请求后返回的页面，此请求返回 index.jsp页面。
    }

    @RequestMapping(value = "/admin/users",method = RequestMethod.GET)
    public String getUser(ModelMap modelMap){
        //查询表中所有的记录
        List<UserEntity> userList = userRepository.findAll();
        //将所有记录返回到jsp页面，放在userList中
        modelMap.addAttribute("userList",userList);
        //返回的页面
        return "admin/users";
    }
    @RequestMapping(value = "/admin/users/add",method=RequestMethod.GET)
    public String addUser(){
        return "admin/addUser";
    }

    @RequestMapping(value = "/admin/users/addP",method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") UserEntity userEntity){
        // 注意此处，post请求传递过来的是一个UserEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'user'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存
        //userRepository.save(userEntity);

        // 数据库中添加一个用户，并立即刷新缓存
        userRepository.saveAndFlush(userEntity);

        // 重定向到用户管理页面，方法为 redirect:url
        return "redirect:/admin/users";
    }
}
