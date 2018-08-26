package database.dao.imp;

import database.dao.FlightParametersDAO;
import database.entity.FlightParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Queue;

@Repository
@Transactional
public class FlightParametersDAOImp implements FlightParametersDAO {

    //путь к log4j2.xml (resources/logger/log4j2.xml)
    static {
        try {
            InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logger" + File.separator + "log4j2.xml");
            ConfigurationSource source = new ConfigurationSource(inputStream);
            Configurator.initialize(null, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Logger log = LogManager.getLogger(FlightParametersDAOImp.class.getName());

    @Autowired
    private SessionFactory factory;

    @Override
    public Long create(FlightParameters params) {
        log.info("Create " + params);
        return (Long) factory.getCurrentSession().save(params);
    }

    @Override
    public FlightParameters read(Long id) {
        log.info("Read " + id);
        return factory.getCurrentSession().get(FlightParameters.class, id);
    }

    @Override
    public boolean update(FlightParameters params) {
        try {
            factory.getCurrentSession().saveOrUpdate(params);
            log.info("Update " + params);
            return true;
        } catch (HibernateException e){
            e.printStackTrace();
            log.error("Update " + params + " Error message: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(FlightParameters params) {
        try {
            factory.getCurrentSession().delete(params);
            log.info("Delete " + params);
            return true;
        } catch (HibernateException e){
            log.error("Delete " + params + " error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FlightParameters> getAll() {
        return factory.getCurrentSession().createSQLQuery("SELECT * FROM FlightParameters").addEntity(FlightParameters.class).list();
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
