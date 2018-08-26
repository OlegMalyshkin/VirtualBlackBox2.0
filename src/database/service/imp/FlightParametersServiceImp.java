package database.service.imp;

import database.dao.FlightParametersDAO;
import database.entity.FlightParameters;
import database.service.FlightParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightParametersServiceImp implements FlightParametersService {

    @Autowired
    private FlightParametersDAO dao;

    @Override
    public void create(FlightParameters params) {
        if(params != null){
            params.setId(dao.create(params));
        }
    }

    @Override
    public FlightParameters read(Long id) {
        if(id == null){
            return null;
        }
        return dao.read(id);
    }

    @Override
    public void update(FlightParameters params) {
        dao.update(params);
    }

    @Override
    public void delete(FlightParameters params) {
        dao.delete(params);
    }

    @Override
    public List<FlightParameters> getAll() {
        return dao.getAll();
    }

    public void setDao(FlightParametersDAO dao) {
        this.dao = dao;
    }

    public FlightParametersDAO getDao() {
        return dao;
    }
}
