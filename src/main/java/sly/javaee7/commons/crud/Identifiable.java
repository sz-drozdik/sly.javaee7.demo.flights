package sly.javaee7.commons.crud;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Identifiable {

	@Column(length = 12)
	private String code;

	@Id
	@GeneratedValue
	private Long id;

	public String getCode() {
		return code;
	}

	public Long getId() {
		return id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getCode();
	}
}
