package com.office.test.board.page;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class PageMaker {

    private int startPage;
    private int endPage;
    private boolean prev, next;
    private int total;
    private int totalPage;
    private Criteria criteria;

    public PageMaker(Criteria criteria, int total) {
        log.info("PageMaker CONSTRUCTOR");

        this.criteria = criteria;
        this.total = total;

        this.endPage = (int) (Math.ceil(criteria.getPageNum() / 10.0)) * 10;
        this.startPage = this.endPage - 9;

        int realEnd = (int) (Math.ceil(total * 1.0 / criteria.getAmount()));
        if (realEnd < this.endPage)
            this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        this.totalPage = total / criteria.getAmount();
        if (total % criteria.getAmount() != 0)
            totalPage += 1;

    }

}
