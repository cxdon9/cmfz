package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {

    public void insert(Banner banner);

    public void delete(Integer id);

    public void update(Banner banner);

    public Map selectAll(int page, int rows);

}
