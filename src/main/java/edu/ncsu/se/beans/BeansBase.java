package edu.ncsu.se.beans;

import org.json.simple.parser.ParseException;

public interface BeansBase {
    public void fromJson(String json) throws ParseException;
}
