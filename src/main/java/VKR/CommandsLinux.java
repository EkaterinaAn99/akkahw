package VKR;

public enum CommandsLinux {
    JAVAINSTALL ("Команда для установки Java в Linux"),
    MPIINSTALL ("Команда для установки MPI в Linux"),
    PYTHONINSTALL ("Команда для установки Python в Linux"),
    JAVATEST ("Запустить команду для тестирования установки Java в Linux"),
    PYTHONTEST ("Запустить команду для тестирования установки Python в Linux"),
    MPITEST ("Запустить команду для тестирования установки MPI в Linux");

    private String title;

    CommandsLinux(String title) {
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
