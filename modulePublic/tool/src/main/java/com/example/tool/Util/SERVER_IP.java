package com.example.tool.Util;

/**
 * @ClassName com.example.tool.Util.SERVER_IP
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-05-28
 * @Version 1.0
 */

public class SERVER_IP {
    public static String Server_IP = "http://116.204.69.99:4567";
    public static String Server_College_Article = "/article/collect";
    public static String Server_Like_Article = "/article/like";
    public static String Server_Dislike_Article = "/article/like";
    public static String Server_CancelCollege_Article = "/article/cancelcollect";
    public static String Server_getCustom_LikeArticleList = "/article/like/list";
    public static String Server_getCustom_CollegeArticleList = "/article/collect/list";

    public static String Server_Apply_Video = "/vedio/login/list";
    public static String Server_Like_Video = "/vedio/like";
    public static String Server_Dislike_Video = "/vedio/cancel/like";
    public static String Server_Collect_Video = "/vedio/collect";
    public static String Server_CancelCollect_Video = "/vedio/cancel/collect";

    public static String Server_Send_Comment = "/comment/add";
    public static String Server_Apply_Comment = "/comment/get/list";
    public static String Server_Apply_UnLoginVideo = "/vedio/list";
    public static String Server_ChangePassword = "/user/update/password";
    public static String Server_ModifyPassword = "/user/updatePasswordByPhone";
    public static String Server_FindPassword = "/user/updatePasswordByPhone";
    public static String Server_Login = "/user/findUserByPhoneAndPwd";
    public static String Server_Login_byCode = "/user/login/code";
    public static String Server_getCode = "/user/code";
    public static String Server_Register = "/user/createUser";
    public static String Server_Send_Code = "/user/code";
    public static String Server_LoadUpPhoto = "/user/icon/url";
    public static String Server_ModifyInfo = "/user/update/info";
}