import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // Sorry just using set folders for now.
        String src = "C:\\Users\\Vince\\IdeaProjects\\SCM\\test\\SIL";
        String tgt = "C:\\Users\\Vince\\IdeaProjects\\SCM\\test\\target";

        Repo repo = new Repo(src, tgt);
    }
}
