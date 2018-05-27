package com.ecjtu.erp.controller;

import com.ecjtu.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:01 2018/4/30
 * @Modified By:
 */
@RestController
public class ViewController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    private ModelAndView index(){
        return new ModelAndView("pages/index")
                .addObject("index", userService.index())
                .addObject("menusShow", userService.menusAndChilds());
    }

    @GetMapping("/error404")
    private ModelAndView error404(){
        return new ModelAndView("pages/examples/404");
    }

    @GetMapping("/error403")
    private ModelAndView error403(){
        return new ModelAndView("pages/examples/403");
    }

    @GetMapping("/error500")
    private ModelAndView error500(){
        return new ModelAndView("pages/examples/500");
    }
}
