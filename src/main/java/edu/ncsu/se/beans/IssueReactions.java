package edu.ncsu.se.beans;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class IssueReactions implements BeansBase {
    class Reaction{
        private String type;
        private String user;
        private String time;

        public Reaction(String type, String user, String time){
            this.type = type;
            this.user = user;
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public String getUser() {
            return user;
        }

        public String getTime() {
            return time;
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            return sb.append("User : ").append(user).append(", type : ")
                    .append(type).append(", created at : ").append(time).toString();
        }
    }

    List<Reaction> reactions = new ArrayList<>();

    public List<Reaction> getReactions(){
        return this.reactions;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Reaction reaction : reactions){
            sb.append(reaction.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void fromJson(String json) throws ParseException {
        JSONArray arr = (JSONArray) (new JSONParser()).parse(json);
        for(Object reaction : arr){
            JSONObject user = (JSONObject) ((JSONObject)reaction).get("user");
            String userName = (String) user.get("login");
            String type = (String)((JSONObject)reaction).get("content");
            String time = (String)((JSONObject)reaction).get("created_at");
            this.reactions.add(new Reaction(type, userName, time));
        }
    }
}
