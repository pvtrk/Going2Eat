package pl.camp.it.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.camp.it.model.Restaurant;

import java.util.Collections;
import java.util.List;
@Service
public class PageService<T> {
    public Page<T> findPaginated(Pageable pageable, List<T> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<T> list;

        if(pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }

        Page<T> page =
                new PageImpl<T>(list, PageRequest.of(currentPage, pageSize), pageList.size());

        return (Page<T>) page;
    }
}
