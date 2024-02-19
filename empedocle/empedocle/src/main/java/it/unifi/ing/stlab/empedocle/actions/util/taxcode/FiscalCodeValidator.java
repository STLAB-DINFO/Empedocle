package it.unifi.ing.stlab.empedocle.actions.util.taxcode;

import it.unifi.ing.stlab.patients.model.Sex;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * https://it.wikipedia.org/wiki/Codice_fiscale#Generazione_del_codice_fiscale
 */
public class FiscalCodeValidator {

    private static final String CODICE_ISTAT_COMUNI_CSV = "codice-istat-comuni-big.csv";

    private static final char[] VOCALS = new char[] { 'A', 'E', 'I', 'O', 'U' };

//	private static final String[] ACCENTED_LETTERS = new String[] { //
//		"À", "Á", "Ä", "Â", //
//		"È", "É", "Ë", "Ê", //
//		"Ì", "Í", "Ï", "Î", //
//		"Ò", "Ó", "Ö", "Ô", //
//		"Ù", "Ú", "Ü", "Û" };
//
//	private static final String[] ACCENTED_LETTERS_REPLACEMENT = new String[] { //
//		"A'", "A'", "A", "A", //
//		"E'", "E'", "E", "E", //
//		"I'", "I'", "I", "I", //
//		"O'", "O'", "O", "O", //
//		"U'", "U'", "U", "U" };

    private static Map<Integer, String> monthValues;
    private static HashMap<String, Integer> oddValues;
    private static HashMap<String, Integer> evenValues;
    private static HashMap<Integer, String> checkDigitValues;
    private static HashMap<String, String> cityValues;

    static {
        initMonthValuesMap();
        initOddValuesMap();
        initEvenValuesMap();
        initCityValuesMap();
        initCheckDigitValuesMap();
    }

    /**
     * Calculate valid italian fiscal codes for given data.
     *
     * @param surname
     *            person surname
     * @param name
     *            person name
     * @param birthDate
     *            person birth date
     * @param birthPlace
     *            person town of birth
     * @param gender
     *            person gender
     * @return a valid fiscal code, null if the fiscal code cannot be computed
     * @throws FiscalCodeValidatorException
     */
    public static String computeFiscalCode(
            String surname, String name, LocalDate birthDate, String birthPlace, Sex gender ) throws FiscalCodeValidatorException {

        if ( !checkParamsNotEmpty( surname, name, birthDate, birthPlace, gender ) ) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        result.append( surnameCode( FiscalCodeNormalizer.normalize( surname, true ) ) );
        result.append( nameCode( FiscalCodeNormalizer.normalize( name, true ) ) );
        result.append( yearCode( birthDate.getYear() ) );
        result.append( monthCode( birthDate.getMonthValue() ) );
        result.append( dayCode( birthDate.getDayOfMonth(), gender ) );
        result.append( cityCode( birthPlace.toUpperCase().trim() ) );
        result.append( checkDigit( result.toString() ) );

        return result.toString();
    }

    private static void initCheckDigitValuesMap() {
        checkDigitValues = new HashMap<>();

        checkDigitValues.put( 0, "A" );
        checkDigitValues.put( 1, "B" );
        checkDigitValues.put( 2, "C" );
        checkDigitValues.put( 3, "D" );
        checkDigitValues.put( 4, "E" );
        checkDigitValues.put( 5, "F" );
        checkDigitValues.put( 6, "G" );
        checkDigitValues.put( 7, "H" );
        checkDigitValues.put( 8, "I" );
        checkDigitValues.put( 9, "J" );
        checkDigitValues.put( 10, "K" );
        checkDigitValues.put( 11, "L" );
        checkDigitValues.put( 12, "M" );
        checkDigitValues.put( 13, "N" );
        checkDigitValues.put( 14, "O" );
        checkDigitValues.put( 15, "P" );
        checkDigitValues.put( 16, "Q" );
        checkDigitValues.put( 17, "R" );
        checkDigitValues.put( 18, "S" );
        checkDigitValues.put( 19, "T" );
        checkDigitValues.put( 20, "U" );
        checkDigitValues.put( 21, "V" );
        checkDigitValues.put( 22, "W" );
        checkDigitValues.put( 23, "X" );
        checkDigitValues.put( 24, "Y" );
        checkDigitValues.put( 25, "Z" );
    }

