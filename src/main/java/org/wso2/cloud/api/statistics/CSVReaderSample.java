package org.wso2.cloud.api.statistics;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVReaderSample {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in );
        System.out.println("Path to your API creation json file: ");
        String jsonFile = scanner.nextLine();
        System.out.println("Directory path to create result csv files: ");
        String directoryPath = scanner.nextLine();
        JSONParser parser = new JSONParser();

        try {
            JSONArray onlyManageUsers = new JSONArray();
            JSONArray onlyImplementUsers = new JSONArray();
            JSONArray onlyDesignUsers = new JSONArray();
            JSONArray onlyStoreUsers = new JSONArray();

            JSONObject object = (JSONObject) parser.parse(new FileReader(jsonFile));
            JSONArray array = (JSONArray) object.get("userTriggers");
            JSONObject obj = new JSONObject();
            int implement = 0;
            int manage = 0;
            int store = 0;
            String username;
            int i;
            for (i = 0; i < array.size(); i++) {
                obj = (JSONObject) array.get(i);
                username = obj.get("userName").toString();
                implement = Integer.parseInt(obj.get("implement").toString());
                manage = Integer.parseInt(obj.get("manage").toString());
                store = Integer.parseInt(obj.get("store").toString());
                if (store != 0) {
                    onlyStoreUsers.add(username);
                } else if (implement == 0) {
                    onlyDesignUsers.add(username);
                } else if (manage == 0) {
                    onlyImplementUsers.add(username);
                } else {
                    onlyManageUsers.add(username);
                }
            }
            System.out.println("all users : " + array.size());
            System.out.println("only store users : " + onlyStoreUsers.size());
            System.out.println("only manage users : " + onlyManageUsers.size());
            System.out.println("only implement users : " + onlyImplementUsers.size());
            System.out.println("only design users : " + onlyDesignUsers.size());

            FileWriter file1 = new FileWriter(directoryPath + "/onlyStoreUsers.csv");
            FileWriter file2 = new FileWriter(directoryPath + "/onlyManageUsers.csv");
            FileWriter file3 = new FileWriter(directoryPath + "/onlyImplementUsers.csv");
            FileWriter file4 = new FileWriter(directoryPath + "/onlyDesignUsers.csv");

            try {
                for (i = 0; i < onlyStoreUsers.size(); i++) {
                    file1.write(onlyStoreUsers.get(i).toString() + "\n");
                }
                for (i = 0; i < onlyManageUsers.size(); i++) {
                    file2.write(onlyManageUsers.get(i).toString() + "\n");
                }
                for (i = 0; i < onlyImplementUsers.size(); i++) {
                    file3.write(onlyImplementUsers.get(i).toString() + "\n");
                }
                for (i = 0; i < onlyDesignUsers.size(); i++) {
                    file4.write(onlyDesignUsers.get(i).toString() + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            } finally {
                file1.flush();
                file2.flush();
                file3.flush();
                file4.flush();
                file1.close();
                file2.close();
                file3.close();
                file4.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}