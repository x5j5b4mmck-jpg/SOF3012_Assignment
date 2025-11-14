package com.poly.asm.dao;

import com.poly.asm.entity.Video;
import java.util.List;
import javax.persistence.TypedQuery;

public class VideoDAO extends AbstractDAO<Video> {
    public VideoDAO() {
        super();
    }
    
    // Ví dụ: Lấy 6 video có lượt xem nhiều nhất (theo yêu cầu)
    public List<Video> findTop6ByViews() {
        String jsql = "SELECT o FROM Video o WHERE o.active = true ORDER BY o.views DESC";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setMaxResults(6); // Lấy tối đa 6
        return query.getResultList();
    }
    
    // Thêm các phương thức phân trang...
}