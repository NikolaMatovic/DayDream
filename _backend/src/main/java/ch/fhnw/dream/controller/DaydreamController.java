package ch.fhnw.dream.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.dream.business.service.DaydreamService;
import ch.fhnw.dream.business.service.UserService;
import ch.fhnw.dream.data.domain.Comment;
import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.Tag;
import ch.fhnw.dream.data.domain.User;
import ch.fhnw.dream.data.domain.Visibility;

@RestController
@RequestMapping("/v1/dreams")
public class DaydreamController {

    @Autowired
    private DaydreamService daydreamService;

    @Autowired
    private UserService userService;


    // Get all public daydreams
    @GetMapping("/public")
    public List<Daydream> getPublicDaydreams() {
        return daydreamService.getDaydreamsByVisibility(Visibility.PUBLIC);
    }

    // Get all daydreams for the logged-in user (personal feed)
    @GetMapping("/my")
    public List<Daydream> getMyDaydreams(Authentication authentication) {
        return daydreamService.getDaydreamsByUser(authentication.getName());
    }

    // Get all daydreams (admin or for demo)
    @GetMapping("/all")
    public List<Daydream> getAllDaydreams() {
        return daydreamService.getAllDaydreams();
    }
    // Update a daydream (title, description, mood, visibility, tags)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDaydream(@PathVariable Long id, @RequestBody CreateDaydreamRequest request, Authentication authentication) {
        try {
            Daydream updated = daydreamService.updateDaydream(id, request, authentication.getName());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        }
    }

    // Delete a daydream
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDaydream(@PathVariable Long id, Authentication authentication) {
        try {
            daydreamService.deleteDaydream(id, authentication.getName());
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createDaydream(@RequestBody CreateDaydreamRequest request, Authentication authentication) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Titel darf nicht leer sein"));
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Beschreibung darf nicht leer sein"));
        }

        try {
            User user = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));

            Daydream daydream = new Daydream();
            daydream.setUser(user);
            daydream.setTitle(request.getTitle().trim());
            daydream.setDescription(request.getDescription().trim());
            daydream.setMood(request.getMood());
            daydream.setVisibility(parseVisibility(request.getVisibility()));

            List<Tag> tags = new ArrayList<>();
            if (request.getTags() != null) {
                for (String rawTagName : request.getTags()) {
                    if (rawTagName == null) {
                        continue;
                    }

                    String cleanedName = rawTagName.trim();
                    if (cleanedName.isEmpty()) {
                        continue;
                    }

                    Tag tag = new Tag();
                    tag.setDaydream(daydream);
                    tag.setName(cleanedName);
                    tags.add(tag);
                }
            }
            daydream.setTags(tags);

            Daydream created = daydreamService.createDaydream(daydream);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/{daydreamId}/comments")
    public ResponseEntity<?> addComment(
        @PathVariable Long daydreamId,
        @RequestBody CreateCommentRequest request,
        Authentication authentication
    ) {
        if (request.getContent() == null || request.getContent().isBlank()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Kommentar darf nicht leer sein"));
        }

        try {
            Comment comment = daydreamService.addCommentToDaydream(
                daydreamId,
                authentication.getName(),
                request.getContent().trim()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    public static class CreateCommentRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class CreateDaydreamRequest {
        private String title;
        private String description;
        private String mood;
        private String visibility;
        private List<String> tags;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMood() {
            return mood;
        }

        public void setMood(String mood) {
            this.mood = mood;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    private Visibility parseVisibility(String rawVisibility) {
        if (rawVisibility == null || rawVisibility.isBlank()) {
            return Visibility.PUBLIC;
        }

        try {
            return Visibility.valueOf(rawVisibility.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Visibility.PUBLIC;
        }
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
