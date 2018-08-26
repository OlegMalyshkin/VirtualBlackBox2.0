package database.service;

import database.entity.FlightParameters;

import java.util.List;

public interface FlightParametersService {

    void create(FlightParameters params);

    FlightParameters read(Long id);

    void update(FlightParameters params);

    void delete(FlightParameters params);

    List<FlightParameters> getAll();

}
