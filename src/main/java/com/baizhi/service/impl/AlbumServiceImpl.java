package com.baizhi.service.impl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumDao;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("albumService")
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public List<Album> getAlbum() {
        return albumDao.getAlbum();
    }

    @Override
    public Album selectAlbumByid(String id) {
        return albumDao.selectByPrimaryKey(id);
    }

    @Override
    public void insert(Album album) {
        albumDao.insert(album);
    }

    @Override
    public List<Album> selectAll() {
        return albumDao.selectAll();
    }
}
