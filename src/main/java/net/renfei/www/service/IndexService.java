package net.renfei.www.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.www.common.baseclass.BaseService;
import net.renfei.www.dao.VAllInfoMapper;
import net.renfei.www.entity.dbo.VAllInfoExample;
import net.renfei.www.entity.dbo.VAllInfoWithBLOBs;
import net.renfei.www.entity.dto.AllInfoDTOList;
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
        vAllInfoExample.createCriteria();
        Page page = PageHelper.startPage(intPage, intRows);
        List<VAllInfoWithBLOBs> vAllInfoWithBLOBsList = vAllInfoMapper.selectByExampleWithBLOBs(vAllInfoExample);
        AllInfoDTOList allInfoDTOList = new AllInfoDTOList();
        allInfoDTOList.setCount(page.getTotal());
        allInfoDTOList.setVAllInfoWithBLOBsList(vAllInfoWithBLOBsList);
        return allInfoDTOList;
    }
}
