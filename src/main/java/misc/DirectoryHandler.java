package main.java.misc;

public class DirectoryHandler {

    /***
     * LEVEL 1:
     * Root Directory
     * 'src/main/'
     */
    public static final String MAIN = "main/";

    /**
     * LEVEL 2:
     * Java Source code Directory
     * 'src/main/java/'
     */
    public static final String JAVA = MAIN+"java/";

    /**
     * LEVEL 2:
     * Resources Directory
     * 'src/main/resources/'
     */
    public static final String RESOURCES = MAIN+"resources/";


    /**
     * LEVEL 3:
     * UNDER LEVEL 2 JAVA DIR:
     * Fxml Controllers Directory
     * 'src/main/java/controller/'
     */
    public static final String CONTROLLER = JAVA+"controller/";

    /**
     * LEVEL 3:
     * UNDER LEVEL 2 JAVA DIR:
     * Miscellaneous classes Directory
     * 'src/main/java/misc/'
     */
    public static final String MISC = JAVA+"misc/";

    /**
     * LEVEL 3:
     * UNDER LEVEL 2 RESOURCES DIR:
     * CSS Styles Directory
     * 'src/main/java/misc/style/'
     */
    public static final String STYLE = RESOURCES+"style/";

    /**
     * LEVEL 3:
     * UNDER LEVEL 2 RESOURCES DIR:
     * FXML Files Directory
     * 'src/main/java/misc/fxml/'
     */
    public static final String FXML = RESOURCES+"fxml/";

    /**
     * LEVEL 3:
     * UNDER LEVEL 2 RESOURCES DIR:
     * Image Directory
     * 'src/main/java/misc/img/'
     */
    public static final String IMG = RESOURCES+"img/";
}
