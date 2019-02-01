package edu.ncsu.se.app;

import edu.ncsu.se.beans.BeansBase;
import edu.ncsu.se.beans.IssueReactions;
import edu.ncsu.se.beans.RepoBranchList;
import edu.ncsu.se.utils.RestUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class App {
    private static final String DEFAULT_GIT_BASE_URL = "https://api.github.com";

    public static void listBranches(String owner, String repoName) {
        BeansBase branchList = new RepoBranchList();
        StringJoiner url = new StringJoiner("/");
        url.add(DEFAULT_GIT_BASE_URL);
        url.add("repos");
        url.add(owner);
        url.add(repoName);
        url.add("branches");
        String response = null;
        try {
            response = RestUtils.getRequest(url.toString(), new HashMap<>());
            branchList.fromJson(response);
        } catch (Exception e) {
            if(e instanceof ParseException){
                System.out.println("Error while parsing json");
                e.printStackTrace();
            }
            else  System.out.println(e.getMessage());

        }
        System.out.println(((RepoBranchList) branchList).getBranches().toString());
    }

    public static void createNewRepo(String token, String repoName) {
        StringJoiner url = new StringJoiner("/");

        url.add(DEFAULT_GIT_BASE_URL);
        url.add("user");
        url.add("repos");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);

        Map<String, Object> params = new HashMap<>();
        params.put("name", repoName);
        params.put("description", repoName);
        params.put("private", false);
        params.put("has_issues", true);
        params.put("auto_init", true);

        try {
            String response = RestUtils.postRequest(url.toString(), params, headers);
            JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response);
            System.out.println("Repo created !! ");
            System.out.println("Repo Name : " + jsonObject.get("name"));
            System.out.println("Repo URL : " + jsonObject.get("html_url"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createIssue(String token, String owner, String repoName, String title, String body, String[] assignees) {
        StringJoiner url = new StringJoiner("/");

        url.add(DEFAULT_GIT_BASE_URL);
        url.add("repos");
        url.add(owner);
        url.add(repoName);
        url.add("issues");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);

        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);

        if(assignees != null && assignees.length > 0)
            params.put("assignees", assignees);

        try {
            String response = RestUtils.postRequest(url.toString(), params, headers);
            JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response);
            System.out.println("Issue created !! ");
            System.out.println("Issue URL : " + jsonObject.get("url"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editRepo(String token, String owner, String repoName) {
        StringJoiner url = new StringJoiner("/");

        url.add(DEFAULT_GIT_BASE_URL);
        url.add("repos");
        url.add(owner);
        url.add(repoName);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);

        Map<String, Object> params = new HashMap<>();
        params.put("name", repoName);
        params.put("has_issues", false);
        try {
            String response = RestUtils.patchRequest(url.toString(), params,headers);
            JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(response);
            System.out.println("Has Issues : " + jsonObject.get("has_issues"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addAssignee(String token, String owner, String repoName, int issueNumber, String[] assignees) {
        StringJoiner url = new StringJoiner("/");

        url.add(DEFAULT_GIT_BASE_URL);
        url.add("repos");
        url.add(owner);
        url.add(repoName);
        url.add("issues");
        url.add(String.valueOf(issueNumber));
        url.add("assignees");
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);
        headers.put("Accept", "application/vnd.github.symmetra-preview+json");
        Map<String, Object> params = new HashMap<>();
        params.put("assignees", assignees);
        try {
            System.out.println(RestUtils.postRequest(url.toString(), params,headers));
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        System.out.println("Successfully Added");
    }

    public static void getReactionsOfIssue(String owner, String repoName, int issueNumber) {
        StringJoiner url = new StringJoiner("/");

        url.add(DEFAULT_GIT_BASE_URL);
        url.add("repos");
        url.add(owner);
        url.add(repoName);
        url.add("issues");
        url.add(String.valueOf(issueNumber));
        url.add("reactions");

        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/vnd.github.squirrel-girl-preview+json");
        try {
            String response = RestUtils.getRequest(url.toString(), headers);
            IssueReactions reactions = new IssueReactions();
            reactions.fromJson(response);
            System.out.println(reactions.toString());
        }
        catch (Exception e) {
            if(e instanceof ParseException){
                System.out.println("Error while parsing json");
                e.printStackTrace();
            } else System.out.println(e.getMessage());

        }

    }


}
