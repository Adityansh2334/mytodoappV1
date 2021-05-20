package com.note.indiatodo.dao;


import com.note.indiatodo.entity.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDao {
    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
    public Session session;
        public void saveUserData(Users users){
            try {
                session = getSession();
                Transaction transaction = session.beginTransaction();
                session.save(users);
                transaction.commit();
            }catch (HibernateException  e){
                e.printStackTrace();
            }
        }
        public Users getUsersDataById(Long id){
            Users users=null;
            try{
                session = getSession();
                users=session.get(Users.class,id);
            }catch (HibernateException e){
                e.printStackTrace();
            }
            return users;
        }
        @SuppressWarnings("unchecked")
        public Users getUserData(String uname,String pass){
            Users users=null;
            try{
                session=getSession();
                String hql="from "+Users.class.getName()+" where user_emai_ph=:unm and user_password=:upp";
                Query<Users> query = session.createQuery(hql);
                query.setParameter("unm",uname).setParameter("upp",pass);
                users = query.uniqueResult();
            }catch (HibernateException e){
                e.printStackTrace();
            }
            return users;
        }

}
