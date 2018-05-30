package sly.javaee7.commons.crud.lazy;

import java.io.Serializable;
import java.util.List;

public class LoadResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public Serializable info;
	public List<T> resultList;
	public int rowCount;
}