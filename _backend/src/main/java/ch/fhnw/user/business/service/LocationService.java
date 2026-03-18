package ch.fhnw.user.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.user.data.domain.Location;
import ch.fhnw.user.data.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location with id " + id + " not found"));
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location updatedLocation) {
        Location existingLocation = getLocationById(id);
        existingLocation.setName(updatedLocation.getName());
        existingLocation.setCity(updatedLocation.getCity());
        return locationRepository.save(existingLocation);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}