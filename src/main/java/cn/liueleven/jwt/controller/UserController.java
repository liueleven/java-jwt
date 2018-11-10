package cn.liueleven.jwt.controller;


import cn.liueleven.jwt.model.User;
import cn.liueleven.jwt.service.UserService;
import cn.liueleven.jwt.utils.JWTUtils;
import cn.liueleven.jwt.utils.Result;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 测试jwt
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
        return userService.selectUsers();
    }


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String toLogin() {
        return "index";
    }





    /**
     * 用户登录，账号密码校验后，生成token，并且把token存在redis中，刷新token的有效时间
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public String login(@RequestBody User u) {

        User user = userService.findUser(u);
        if(user == null) {
            return "用户不存在！";
        }
        String token = JWTUtils.genToken(ImmutableMap.of("email",user.getEmail(),
                "name",user.getName(),"phone",user.getPhone()));
        // 刷新
        refreshTokenForUser(token,user.getEmail());
        user.setToken(token);
        return token;
    }

    /**
     * 刷新token过期时间
     * @param token
     * @param email
     * @return
     */
    private String refreshTokenForUser(String token,String email) {
        stringRedisTemplate.opsForValue().set(email,token);
        stringRedisTemplate.expire(email, 30, TimeUnit.MINUTES);
        return token;
    }



    /**
     * 模拟验证用户是否登录
     * @param request
     * @return
     */
    @RequestMapping("/user/verify")
    @ResponseBody
    public Result getUserIsLogin(HttpServletRequest request) {
        String token = request.getHeader("auth");
        Map<String, String> map = JWTUtils.verifyToken(token);
        String email = map.get("email");
        long expire = stringRedisTemplate.getExpire(email);
        // 如果没有过期，说明是登录状态，更新过期时间
        if(expire > 0) {
            refreshTokenForUser(token, email);
        }else {
            return new Result(Result.ERROR,Result.FAIL);
        }
        User user = userService.findByEmail(email);
        return new Result(user);
    }

    /**
     * 模拟用户退出登录，让token失效
     * @param token
     */
    @GetMapping("/user/invalidate")
    @ResponseBody
    public String invalidate(@RequestParam  String token) {
        Map<String, String> map = JWTUtils.verifyToken(token);
        String email = map.get("email");
        stringRedisTemplate.delete(email);
        return "用户token失效成功！现在获取不到用户信息了。。。";
    }



}
