package com.forcelate.entity;

import javax.persistence.*;

import com.forcelate.entity.enums.Color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="text", length = 500)
	private String text;
	
	@Enumerated(EnumType.STRING)
	private Color color;
	
	@ManyToOne(cascade = {
			CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH} , fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
}
