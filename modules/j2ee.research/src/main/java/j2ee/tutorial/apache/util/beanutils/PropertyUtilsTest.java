package j2ee.tutorial.apache.util.beanutils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public class PropertyUtilsTest {
	public static void main(String[] args) throws Exception {
		Person person=new Person();
		person.setName("Alex Wolfe");
		person.setFavoriteColor("Green");
		try{
			String name=(String)PropertyUtils.getSimpleProperty(person,"name");
			String favoriteColor=(String)PropertyUtils.getSimpleProperty(person,"favoriteColor");
			System.out.println("The Person: " + name + " likes " + favoriteColor);
		}catch(IllegalAccessException e){
			System.out.println("You are not allowed to access a property!");
		}catch(InvocationTargetException e){
			System.out.println("There was a problem invoking the method.");
		}catch(NoSuchMethodException e){
			System.out.println("There is no method to get a property.");
		}

		Book book = new Book( );
		book.setName( "Emerson's Essays" );
		Person author = new Person( );
		author.setName( "Ralph Waldo Emerson" );
		book.setAuthor( author );
		System.out.println( PropertyUtils.getNestedProperty(book,"author.name"));


		book = new Book( );
		PropertyUtils.setProperty( book, "name", "Some Apache Book" );
		PropertyUtils.setProperty( book, "author", new Person( ) );
		PropertyUtils.setProperty( book, "author.name", "Ken Coar" );
		System.out.println(book.toString());

		System.out.println("********************* Testing Property Access *********************");
		book = new Book( );
		book.setName( "Blah" );
		// Can we read and write "name"
		System.out.println( "Is name readable? " + PropertyUtils.isReadable( book, "name" ) );
		System.out.println( "Is name writable? " + PropertyUtils.isWriteable( book, "name" ) );

	}
}
