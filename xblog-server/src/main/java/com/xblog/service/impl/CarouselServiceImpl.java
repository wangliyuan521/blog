package com.xblog.service.impl;

import com.xblog.core.AbstractService;
import com.xblog.dao.CarouselMapper;
import com.xblog.model.Carousel;
import com.xblog.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CarouselServiceImpl extends AbstractService<Carousel> implements CarouselService {
    @Resource
    private CarouselMapper carouselMapper;

}
