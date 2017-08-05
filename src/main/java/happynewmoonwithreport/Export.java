package happynewmoonwithreport;

/**
 * The export object
 */
public class Export {
    private String name;
    private ExternalKind kind;

    public Export() {
        super();
    }

    public Export (String name , ExternalKind kind) {
        this.name = name;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public ExternalKind getKind() {
        return kind;
    }

}
