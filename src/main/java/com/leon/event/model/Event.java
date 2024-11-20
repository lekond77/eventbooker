package com.leon.event.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private String date;

	@Column
	private String imageUrl;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private List<AttendeeEvent> attendees;

	public Event() {
		
	}
	public Event(String title, String description, String date, String imageUrl) {

		this.title = title;
		this.description = description;
		this.date = date;
		this.imageUrl = imageUrl;
	}

	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDate() {
		return date;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}
	
	public List<AttendeeEvent> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<AttendeeEvent> attendees) {
        this.attendees = attendees;
    }

}
