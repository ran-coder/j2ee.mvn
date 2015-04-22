package j2ee.tutorial.apache.util.codec;

import org.apache.commons.codec.language.Soundex;

public class SoundexTest {

	public static void main(String[] args) {
		Soundex soundex = new Soundex( );
		String obrienSoundex = soundex.soundex( "O'Brien" );
		String obrianSoundex = soundex.soundex( "O'Brian" );
		String obryanSoundex = soundex.soundex( "O'Bryan" );
		System.out.println( "O'Brien soundex: " + obrienSoundex );
		System.out.println( "O'Brian soundex: " + obrianSoundex );
		System.out.println( "O'Bryan soundex: " + obryanSoundex );
	}

}
