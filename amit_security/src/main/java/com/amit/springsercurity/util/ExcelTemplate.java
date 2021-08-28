package com.amit.springsercurity.util;

import java.util.*;

public interface ExcelTemplate {
    interface ImportUserTemplate{
        String tableName = "user";
        List<String> parameters = new ArrayList<String>(Arrays.asList("username", "password", "name", "age"));

        interface DateFormat{
            int DAY = 0;
            int MONTH = 1;
            int YEAR = 2;
            String separate = "/";
        }

        interface CellIndex{
            int FULLNAME = 0;
            int BIRTHDAY = 1;
            int PASSWORD = 2;
        }
    }

}
