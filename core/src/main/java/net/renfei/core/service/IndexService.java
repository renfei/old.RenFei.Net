package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.persistences.VAllInfoMapper;
import net.renfei.dao.entity.VAllInfoExample;
import net.renfei.dao.entity.VAllInfoWithBLOBs;
import net.renfei.core.entity.AllInfoDTOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService extends BaseService {
    @Autowired
    private VAllInfoMapper vAllInfoMapper;

    public AllInfoDTOList getAllInfo(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        VAllInfoExample vAllInfoExample = new VAllInfoExample();
        vAllInfoExample.setOrderByClause("release_time DESC");
        vAllInfoExample.createCriteria();
        Page page = PageHelper.startPage(intPage, intRows);
        List<VAllInfoWithBLOBs> vAllInfoWithBLOBsList = vAllInfoMapper.selectByExampleWithBLOBs(vAllInfoExample);
        AllInfoDTOList allInfoDTOList = new AllInfoDTOList();
        allInfoDTOList.setCount(page.getTotal());
        allInfoDTOList.setVAllInfoWithBLOBsList(vAllInfoWithBLOBsList);
        return allInfoDTOList;
    }
}
