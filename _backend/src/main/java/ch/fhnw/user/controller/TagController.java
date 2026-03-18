package ch.fhnw.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.TagService;
import ch.fhnw.user.data.domain.Tag;
import ch.fhnw.user.data.domain.Daydream;

@RestController
@RequestMapping("/tagapi/v1")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/tags/daydream/{daydreamId}")
    public List<Tag> getTagsByDaydream(@PathVariable UUID daydreamId) {
        Daydream daydream = new Daydream();
        daydream.setId(daydreamId);
        return tagService.getTagsByDaydream(daydream);
    }

    @GetMapping("/tags/search")
    public List<Tag> getTagsByName(@RequestParam String name) {
        return tagService.getTagsByName(name);
    }

    @PostMapping("/tags")
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
