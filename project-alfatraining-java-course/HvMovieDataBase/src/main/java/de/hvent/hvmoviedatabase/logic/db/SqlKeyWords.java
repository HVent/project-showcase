package de.hvent.hvmoviedatabase.logic.db;

/**
 * Diese abstrakte Klasse stellt eine Sammlung von SQL-Schlüsselwörtern zur Verfügung,
 * die in einer Datenbankanwendung verwendet werden. Sie kapselt häufig verwendete SQL-Statements
 * und Datentypen, sodass diese zentral und einheitlich im Code genutzt werden können.
 *
 * <p>Die Klasse enthält SQL-Keywords für Tabellenaktionen, Datentypen, Operatoren, Schlüssel und Bedingungen.</p>
 *
 * <p>Beispielhafte Verwendung:</p>
 * <pre>
 * String createTableQuery = SqlKeyWords.CREATE_TBL + "my_table (" +
 *         SqlKeyWords.PRIMARY_KEY_AUTO_INCREMENT_INC_COMMA +
 *         "column1 " + SqlKeyWords.DATA_TYPE_TEXT + ")";
 * </pre>
 *
 * @author rHistel
 * @since 04.10.2017
 */

public abstract class SqlKeyWords {

    //region Datatypes
    protected static final String DATA_TYPE_INTEGER           = " INTEGER ";
    protected static final String DATA_TYPE_INTEGER_INC_COMMA = " INTEGER, ";

    protected static final String DATA_TYPE_REAL           = " REAL ";
    protected static final String DATA_TYPE_REAL_INC_COMMA = " REAL, ";

    protected static final String DATA_TYPE_TEXT_INC_COMMA = " TEXT, ";
    protected static final String DATA_TYPE_TEXT           = " TEXT ";

    protected static final int DATA_TYPE_BOOLEAN_FALSE = 0;
    protected static final int DATA_TYPE_BOOLEAN_TRUE  = 1;

    //endregion

    //region table actions
    protected static final String CREATE_TBL            = "CREATE TABLE ";
    protected static final String SELECT_INC_BLANK      = "SELECT ";
    protected static final String FROM_INC_BLANKS       = " FROM ";
    protected static final String SELECT_ALL_DATA_FROM  = SELECT_INC_BLANK + "*" + FROM_INC_BLANKS;
    protected static final String SELECT_COUNT_FROM_TBL = "SELECT COUNT(*)" + FROM_INC_BLANKS;
    protected static final String DROP_TBL              = "DROP TABLE IF EXISTS ";

    protected static final String INSERT_TBL      = "INSERT INTO ";
    protected static final String UPDATE_TBL      = "UPDATE ";
    protected static final String DELETE_FROM_TBL = "DELETE" + FROM_INC_BLANKS;

    //region table actions

    protected static final String DROP_TBL_IF_EXISTS = "DROP TABLE IF EXISTS ";

    //endregion

    //region keys
    protected static final String PRIMARY_KEY_AUTO_INCREMENT_INC_COMMA =
            DATA_TYPE_INTEGER + "PRIMARY KEY AUTOINCREMENT, ";
    protected static final String PRIMARY_KEY_INC_COMMA                = DATA_TYPE_INTEGER + "PRIMARY KEY, ";
    //endregion

    //region operators
    protected static final String SET          = " SET ";
    protected static final String AND_OPERATOR = " AND ";
    protected static final String VALUES_OPERATOR                  = " VALUES ";
    protected static final String EQUALS                           = " = ";
    protected static final String EQUALS_OPERATOR_INC_PLACE_HOLDER = " =?";
    //endregion

    //region conditions
    protected static final String WHERE_CONDITION = " WHERE ";

    //endregion

    //region chars
    protected static final String COMMA        = ", ";
    protected static final String BACK_TICK    = "`";
    protected static final String SINGLE_QUOTE = "'";
    protected static final String CHAR_SEMICOLON     = ";";
    protected static final String CHAR_OPEN_PARENTHESIS            = "(";
    protected static final String CHAR_CLOSE_PARENTHESIS           = ")";
    protected static final String CHAR_CLOSE_PARENTHESIS_SEMICOLON = CHAR_CLOSE_PARENTHESIS + CHAR_SEMICOLON;

    //endregion
}
