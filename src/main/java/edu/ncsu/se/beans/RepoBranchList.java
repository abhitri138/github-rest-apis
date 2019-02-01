package edu.ncsu.se.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RepoBranchList implements BeansBase{
    private List<String> branches = new ArrayList<String>();
    private static final String BRANCH_NAME_PROP = "name";

    public List<String> getBranches() {
        return branches;
    }

    public void fromJson(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray branchArray = (JSONArray) parser.parse(json);
        for( Object branch : branchArray){
            String branchName = (String) ((JSONObject)branch).get(BRANCH_NAME_PROP);
            this.branches.add(branchName);
        }
    }
}
