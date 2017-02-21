import java.io.*;
import java.util.Scanner;

public class Main {
    public static void execute(Repo repo) throws IOException {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("exit")) {
            if (input.equals("create")) {
                System.out.print("Source: ");
                String src = sc.next();
                System.out.print("Target: ");
                String tgt = sc.next();
                repo.create(src, tgt);
            }
            input = sc.nextLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Repo repo = new Repo();
        execute(repo);
    }
}
