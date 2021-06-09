package com.github.CCweixiao.starter.example.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>测试的一个controller</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@RestController
public class IndexController {
    /*@Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public UserPojo getUser() {
        return userService.getUser("10001");
    }

    @GetMapping("/getUser2")
    public List<Map<String, Object>> getUser2(){
        return userService.getDataWithMapper();
    }*/

    @GetMapping("/getUser")
    public String getUser(@RequestParam(defaultValue = "", name = "token") String token) {
        System.out.println(token);
        //https://web-sso-sandbox.intsig.net/login?platform_id=jFfGyeOvKBp66iJCSt3xTv7MoSZOLwzT&redirect=http%3A%2F%2Flocalhost%3A8088/getUser
        return "leo jie " + System.currentTimeMillis();
    }

}
