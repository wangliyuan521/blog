package com.xblog.service.impl;

import com.xblog.dao.ReaderMapper;
import com.xblog.model.Reader;
import com.xblog.service.ReaderService;
import com.xblog.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ReaderServiceImpl extends AbstractService<Reader> implements ReaderService {
    @Resource
    private ReaderMapper readerMapper;

    @Override
    public Integer saveAndGetId(Reader reader) {
        readerMapper.insert(reader);
        return reader.getId();
    }
}
