package com.poly.asm.dao;

import com.poly.asm.entity.User;
import com.poly.asm.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserDAO extends AbstractDAO<User> {
    
    public UserDAO() {
        super();
    }
    
    public User findByEmail(String email) {
        String jsql = "SELECT o FROM User o WHERE o.email = :email";
        TypedQuery<User> query = em.createQuery(jsql, User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy
        }
    }

    // Phương thức check login
    public User checkLogin(String userId, String password) {
        String jsql = "SELECT o FROM User o WHERE o.id = :uid AND o.password = :pass";
        TypedQuery<User> query = em.createQuery(jsql, User.class);
        query.setParameter("uid", userId);
        query.setParameter("pass", password);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Sai login
        }
    }
    /**
     * Đếm tổng số người dùng
     */
    public long count() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jsql = "SELECT count(o) FROM User o";
            TypedQuery<Long> query = em.createQuery(jsql, Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Lấy người dùng theo phân trang
     * @param pageNumber Số trang (bắt đầu từ 1)
     * @param pageSize Số lượng/trang
     */
    public List<User> findAll(int pageNumber, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jsql = "SELECT o FROM User o";
            TypedQuery<User> query = em.createQuery(jsql, User.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}