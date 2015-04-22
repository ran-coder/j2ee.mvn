package j2ee.tutorial.apache.util.dbutils;

public class Book {
	public int		id;
	public String	title;
	public String	authors;

	public Book() {}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}