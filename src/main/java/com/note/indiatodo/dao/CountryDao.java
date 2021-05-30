package com.note.indiatodo.dao;

import com.note.indiatodo.dto.CountryMapping;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CountryDao {
    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
    public Session session;

    @SuppressWarnings("unchecked")
    public List<CountryMapping> getConDetails(){
        session=getSession();
        String sql="SELECT * FROM country_pop";
        Query<CountryMapping> query = session.createNativeQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(CountryMapping.class));
        return query.list();
    }
}
