package com.weolbu.admin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class PagingUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PagingUtil.class);

    public static String pagingHtml(Page page, String path) {
        int pageNum = page.getNumber()+1; // 현재 페이지 번호
        int startPageNum = (pageNum -1) / 10 * 10 + 1;
        int endPageNum = startPageNum + 10 - 1;
        if (endPageNum > page.getTotalPages()) {
            endPageNum = page.getTotalPages();
        }
        String active = "active";

        StringBuffer buffer = new StringBuffer();

        buffer.append("<div class=\"dataTables_paginate paging_simple_numbers\" id=\"paginate\">");
        buffer.append("<ul class=\"pagination\">");
        // 이전 페이지로 이동

        if(page.hasPrevious()) {
            int prevBlock = pageNum-1;
            buffer.append("<li class=\"paginate_button page-item previous \" id=\"previous\">");
            buffer.append("<a href=\"").append(path).append("?page=").append(prevBlock).append("\" aria-controls=\"previous\" data-dt-idx=\"").append(prevBlock).append("\"tabindex=\"0\" class=\"page-link\">Previous</a>");
        } else {
            int prevBlock = pageNum-1;
            buffer.append("<li class=\"paginate_button page-item previous disabled \" id=\"previous\">");
            buffer.append("<a href=\"").append(path).append("?page=").append(prevBlock).append("\" aria-controls=\"previous\" data-dt-idx=\"").append(prevBlock).append("\"tabindex=\"0\" class=\"page-link\">Previous</a>");
        }
        buffer.append("</li>");

        for(int i = startPageNum; i<=endPageNum;i++) {
            if (i != pageNum) {
                active = "";
            } else {
                active = "active";
            }
            buffer.append("<li class=\"paginate_button page-item ").append(active).append("\">");
            buffer.append("<a href=\"").append(path).append("?page=").append(i).append("\" aria-controls=\"pageNo-").append(i).append("\" data-dt-idx=\"").append(pageNum).append("\" tabindex=\"0\" class=\"page-link\">").append(i).append("</a>");
            buffer.append("</li>");
        }

        // 다음 페이지로 이동
        if(page.hasNext()) {
            int nextBlock = pageNum+1;
            buffer.append("<li class=\"paginate_button page-item next \" id=\"next\">");
            buffer.append("<a href=\"").append(path).append("?page=").append(nextBlock).append("\" aria-controls=\"endPage\" data-dt-idx=\"").append(nextBlock).append("\"tabindex=\"0\" class=\"page-link\">Next</a>");
            buffer.append("</li>");
        } else {
            int nextBlock = pageNum+1;
            buffer.append("<li class=\"paginate_button page-item next disabled \" id=\"next\">");
            buffer.append("<a href=\"").append(path).append("?page=").append(nextBlock).append("\" aria-controls=\"endPage\" data-dt-idx=\"").append(nextBlock).append("\"tabindex=\"0\" class=\"page-link\">Next</a>");
            buffer.append("</li>");
        }
        buffer.append("</ul>");
        buffer.append("</div>");
        return buffer.toString();
    }




}

