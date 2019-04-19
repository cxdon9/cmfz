package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    public List<Album> getAlbum();

    Album selectAlbumByid(String id);


    void insert(Album album);

    List<Album> selectAll();
}
