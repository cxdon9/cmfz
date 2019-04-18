package com.baizhi.service.impl;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerDao;
import com.baizhi.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bannerService")
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    public void delete(Integer id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKey(banner);
    }

    @Override
    public Map selectAll(int page, int rows) {

        Map map = new HashMap();
        //1,5
        PageHelper.startPage(page, rows);
        List<Banner> list = bannerDao.selectAll();
        PageInfo pageInfo = new PageInfo<>(bannerDao.selectAll());
        long total = pageInfo.getTotal();
        map.put("rows", list);
        map.put("total", total);
        return map;

    }
}
