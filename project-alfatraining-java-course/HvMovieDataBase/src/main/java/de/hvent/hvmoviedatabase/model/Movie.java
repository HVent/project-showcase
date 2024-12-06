package de.hvent.hvmoviedatabase.model;

/**
 * Diese Klasse stellt ein Modell für einen Film dar.
 * Ein Film besteht aus einer ID, einem Titel, einem Erscheinungsjahr und einem Genre.
 * Es gibt mehrere Konstruktoren, um ein Filmobjekt mit unterschiedlichen Attributen zu instanziieren.
 */
public class Movie {

    //region Konstanten
    private static final int DEFAULT_ID = -1;
    private static final String DEFAULT_TITLE = ">noTitleSet<";
    private static final String DEFAULT_RELEASE_YEAR = ">noReleaseYearSet<";
    private static final String DEFAULT_GENRE = ">noGenreSet<";

    /**
     * Format für die Anzeige von Film-Informationen in einer Listenansicht.
     * Wird verwendet, um die Formatierung der Filmdetails in der UI anzuzeigen.
     */
    public static final String LIST_CELL_FORMAT = "%d / %s / %s / %s";
    //endregion

    //region Decl und Init Attribute
    private int id;
    private String title;
    private String releaseYear;
    private String genre;
    //endregion

    //region Konstruktoren

    /**
     * Standardkonstruktor, der alle Attribute mit den Standardwerten initialisiert.
     */
    public Movie() {
        this.id = DEFAULT_ID;
        this.title = DEFAULT_TITLE;
        this.releaseYear = DEFAULT_RELEASE_YEAR;
        this.genre = DEFAULT_GENRE;
    }

    /**
     * Konstruktor zum Setzen der Hauptattribute bei Instanziierung
     * Id wird hier nicht zugewiesen
     * @param title         : {@link String} : Filmtitel
     * @param releaseYear   : {@link String} : Erscheinungsjahr
     * @param genre         : {@link String} : Genre des Films
     */
    public Movie(String title, String releaseYear, String genre) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    /**
     * Konstruktor zum Setzen aller Attribute bei Instanziierung
     *
     * @param id           : int : ID des Films
     * @param title        : {@link String} : Filmtitel
     * @param releaseYear  : {@link String} : Erscheinungsjahr
     * @param genre        : {@link String} : Genre des Films
     */
    public Movie(int id, String title, String releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }
    //endregion

    //region Getter und Setter
    public int getId() { return id; }
    public void setId( int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle( String title) { this.title = title; }

    public String getReleaseYear() { return releaseYear; }
    public void setReleaseYear( String releaseYear) { this.releaseYear = releaseYear; }

    public String getGenre() { return genre; }
    public void setGenre( String genre) { this.genre = genre; }

    /**
     * Gibt eine formatierte String-Darstellung des Films zurück,
     * die in einer Listenansicht verwendet werden kann.
     *
     * @return listCellInformation : {@link String} : Die formatierte String-Darstellung des Films.
     */
    public String getListCellInformation() {
        String listCellInformation =
                String.format(LIST_CELL_FORMAT,
                        this.getId(),
                        this.getTitle(),
                        this.getReleaseYear(),
                        this.getGenre());
        return listCellInformation;
    }
    //endregion

    //region toString
    public String toString() {
        return "Movie{" +
                "id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", genre='" + genre + '\'' +
                "} ";
    }
    //endregion
}
