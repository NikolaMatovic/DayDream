package ch.fhnw.dream.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.User;
import ch.fhnw.dream.data.repository.DaydreamRepository;

@Service
public class DaydreamService {

    @Autowired
    private DaydreamRepository daydreamRepository;

    public List<Daydream> getAllDaydreams() {
        return daydreamRepository.findAll();
    }

    public Daydream createDaydream(Daydream daydream) {
        if (daydream.getUpdatedAt() == null) {
            daydream.setUpdatedAt(java.time.LocalDateTime.now());
        }
        return daydreamRepository.save(daydream);
    }
}
