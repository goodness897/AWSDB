package com.example.stu.awsdb;

/**
 * Created by STU on 2016-02-17.
 */
import java.util.Random;

public class Constants {

    public static final String IDENTITY_POOL_ID = "us-east-1:8c2298e9-9317-4a07-b10d-d98c241ceb89";
    // Note that spaces are not allowed in the table name
    public static final String TEST_TABLE_NAME = "MUAJI_TABLE";

    public static final Random random = new Random();
    public static final String[] NAMES = new String[] {
            "Norm", "Jim", "Jason", "Zach", "Matt", "Glenn", "Will", "Wade", "Trevor", "Jeremy",
            "Ryan", "Matty", "Steve", "Pavel"
    };

    public static String getRandomName() {
        int name = random.nextInt(NAMES.length);

        return NAMES[name];
    }

    public static int getRandomScore() {
        return random.nextInt(1000) + 1;
    }
}