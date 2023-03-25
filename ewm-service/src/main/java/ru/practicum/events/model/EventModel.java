
package ru.practicum.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.CategoryModel;
import ru.practicum.users.model.UserModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class EventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "events_id  ")
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryModel category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id ")
    private UserModel userEventCreator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private LocationCategoryModel location;
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "description")
    private String description;
    @Column(name = "event_date ")
    private LocalDateTime event_date;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = " participant_limit")
    private int participant_limit;
    @Column(name = "request_moderation")
    private boolean request_moderation;
    @Column(name = "title")
    private String title;
}
