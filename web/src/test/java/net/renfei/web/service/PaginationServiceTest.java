package net.renfei.web.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import net.renfei.web.entity.PaginationVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class PaginationServiceTest extends TestApplication {
    @Autowired
    private PaginationService paginationService;

    @Test
    public void test() {
        List<PaginationVO> paginationVOS = null;
        paginationVOS = paginationService.getPagination(1, 20, "/page/");
        print(paginationVOS);
        log.info("========");
        paginationVOS = paginationService.getPagination(1, 5, "/page/");
        print(paginationVOS);
        log.info("========");
        paginationVOS = paginationService.getPagination(9, 10, "/page/");
        print(paginationVOS);
        log.info("========");
        paginationVOS = paginationService.getPagination(3, 4, "/page/");
        print(paginationVOS);
        log.info("========");
        paginationVOS = paginationService.getPagination(1, 2, "/page/");
        print(paginationVOS);
        log.info("========");
        paginationVOS = paginationService.getPagination(1, 1, "/page/");
        print(paginationVOS);
        log.info("========");
    }

    private void print(List<PaginationVO> paginationVOS) {
        for (PaginationVO obj : paginationVOS
        ) {
//            log.info("Page:" + obj.getPage() + " Lik:" + obj.getLink());
        }
    }
}
