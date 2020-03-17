package com.xblog.service;
import com.xblog.model.Reader;
import com.xblog.core.Service;


public interface ReaderService extends Service<Reader> {
    // 持久化并返回id
    Integer saveAndGetId(Reader reader);
}
