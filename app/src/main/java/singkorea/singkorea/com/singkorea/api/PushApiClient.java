package singkorea.singkorea.com.singkorea.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import singkorea.singkorea.com.singkorea.model.AreaCodeModel;
import singkorea.singkorea.com.singkorea.model.BannerModel;
import singkorea.singkorea.com.singkorea.model.BasicModel;
import singkorea.singkorea.com.singkorea.model.CategoryModel;
import singkorea.singkorea.com.singkorea.model.ClipListModel;
import singkorea.singkorea.com.singkorea.model.ClipModel;
import singkorea.singkorea.com.singkorea.model.EstateDetailParentModel;
import singkorea.singkorea.com.singkorea.model.EstateListModel;
import singkorea.singkorea.com.singkorea.model.ExchangeMoneyModel;
import singkorea.singkorea.com.singkorea.model.LoginListModel;
import singkorea.singkorea.com.singkorea.model.MarketDetailParentModel;
import singkorea.singkorea.com.singkorea.model.MarketListModel;
import singkorea.singkorea.com.singkorea.model.PromotionModel;
import singkorea.singkorea.com.singkorea.model.ShopDetailListModel;
import singkorea.singkorea.com.singkorea.model.ShopListModel;
import singkorea.singkorea.com.singkorea.model.ShopModel;
import singkorea.singkorea.com.singkorea.model.SubCategoryModel;
import singkorea.singkorea.com.singkorea.model.TopNewsModel;

/**
 * push Interface
 */
public interface PushApiClient {

    @GET("/api/banner.php")
    @Headers("Content-type: application/json")
    Call<List<BannerModel>> getBanner(@Query("type") String type, @Query("limit") int limit);

    @GET("/api/bestshop.php")
    @Headers("Content-type: application/json")
    Call<List<ShopModel>> getShop(@Query("shoptype") String shoptype);

    @GET("/api/clipList.php")
    @Headers("Content-type: application/json")
    Call<List<ClipModel>> getClip();

    @GET("/api/exchange.php")
    @Headers("Content-type: application/json")
    Call<List<ExchangeMoneyModel>> getExchangeMoney();

    @GET("/api/topnews1.php")
    @Headers("Content-type: application/json")
    Call<List<TopNewsModel>> getTopNews();

    @GET("/api/promotion.php")
    @Headers("Content-type: application/json")
    Call<List<PromotionModel>> getPromotion(@Query("cate") String category);

    @GET("/api/subcategoryList.php")
    @Headers("Content-type: application/json")
    Call<List<SubCategoryModel>> getSubCategory(@Query("cate") String category);

    @GET("/api/shoplist.php")
    @Headers("Content-type: application/json")
    Call<ShopListModel> getShops(@Query("cate") String category, @Query("page") int page, @Query("count") int count, @Query("areaCode") String areaCode);
//    Call<ShopListModel> getShops(@Query("cate") String category, @Query("page") int page, @Query("count") int count);

    @GET("/api/shoplist.php")
    @Headers("Content-type: application/json")
    Call<ShopListModel> getShops(@Query("cate") String category, @Query("page") int page, @Query("count") int count, @Query("areaCode") String areaCode, @Query("search") String search);

    @GET("/api/sublist.php")
    @Headers("Content-type: application/json")
    Call<ShopListModel> getDetailShops(@Query("shoptype") String shoptype, @Query("page") int page, @Query("count") int count);

    @GET("/api/sublist.php")
    @Headers("Content-type: application/json")
    Call<ClipListModel> getDetailClip(@Query("shoptype") String shoptype, @Query("page") int page, @Query("count") int count);

    @GET("/api/shoplist.php")
    @Headers("Content-type: application/json")
    Call<ShopListModel> getShopsArea(@Query("cate") String category, @Query("page") int page, @Query("count") int count, @Query("areaCode") String areaCode);

    @GET("/api/shopdetail.php")
    @Headers("Content-type: application/json")
    Call<ShopDetailListModel> getShopDetail(@Query("idx") String idx);

    @GET("/api/estateList.php")
    @Headers("Content-type: application/json")
    Call<EstateListModel> getEstateList(@Query("cate") String category, @Query("page") int page, @Query("count") int count);

    @GET("/api/estateDetail.php")
    @Headers("Content-type: application/json")
    Call<EstateDetailParentModel> getEstateDetail(@Query("roomid") String idx);

    @GET("/api/marketdetail.php")
    @Headers("Content-type: application/json")
    Call<MarketDetailParentModel> getMarketDetail(@Query("proid") String idx);


    @Multipart
    @POST("/api/estateAdd.php")
    Call<ResponseBody> registerEstate(
            @PartMap() Map<String, RequestBody> partMap, /* Associated with 'dirname' POST variable */
            @Part List<MultipartBody.Part> files); /* Associated with 'attachment[]' POST variable */


    @GET("/api/marketList.php")
    @Headers("Content-type: application/json")
    Call<MarketListModel> getMarketList(@Query("cate") String category, @Query("page") int page);

    @GET("/api/login.php")
    @Headers("Content-type: application/json")
    Call<LoginListModel> login(@Query("userid") String userID, @Query("password") String password);

    @GET("/api/register.php")
    @Headers("Content-type: application/json")
    Call<BasicModel> registerMember(@Query("userid") String userID, @Query("password") String password,
                                    @Query("username") String userName, @Query("email") String email, @Query("location") String location,
                                    @Query("regtype") String regType);

    @GET("/api/idcheck.php")
    @Headers("Content-type: application/json")
    Call<BasicModel> checkId(@Query("UserID") String userID);

    @Multipart
    @POST("/api/marketAdd.php")
    Call<ResponseBody> registerMarket(
            @PartMap() Map<String, RequestBody> partMap, /* Associated with 'dirname' POST variable */
            @Part List<MultipartBody.Part> files); /* Associated with 'attachment[]' POST variable */


    @GET("/api/areaCodeList.php")
    @Headers("Content-type: application/json")
    Call<List<AreaCodeModel>> getAreaCode();

    @GET("/api/marketCategoryList.php")
    @Headers("Content-type: application/json")
    Call<List<CategoryModel>> getCategory();
}