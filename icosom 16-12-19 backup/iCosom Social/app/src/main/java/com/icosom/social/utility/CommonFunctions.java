package com.icosom.social.utility;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.icosom.social.model.FeedModel;

import java.util.ArrayList;

import okhttp3.MediaType;

public class CommonFunctions {
    private static String BASE_URL = "https://icosom.com/social/";



    private static String BASE_URLs = "https://api.rechapi.com/moneyTransfer/";
    private static String center_url = "?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&";

    public static final String Report = " https://icosom.com/wallet/main/Report.php?action=insertProblem";

    public static final String FETCH_FEEDSs = BASE_URLs+"cusDetails.php?format=json&token=xjW1bCqhDUOcotCzHVrMhyQ2AsXCUE&customerMobile=";
    public static final String customerRegistration = BASE_URLs+"customerRegistration.php"+center_url+"customerName=";
    public static final String AddBeneficiary = BASE_URLs+"addBeneficiary.php"+center_url;
    public static final String beneficiaryVerifiy = BASE_URLs+"beneficiaryVerifiy.php"+center_url;
    public static final String moneyTransfer =BASE_URLs+"sendMoney.php"+center_url;
//    private static String BASE_URL = "https://greenusys.website/icosom/social/";
//    private static String BASE_URL = "https://anonymouse.org/cgi-bin/anon-www.cgi/https://greenusys.website/icosom/social/";
    public static final String FETCH_FEEDS = BASE_URL+"main/feedProcess.php?action=fetchFeeds";
    public static final String LIKE_DISLIKE_FEED = BASE_URL+"main/feedProcess.php?action=likePost";
    public static final String FETCH_IMAGES = BASE_URL+"postFiles/images/";
    public static final String FETCH_VIDEOS = BASE_URL+"postFiles/videos/";
    public static final String FETCH_COMMENTS = BASE_URL+"main/feedProcess.php?action=fetchComments";
    public static final String ADD_COMMENTS = BASE_URL+"main/feedProcess.php?action=addComment";
    public static final String SHOW_ALL_MY_POST = BASE_URL+"main/feedProcess.php?action=showAllMyPost";
    public static final String GET_ONLINE_USERS = BASE_URL+"main/onlineUserProcess.php?action=getOnlineUsers";
    public static final String FETCH_FRIEND_REQUEST = BASE_URL+"main/userProcess.php?action=fetchFriendRequests";
    public static final String LOGIN = BASE_URL+"main/loginProcess.php?action=login";
    public static final String notify = BASE_URL+"main/notAPI.php";
    public static final String REGISTER = BASE_URL+"main/signupProcess.php?action=register";
    public static final String VERIFY_OTP = BASE_URL+"main/signupProcess.php?action=verifyOTP";
    public static final String RESEND_OTP = BASE_URL+"main/signupProcess.php?action=resendOTP";
    public static final String CHANGE_COVER_PIC = BASE_URL+"main/profileProcess.php?action=changeCoverPhoto";
    public static final String CHANGE_PROFILE_PIC = BASE_URL+"main/profileProcess.php?action=changeProfilePicture";
    public static final String ACCEPT_FIREND_REQUEST = BASE_URL+"main/userProcess.php?action=acceptFriendRequestTo";
    public static final String DELETE_FIREND_REQUEST = BASE_URL+"main/userProcess.php?action=deny";
    public static final String FOLLOW_FIREND_REQUEST = BASE_URL+"main/userProcess.php?action=follow";
    public static final String SHOW_PROFILE = BASE_URL+"main/userProcess.php?action=showUserData";
    public static final String SEARCH_PROFILE = BASE_URL+"main/searchProcess.php?action=search";
    public static final String UPDATE_DESCRIPTION = BASE_URL+"main/userProcess.php?action=updateDescription";
    public static final String GET_FOLLOW_AND_FRIEND = BASE_URL+"main/userProcess.php?action=getFriendAndFollow";
    public static final String GET_ALL_FRIENDS = BASE_URL+"main/userProcess.php?action=fetchUserAllFriends";
    public static final String GET_ALL_FOLLOWINGS = BASE_URL+"main/userProcess.php?action=fetchUserAllFollowing";
    public static final String GET_ALL_FOLLOWERS = BASE_URL+"main/userProcess.php?action=fetchUserAllFollowers";
    public static final String EDIT_POST = BASE_URL+"main/feedProcess.php?action=editPost";
    public static final String SHARE_POST = BASE_URL+"main/feedProcess.php?action=sharePost";
    public static final String ADD_POST = BASE_URL+"main/feedProcess.php?action=addPost";
    public static final String DELETE_POST = BASE_URL+"main/feedProcess.php?action=deletePost";
    public static final String DELETE_COMMENT = BASE_URL+"main/feedProcess.php?action=deleteComment";
    public static final String FETCH_NOTIFICATION = BASE_URL+"main/notificationProcess.php?action=fetchNotification";
    public static final String UPDATE_NOTIFICATION = BASE_URL+"main/notificationProcess.php?action=updateNotification";
    public static final String SEND_FRIEND_REQUEST = BASE_URL+"main/userProcess.php?action=sendFriendRequestTo";
    public static final String CANCEL_FRIEND_REQUEST = BASE_URL+"main/profileProcess.php?action=cancelRequest";
    public static final String UNFRIEND= BASE_URL+"main/userProcess.php?action=unfriend";
    public static final String FOLLOW = BASE_URL+"main/userProcess.php?action=follow";
    public static final String UNFOLLOW = BASE_URL+"main/userProcess.php?action=unFollow";

