package com.xblog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xblog.core.ProjectConstant;
import com.xblog.core.Result;
import com.xblog.core.ResultGenerator;
import com.xblog.model.User;
import com.xblog.service.UserService;
import com.xblog.util.EmptyUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result getList(@RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "10")Integer limit){
        PageHelper.startPage(page, limit, "create_time desc");
        List<User> users = userService.findAll();
        PageInfo pageInfo = new PageInfo(users);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    public Result<User> add(@RequestBody User user){
        user.setStatus(ProjectConstant.DEFAULT_USER_STATUS);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result<User> update(@RequestBody User user){
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete")
    public Result<User> delete(Integer id){
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/login")
    public Result login(String username, String password, HttpServletRequest request) {

        if (EmptyUtil.isEmpty(username) || EmptyUtil.isEmpty(password)){
            return ResultGenerator.genFailResult("账号或密码不能为空");
        }
        User user = userService.getByUsernameAndPassword(username, DigestUtils.md5Hex(password));
        if (EmptyUtil.isEmpty(user)){
            return ResultGenerator.genFailResult("账号或者密码错误");
        }

        request.getSession().setAttribute("user", user.getUsername());
        Map<String, String> tokenMap = new HashMap<>();
        String token = getToken();
        tokenMap.put("token", token);
        request.getSession().setAttribute("token", token);
        return ResultGenerator.genSuccessResult(tokenMap);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if (EmptyUtil.isEmpty(user.getUsername()) || EmptyUtil.isEmpty(user.getPassword())){
            return ResultGenerator.genFailResult("账号或密码不能为空");
        }
        user.setStatus(ProjectConstant.DEFAULT_USER_STATUS);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setRole(ProjectConstant.DEFAULT_USER_ROLE);
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null) {
            return ResultGenerator.genFailResult("注销失败，当前并没有用户登录！");
        }
        request.getSession().setAttribute("user", null);
        return ResultGenerator.genSuccessResult("注销成功！");
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

}
