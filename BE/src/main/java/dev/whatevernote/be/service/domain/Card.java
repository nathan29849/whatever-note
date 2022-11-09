package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE note SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class Card extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	protected Card() {}

	@Column(name="card_order")
	private Long seq;

	private String title;

	@ManyToOne
	@JoinColumn(name="note_id")
	private Note note;
}
