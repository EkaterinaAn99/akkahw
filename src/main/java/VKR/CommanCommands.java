package VKR;

public enum CommanCommands {
    CHECKNET ("ipconfig"),
    JAVA ("Java"),
    PYTHON ("Python") ,
    MPI ("MPI"),
    WINDOWS ("Windows"),
    LINUX ("Linux");

    private String title;

    CommanCommands(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}

