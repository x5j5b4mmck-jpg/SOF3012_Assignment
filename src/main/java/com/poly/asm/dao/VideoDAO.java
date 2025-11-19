package com.poly.asm.dao;

import com.poly.asm.entity.Video;
import com.poly.asm.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
    
 
    public List<Video> findTop5Related(String excludeVideoId) {
        String jsql = "SELECT o FROM Video o WHERE o.active = true AND o.id != :excludeId ORDER BY o.views DESC";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setParameter("excludeId", excludeVideoId);
        query.setMaxResults(5); // Lấy 5 video
        return query.getResultList();
    }
    /**
     * Đếm tổng số video
     */
    public long count() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jsql = "SELECT count(o) FROM Video o";
            TypedQuery<Long> query = em.createQuery(jsql, Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Lấy video theo phân trang
     * @param pageNumber Số trang (bắt đầu từ 1)
     * @param pageSize Số lượng/trang
     */
    public List<Video> findAll(int pageNumber, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jsql = "SELECT o FROM Video o";
            TypedQuery<Video> query = em.createQuery(jsql, Video.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    //tao video moi
    public Video create(Video entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();          // 1. Bắt đầu
            em.persist(entity);     // 2. Lưu
            trans.commit();         // 3. Xác nhận (Quan trọng)
            return entity;
        } catch (Exception e) {
            trans.rollback();       // Quay lui nếu lỗi
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
    /**
     * GHI ĐÈ PHƯƠNG THỨC DELETE ĐỂ XÓA KHÓA NGOẠI
     * Xóa Favorite và Share trước, sau đó mới xóa Video
     */
    @Override
    public Video delete(Video entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            
            // 1. Tìm video thực tế trong DB để đảm bảo nó tồn tại
            Video video = em.find(Video.class, entity.getId());
            
            if (video != null) {
                // 2. Xóa sạch dữ liệu liên quan ở bảng Favorite (Người đã Like video này)
                String sqlFavorite = "DELETE FROM Favorite f WHERE f.video.id = :vid";
                em.createQuery(sqlFavorite)
                  .setParameter("vid", video.getId())
                  .executeUpdate();
                  
                // 3. Xóa sạch dữ liệu liên quan ở bảng Share (Người đã Share video này)
                String sqlShare = "DELETE FROM Share s WHERE s.video.id = :vid";
                em.createQuery(sqlShare)
                  .setParameter("vid", video.getId())
                  .executeUpdate();
                  
                // 4. Sau khi sạch sẽ rồi thì mới xóa Video
                em.remove(video);
            }
            
            trans.commit();
            return entity;
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}