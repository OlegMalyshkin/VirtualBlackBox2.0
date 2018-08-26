package database.dao;

import database.entity.FlightParameters;

import java.util.List;
import java.util.Queue;

public interface FlightParametersDAO {

    Long create(FlightParameters params);

    FlightParameters read(Long id);

    boolean update(FlightParameters params);

    boolean delete(FlightParameters params);

    List<FlightParameters> getAll();

}
