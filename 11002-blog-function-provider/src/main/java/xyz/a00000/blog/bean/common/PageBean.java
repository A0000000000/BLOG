package xyz.a00000.blog.bean.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageBean<T> implements Serializable {

    private Integer page;
    private Integer count;
    private Integer sum;
    private Integer total;
    private List<T> data;

    public static<T> PageBean<T> createPageBean(PageForm<T> form, Integer sum) {
        PageBean<T> bean = new PageBean<>();
        Integer count = form.getCount();
        if (count == null || count == 0) {
            count = 10;
        }
        bean.setSum(sum);
        bean.setCount(count);
        bean.setTotal((sum + count - 1) / count);
        Integer page = form.getPage();
        if (page > bean.getTotal()) {
            page = bean.getTotal();
        }
        if (page == null || page < 1) {
            page = 1;
        }
        bean.setPage(page);
        form.setCount(count);
        form.setPage(page);
        return bean;
    }

}
