package ch.fhnw.user.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.user.data.domain.Tag;
import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByDaydream(Daydream daydream) {
        return tagRepository.findByDaydream(daydream);
    }

    public List<Tag> getTagsByName(String name) {
        return tagRepository.findByName(name);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }
}