    private static void initCityValuesMap() {
        cityValues = new HashMap<>();

        try {
            String source = IOUtils.toString( FiscalCodeValidator.class.getClassLoader()
                    .getResource( CODICE_ISTAT_COMUNI_CSV ) );

            Scanner scanner = new Scanner( source );

            while ( scanner.hasNextLine() ) {
                String line = scanner.nextLine();

                Scanner s = new Scanner( line );
                s.useDelimiter( "\t" );

                String code = s.next();
                String name = s.next();

                cityValues.put( name, code );

                s.close();

//				cityValues.put( StringUtils.replaceEach( name, ACCENTED_LETTERS,
//						ACCENTED_LETTERS_REPLACEMENT ), code );
            }
            scanner.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private static void initEvenValuesMap() {
        evenValues = new HashMap<>();

        evenValues.put( "0", 0 );
        evenValues.put( "1", 1 );
        evenValues.put( "2", 2 );
        evenValues.put( "3", 3 );
        evenValues.put( "4", 4 );
        evenValues.put( "5", 5 );
        evenValues.put( "6", 6 );
        evenValues.put( "7", 7 );
        evenValues.put( "8", 8 );
        evenValues.put( "9", 9 );
        evenValues.put( "A", 0 );
        evenValues.put( "B", 1 );
        evenValues.put( "C", 2 );
        evenValues.put( "D", 3 );
        evenValues.put( "E", 4 );
        evenValues.put( "F", 5 );
        evenValues.put( "G", 6 );
        evenValues.put( "H", 7 );
        evenValues.put( "I", 8 );
        evenValues.put( "J", 9 );
        evenValues.put( "K", 10 );
        evenValues.put( "L", 11 );
        evenValues.put( "M", 12 );
        evenValues.put( "N", 13 );
        evenValues.put( "O", 14 );
        evenValues.put( "P", 15 );
        evenValues.put( "Q", 16 );
        evenValues.put( "R", 17 );
        evenValues.put( "S", 18 );
        evenValues.put( "T", 19 );
        evenValues.put( "U", 20 );
        evenValues.put( "V", 21 );
        evenValues.put( "W", 22 );
        evenValues.put( "X", 23 );
        evenValues.put( "Y", 24 );
        evenValues.put( "Z", 25 );
    }

    private static void initOddValuesMap() {
        oddValues = new HashMap<>();

        oddValues.put( "0", 1 );
        oddValues.put( "1", 0 );
        oddValues.put( "2", 5 );
        oddValues.put( "3", 7 );
        oddValues.put( "4", 9 );
        oddValues.put( "5", 13 );
        oddValues.put( "6", 15 );
        oddValues.put( "7", 17 );
        oddValues.put( "8", 19 );
        oddValues.put( "9", 21 );
        oddValues.put( "A", 1 );
        oddValues.put( "B", 0 );
        oddValues.put( "C", 5 );
        oddValues.put( "D", 7 );
        oddValues.put( "E", 9 );
        oddValues.put( "F", 13 );
        oddValues.put( "G", 15 );
        oddValues.put( "H", 17 );
        oddValues.put( "I", 19 );
        oddValues.put( "J", 21 );
        oddValues.put( "K", 2 );
        oddValues.put( "L", 4 );
        oddValues.put( "M", 18 );
        oddValues.put( "N", 20 );
        oddValues.put( "O", 11 );
        oddValues.put( "P", 3 );
        oddValues.put( "Q", 6 );
        oddValues.put( "R", 8 );
        oddValues.put( "S", 12 );
        oddValues.put( "T", 14 );
        oddValues.put( "U", 16 );
        oddValues.put( "V", 10 );
        oddValues.put( "W", 22 );
        oddValues.put( "X", 25 );
        oddValues.put( "Y", 24 );
        oddValues.put( "Z", 23 );
    }

    private static void initMonthValuesMap() {
        monthValues = new HashMap<>();

        monthValues.put( 1, "A" ); // gennaio
        monthValues.put( 2, "B" ); // febbraio
        monthValues.put( 3, "C" ); // marzo
        monthValues.put( 4, "D" ); // aprile
        monthValues.put( 5, "E" ); // maggio
        monthValues.put( 6, "H" ); // giugno
        monthValues.put( 7, "L" ); // luglio
        monthValues.put( 8, "M" ); // agosto
        monthValues.put( 9, "P" ); // settembre
        monthValues.put( 10, "R" ); // ottobre
        monthValues.put( 11, "S" ); // novembre
        monthValues.put( 12, "T" ); // dicembre
    }

    /*
     * 1. Convert even-position characters into numbers (see evenValues).
     * 2. Convert odd-position characters into numbers (see oddValues).
     * 3. Sum all the obtained values and divide them by 26.
     * 4. Determine an alphabetical letter corresponding to the remainder of the operation (see controlCharValues).
     */
    private static String checkDigit( String code ) throws FiscalCodeValidatorException {
        int evenSum = 0;
        for ( int i = 1; i < code.length(); i += 2 ) {
            Integer value = evenValues.get( String.valueOf( code.charAt( i ) ) );
            if ( value == null ) {
                throw new FiscalCodeValidatorException(
                        "Control character " + code.charAt( i ) + " unsupported value" );
            }
            evenSum += value;
        }

        int oddSum = 0;
        for ( int i = 0; i < code.length(); i += 2 ) {
            Integer value = oddValues.get( String.valueOf( code.charAt( i ) ) );
            if ( value == null ) {
                throw new FiscalCodeValidatorException(
                        "Control character " + code.charAt( i ) + " unsupported value" );
            }
            oddSum += value;
        }

        String checkDigit = checkDigitValues.get( ( evenSum + oddSum ) % 26 );
        if ( checkDigit == null ) {
            throw new FiscalCodeValidatorException(
                    "Control character " + checkDigit + " unsupported value" );
        }

        return checkDigit;
    }

    /*
     * The four alphanumeric characters indicating the Italian municipality or
     * foreign country of birth consist of an alphabetical character
     * followed by three numerical characters, according to the encoding established
     * by the Territorial Agency.
     */
    private static String cityCode( String birthPlace ) throws FiscalCodeValidatorException {
        String code = cityValues.get( birthPlace );

        if( code == null )
            throw new FiscalCodeValidatorException( "City code not found for " + birthPlace.toUpperCase() );

        return code;
    }

    /*
     * For male subjects, the day of birth appears unchanged, with the
     * numbers from one to thirty-one, prefixing the days
     * from the first to the ninth with a zero.
     * For female subjects, the day of birth is increased by forty
     * units, so it appears with numbers from forty-one to seventy-one.
     */
    private static String dayCode( int day, Sex gender ) {
        return gender.equals( Sex.M ) ?
                ( day < 10 ) ? "0" + day : String.valueOf( day )
                : String.valueOf( day + 40 );
    }

    /*
     * The alphabetical character corresponding to the month of birth is that
     * established for each month in the following table:
     * January = A
     * February = B
     * March = C
     * April = D
     * May = E
     * June = H
     * July = L
     * August = M
     * September = P
     * October = R
     * November = S
     * December = T
     */
    private static String monthCode( int month ) {
        return monthValues.get( month );
    }

    /*
     * The two numerical characters indicating the year of birth are,
     * in order, the digit of the tens and the digit of the units of the
     * year itself.
     */
    private static int yearCode( int year ) {
        return year % 100;
    }

    private static String surnameCode( String surname ) throws FiscalCodeValidatorException {
        StringBuilder result = new StringBuilder();

        /*
         * If the surname contains a consonant and a vowel, the consonant and the vowel are noted,
         * in order, and the letter X is assumed as the third character.
         * If the surname consists of only two vowels, they are noted,
         * in order, and the letter X is assumed as the third character.
         */
        if ( surname.length() < 3 ) {
            result.append( surname );
            while ( result.length() < 3 ) {
                result.append( "X" );
            }
            return result.toString();
        }

        List<Character> vocals = new ArrayList<Character>();
        List<Character> consonants = new ArrayList<Character>();
        for ( int i = 0; i < surname.length(); i++ ) {
            if ( isVocal( surname.charAt( i ) ) ) {
                vocals.add( surname.charAt( i ) );
            } else {
                consonants.add( surname.charAt( i ) );
            }
        }

        switch ( consonants.size() ) {

            /*
             * If the surname contains two consonants, the three characters to be noted
             * are, in order, the first and the second consonant and the first
             * vowel.
             */
            case 2:
                result.append( consonants.get( 0 ) );
                result.append( consonants.get( 1 ) );
                result.append( vocals.get( 0 ) );
                break;

            /*
             * If the surname contains one consonant and two vowels, the consonant is noted first,
             * followed by the first and second vowel, in that order.
             */
            case 1:
                result.append( consonants.get( 0 ) );
                result.append( vocals.get( 0 ) );
                result.append( vocals.get( 1 ) );
                break;

            case 0:
                throw new FiscalCodeValidatorException( "Zero consonants found for surname!" );

                /*
                 * If the surname contains three or more consonants, the three characters to be
                 * noted are, in order, the first, second, and third consonant.
                 */
            default:
                result.append( consonants.get( 0 ) );
                result.append( consonants.get( 1 ) );
                result.append( consonants.get( 2 ) );
                break;
        }

        return result.toString();
    }

    private static String nameCode( String name ) throws FiscalCodeValidatorException {
        StringBuilder result = new StringBuilder();

        /*
         * If the name contains one consonant and one vowel, the consonant and vowel are noted,
         * in order, and the letter X is assumed as the third character.
         * If the name consists of only two vowels, they are noted in order, and the letter X
         * is assumed as the third character.
         */
        if ( name.length() < 3 ) {
            result.append( name );
            while ( result.length() < 3 ) {
                result.append( "X" );
            }
            return result.toString();
        }

        List<Character> vocals = new ArrayList<Character>();
        List<Character> consonants = new ArrayList<Character>();
        for ( int i = 0; i < name.length(); i++ ) {
            if ( isVocal( name.charAt( i ) ) ) {
                vocals.add( name.charAt( i ) );
            } else {
                consonants.add( name.charAt( i ) );
            }
        }

        switch ( consonants.size() ) {
            /*
             * If the name contains three consonants, the three characters to be noted are,
             * in order, the first, second, and third consonant.
             */
            case 3:
                result.append( consonants.get( 0 ) );
                result.append( consonants.get( 1 ) );
                result.append( consonants.get( 2 ) );
                break;

            /*
             * If the name contains two consonants, the three characters to be noted are,
             * in order, the first and second consonant and the first vowel.
             */
            case 2:
                result.append( consonants.get( 0 ) );
                result.append( consonants.get( 1 ) );
                result.append( vocals.get( 0 ) );
                break;

            /*
             *If the name contains one consonant and two vowels, the three characters to be
             * noted are, in order, that consonant followed by the first and
             * second vowel.
             */
            case 1:
                result.append( consonants.get( 0 ) );
                result.append( vocals.get( 0 ) );
                result.append( vocals.get( 1 ) );
                break;

            case 0:
                throw new FiscalCodeValidatorException( "Zero consonants found for name!" );

                /*
                 * If the name contains four or more consonants, the three characters to be
                 * noted are, in order, the first, third, and fourth consonant.
                 */
            default:
                result.append( consonants.get( 0 ) );
                result.append( consonants.get( 2 ) );
                result.append( consonants.get( 3 ) );
                break;
        }

        return result.toString();
    }


    private static boolean checkParamsNotEmpty(
            String surname, String name, LocalDate birthDate, String birthPlace, Sex gender ) {
        return surname != null && !surname.isEmpty() &&
                name != null && !name.isEmpty() &&
                birthDate != null &&
                birthPlace != null && !birthPlace.isEmpty() &&
                gender != null;
    }

    private static boolean isVocal( char character ) {
        for ( char vocal : VOCALS ) {
            if ( character == vocal ) {
                return true;
            }
        }
        return false;
    }
}
