package com.xblog.web;
import com.xblog.core.Result;
import com.xblog.core.ResultGenerator;
import com.xblog.model.Carousel;
import com.xblog.service.CarouselService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Resource
    private CarouselService carouselService;


    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Carousel carousel = carouselService.findById(id);
        return ResultGenerator.genSuccessResult(carousel);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "image_order asc");
        List<Carousel> list = carouselService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