    public static final String GET_COUNTRY = BASE_URL+"main/profileProcess.php?action=getCountry";
    public static final String GET_STATES = BASE_URL+"main/profileProcess.php?action=getStates";
    public static final String GET_CITY = BASE_URL+"main/profileProcess.php?action=getCities";
    public static final String EDIT_PROFILE = BASE_URL+"main/profileProcess.php?action=updateProfile";
    public static final String FORGET_PASSWORD = BASE_URL+"main/loginProcess.php?action=forgetPassword";
    public static final String ADVANCE_EDIT_PROFILE = BASE_URL+"main/profileProcess.php?action=updateProfileHobbies";
    public static final String SHOW_SINGLE_POST = BASE_URL+"main/feedProcess.php?action=showSinglePost";
    public static final String register_step1 = BASE_URL+"main/signupProcess.php?action=registerStep1";
    public static final String register_step2 = BASE_URL+"main/signupProcess.php?action=resendOTP";
    public static final String register_step3 = BASE_URL+"main/signupProcess.php?action=register";
    public static final String talent = BASE_URL+"main/feedProcess.php?action=fetchTalent";
    public static final String talent_2 = BASE_URL+"main/feedProcess.php?action=details_comp";
    public static final String talent_3= BASE_URL+"main/feedProcess.php?action=participate_insert";

    public static final String CREATEPIN = BASE_URL+"main/profileProcess.php?action=generatePin";
    public static final String RESETPIN1 = BASE_URL+"main/profileProcess.php?action=resetPin";
    public static final String RESETPIN2= BASE_URL+"main/profileProcess.php?action=submitPIN";
    public static final String loginwallet = BASE_URL+"main/profileProcess.php?action=verifyPIN";


    public static final String email = BASE_URL+"main/profileProcess.php?action=emailSetting";
    public static final String phone = BASE_URL+"main/profileProcess.php?action=phoneSetting";
    public static final String QR_LIST_USER = BASE_URL+"main/userQr.php";
    public static final String LIST_THEME = BASE_URL+"main/fetchTheme.php";

    public static boolean app_running=false;
    public static ArrayList<FeedModel> home_page_list;


    public static float convertDpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}