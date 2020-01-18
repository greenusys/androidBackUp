package com.greenusys.personal.registrationapp.pojos;

/**
 * Created by personal on 3/10/2018.
 */

public class TestResult {

    private String testName;
    private String maxMarks;
    private String score;

    public TestResult(String testName, String maxMarks, String score)
    {
        this.testName = testName;
        this.maxMarks = maxMarks;
        this.score = score;
    }

    public String getTestName()
    {
        return testName;
    }
    public String getMaxMarks()
    {
        return maxMarks;
    }
    public String getScore()
    {
        return score;
    }
}
