package ch.fhnw.user.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.domain.User;
import ch.fhnw.user.data.repository.DaydreamRepository;

@Service
public class DaydreamService {

    @Autowired
    private DaydreamRepository daydreamRepository;

    public List<Daydream> getAllDaydreams() {
        return daydreamRepository.findAll();
    }

    public List<Daydream> getDaydreamsByUser(User user) {
        return daydreamRepository.findByUser(user);
    }

    public List<Daydream> searchDaydreamsByTitle(String title) {
        return daydreamRepository.findByTitleContaining(title);
    }

    public Daydream createDaydream(Daydream daydream) {
        return daydreamRepository.save(daydream);
    }

    public void deleteDaydream(UUID id) {
        daydreamRepository.deleteById(id);
    }
}
