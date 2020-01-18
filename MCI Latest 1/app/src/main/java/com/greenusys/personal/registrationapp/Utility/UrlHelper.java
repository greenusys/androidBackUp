package com.greenusys.personal.registrationapp.Utility;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Allen on 12/29/2017.
 */

public class UrlHelper {








    public static  String baseUrl = "http://greenusys.website/mci/";
    public static String baseuploads = baseUrl+"uploads/";

    public static String login = baseUrl+"login/login_registeredUser";
    public static String registration = baseUrl+"login/user_register";
    public static String talkMd = baseUrl+"user_query";
    public static String news = baseUrl+"master_news";
    public static String classs = baseUrl+"master_class/index";
    public static String forgot = baseUrl+"login/forget_password";
    public static String checking = baseUrl+"user_query/get_user_status";
    public static String video = baseUrl+"master_video";
    public static String study_material = baseUrl+"study_material";
    public static String test_ofline = baseUrl+"testseries";
    public static String gallery = baseUrl+"gallery";
    public static String quiz = baseUrl+"quiz_import";
    public static String quiz_question = baseUrl+"quiz_import/get_quiz_question";
    public static String course = baseUrl+"course_details";
    public static String time_table = baseUrl+"time_import";

    public static String uploads_video = baseuploads+"test_video/";
    public static String images = baseuploads+"gallery/";
    public static String cour = baseuploads+"course/";

    public static String uploads_study="greenusys.com/mci/uploads/study_material/";

    public static String timeTableImport = "http://greenusys.com/mci/timetable_import";
    public static String onlineResult = baseUrl + "online_test_data/add_online_test";
    public static String studentResult = baseUrl + "online_test_data/show_user_online_test_data";

    public static String studentOfflineResult = baseUrl + "excel_import/get_test_result_android";

    public static String courseUrl = baseUrl + "course_details";

    public static String coursePdfStaticUrl = baseUrl + "uploads/course/";
    public static String testlink = baseUrl + "uploads/test_series/";





    public static  String classId;

    public static String userId;

}