package edu.ncsu.se.app;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

import static edu.ncsu.se.app.App.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("*********************************Trying GitHub REST APIs****************************************");
        System.out.println("Enter Token file path");
        String tokenFile = sc.next();
        Properties properties = new Properties();
        properties.load(new FileInputStream(tokenFile));
        final String TOKEN = (String) properties.get("user.token");
        final String GIT_BASE_URL = (String) properties.getOrDefault("github.url", "api.github.com");
        String owner = (String) properties.get("user.name");

        System.out.println();

        while(true) {

            System.out.println("\nChoose an option: ");
            displayOptions();
            int chosenOption = sc.nextInt();
            sc.nextLine();
            switch (chosenOption) {
                case 1:
                    System.out.println("Enter Repository Name: ");
                    String repoName = sc.nextLine();
                    listBranches(owner, repoName);
                    break;
                case 2:
                    System.out.println("Enter New Repository Name: ");
                    String newRepoName = sc.nextLine();
                    createNewRepo(TOKEN, newRepoName);
                    break;
                case 3:
                    System.out.println("Enter Repository Name: ");
                    repoName = sc.nextLine();
                    System.out.println("Enter Title of the issue");
                    String title = sc.nextLine();
                    System.out.println("Enter Description");
                    String desc = sc.nextLine();
                    System.out.println("Enter Assignee name");
                    String[] assignee = new String[1];
                    assignee[0] = sc.nextLine();
                    createIssue(TOKEN, owner, repoName, title, desc, assignee);
                    break;
                case 4:
                    System.out.println("Enter Repository Name: ");
                    repoName = sc.nextLine();
                    String[] newAssignee = new String[1];
                    System.out.println("Enter issue id");
                    int issueNumber = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter name of new assignee");
                    newAssignee[0] = sc.nextLine();
                    addAssignee(TOKEN, owner, repoName, issueNumber, newAssignee);
                    break;
                case 5:
                    System.out.println("Enter Repository Name: ");
                    repoName = sc.nextLine();
                    editRepo(TOKEN, owner, repoName);
                    break;
                case 6:
                    System.out.println("Enter Repository Name: ");
                    repoName = sc.nextLine();
                    System.out.println("Enter issue id");
                    issueNumber = sc.nextInt();
                    getReactionsOfIssue(owner, repoName, issueNumber);
                    break;
                case 7:
                    System.exit(0);
            }
        }
    }

    public static void displayOptions(){
        String[] options = {"List all branches of an existing repo", "Create a new repo", "Create an issue for an existing repo",
        "Add an assignee to an existing issue", "Edit a repo to disable issues", "List reactions for an issue", "Exit"};
        for(int i = 0; i < options.length; i++){
            System.out.println((i + 1) + ":" + options[i]);
        }
    }
}
