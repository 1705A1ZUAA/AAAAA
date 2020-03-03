package com.wd.health.api;

import com.wd.health.bean.DepartmentBean;
import com.wd.health.bean.DiseaseCategoryBean;
import com.wd.health.bean.BannerBean;
import com.wd.health.bean.ConsultingListBean;
import com.wd.health.bean.DrugBean;
import com.wd.health.bean.HealthBean;
import com.wd.health.bean.JianKangBean;
import com.wd.health.bean.KeListBean;
import com.wd.health.bean.LoginBean;
import com.wd.health.bean.SymptomBean;
import com.wd.health.shipin.bean.ChaXunShiPin_ResutBean;
import com.wd.health.shipin.bean.CollectBean;
import com.wd.health.shipin.bean.Commentbean;
import com.wd.health.shipin.bean.VideoCommentList;
import com.wd.health.shipin.bean.Video_TablayoutResultBean;
import com.wd.health.shipin.bean.Videobuybean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IApi {
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<LoginBean> login(@FieldMap Map<String, Object> map);

    //根据科室查询
    @GET("share/knowledgeBase/v1/findDepartment")
     Observable<DepartmentBean> getDepart();

    @GET("share/knowledgeBase/v1/findDiseaseCategory")
    Observable<DiseaseCategoryBean> getDiseaseCategory(@Query("departmentId")int depatid);

    //banner轮播
     @GET("share/v1/bannersShow")
     Observable<BannerBean> getBanner();
    //症状
    @GET("share/knowledgeBase/v1/findDiseaseCategory")
    Observable<SymptomBean> getSymptom();
    //常见药瓶
    @GET("share/knowledgeBase/v1/findDiseaseCategory")
    Observable<DrugBean> getDrug(@Query("drugsCategoryId") int drugsCategoryId,@Query("page") int page,@Query("count") int count);
    //查询科室列表
    @GET("share/knowledgeBase/v1/findDepartment")
    Observable<KeListBean> getKeLian();
    //查询健康资讯板块
    @GET("share/information/v1/findInformationPlateList")
    Observable<HealthBean> getHealthBean();
    //根据资讯板块查询资讯列表
    @GET("share/information/v1/findInformationList")
    Observable<ConsultingListBean> getConsultingList(int plateId,int page, int count);
    //健康讲堂类目
    @GET("user/video/v1/findVideoCategoryList")
    Observable<JianKangBean> getJianKang();
    //视频
    @GET("user/video/v1/findVideoCategoryList")
    Observable<Video_TablayoutResultBean> tablyList();

    @GET("user/video/v1/findVideoVoList")
    @Headers({"userId:91145","sessionId:1581151729091145"})
    Observable<ChaXunShiPin_ResutBean> chaXun_Vidio(@QueryMap Map<String,Object> map);

    @POST("userideoerify1/addUserVideoCollection")
    @Headers({"userId:58145","sessionId:1582427298358145"})
    Observable<CollectBean> colleCtion(@Header("userId")  Integer userid , @Header("sessionId") String sessionid, @QueryMap Map<String,Object> map);
    //视频弹幕
    @POST("userideoerify1/addVideoComment")
    @Headers({"userId:58145","sessionId:1582427298358145"})
    Observable<Commentbean> comment(@QueryMap Map<String,Object> map);
    //视频的购买
    @POST("userideoerify1ideoBuy")
    @Headers({"userId:58145","sessionId:1582427298358145"})
    Observable<Videobuybean> buy(@QueryMap Map<String,Object> map);
    //查询视频的评论
    @GET("userideo1/findVideoCommentList")
    Observable<VideoCommentList> commentList(@Query("videoId") Integer id);

//
//    @POST("user1ndOutEmailCode")
//    Observable<YanZhengMaBean> yanZhengMa(@Query("email") String mail);
//    @POST("user1/register")
//    Observable<RegisterBean> register(@QueryMap Map<String,String> map);
//    @POST("user1/login")
//    Observable<LogainBean> logainBean(@QueryMap Map<String,String> map);


}
