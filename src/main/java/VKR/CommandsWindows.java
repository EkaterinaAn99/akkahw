package VKR;

public enum CommandsWindows {
    JAVAINSTALL ("Команда для установки Java в Windows"),
    MPIINSTALL ("Команда для установки MPI в Windows"),
    PYTHONINSTALL ("Команда для установки Python в Windows"),
    JAVATEST ("Запустить команду для тестирования установки Java в Windows"),
    PYTHONTEST ("Запустить команду для тестирования установки Python в Windows"),
    MPITEST ("Запустить команду для тестирования установки MPI в Windows");

    private String title;

    CommandsWindows(String title) {
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
