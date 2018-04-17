package cn.tties.energy.api;


import java.util.Map;

import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataFragmentbean;
import cn.tties.energy.model.result.Data_CurrentPressbean;
import cn.tties.energy.model.result.Data_Currentbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.model.result.Data_Factorbean;
import cn.tties.energy.model.result.Data_HaveKwbean;
import cn.tties.energy.model.result.Data_NoKvarbean;
import cn.tties.energy.model.result.Data_Nobean;
import cn.tties.energy.model.result.Data_Pressbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.model.result.Discussbean;
import cn.tties.energy.model.result.EnergyFragmentbean;
import cn.tties.energy.model.result.Energy_BasePlanbean;
import cn.tties.energy.model.result.Energy_Monthlybean;
import cn.tties.energy.model.result.Energy_TransformerDamgebean;
import cn.tties.energy.model.result.Energy_TransformerListbean;
import cn.tties.energy.model.result.Energy_TransformerTemperaturebean;
import cn.tties.energy.model.result.Energy_TransformerVolumebean;
import cn.tties.energy.model.result.Identity_Passbean;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.model.result.Versionbean;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;



public interface Api {


    // login请求方法
    @POST("login.htm")
    @FormUrlEncoded
    Observable<Loginbean> getLogin(@FieldMap Map<String, Object> map);
    //修改密码接口
    @POST("changePassword.htm")
    @FormUrlEncoded
    Observable<Identity_Passbean> gettIdentity_Pass(@FieldMap Map<String, Object> map);
    //版本号请求方法
    @POST("getUpdateVersion.htm")
    Observable<Versionbean> getVersion();
    //Ops问题接口
    @POST("queryQuertionForEnergy.do")
    @FormUrlEncoded
    Observable<Opsbean> getOps(@FieldMap Map<String, Object> map);
    //Ops问题详情
    @POST("queryQuertionForEnergy.do")
    @FormUrlEncoded
    Observable<Opsbean> getOpsQuertion(@FieldMap Map<String, Object> map);
    //回复问题
    @POST("AddAdvice.do")
    @FormUrlEncoded
    Observable<Discussbean> getdiscuss(@FieldMap Map<String, Object> map);
    //能效
    @POST("getScoreRank.htm")
    @FormUrlEncoded
    Observable<EnergyFragmentbean> getEnergyFragment(@FieldMap Map<String, Object> map);
    //报装方案
    @POST("queryDeclareScheme.htm")
    @FormUrlEncoded
    Observable<Energy_BasePlanbean> getEnergy_BasePlan(@FieldMap Map<String, Object> map);
    //变压器列表  运维
    @POST("getBindTransformer.do")
    @FormUrlEncoded
    Observable<Energy_TransformerListbean> getEnergy_TransformerList(@FieldMap Map<String, Object> map);
    //变压器变损  运维
    @POST("getTransformerDamgeAndConsume.do")
    @FormUrlEncoded
    Observable<Energy_TransformerDamgebean> getEnergy_TransformerDamge(@FieldMap Map<String, Object> map);
    //变压器温度  运维
    @POST("getTransformerTemperature.do")
    @FormUrlEncoded
    Observable<Energy_TransformerTemperaturebean> getEnergy_TransformerTemperature(@FieldMap Map<String, Object> map);
    //变压器容量  运维
    @POST("getTransformerVolume.do")
    @FormUrlEncoded
    Observable<Energy_TransformerVolumebean> getEnergy_TransformerVolume(@FieldMap Map<String, Object> map);
    //月报  运维及能效
    @POST("getReportList.do")
    @FormUrlEncoded
    Observable<Energy_Monthlybean> getEnergy_Monthly(@FieldMap Map<String, Object> map);
    //电力数据
    @POST("queryMonthCostData.htm")
    @FormUrlEncoded
    Observable<DataFragmentbean> getDataFragemet(@FieldMap Map<String, Object> map);
    //电费数据  //基本电费优化
    @POST("queryMonthElectricity.htm")
    @FormUrlEncoded
    Observable<Databean> getData(@FieldMap Map<String, Object> map);
    //电量数据
    @POST("queryMonthData.htm")
    @FormUrlEncoded
    Observable<Data_Electricbean> getData_Electric(@FieldMap Map<String, Object> map);
    //有功功率
    @POST("queryMonthData.htm")
    @FormUrlEncoded
    Observable<Data_HaveKwbean> getData_HaveKw(@FieldMap Map<String, Object> map);
    //无功功率
    @POST("queryMonthData.htm")
    @FormUrlEncoded
    Observable<Data_NoKvarbean> getData_NoKvar(@FieldMap Map<String, Object> map);
    //功率因数
    @POST("queryMonthData.htm")
    @FormUrlEncoded
    Observable<Data_Factorbean> getData_Factor(@FieldMap Map<String, Object> map);
    //电流
    @POST("queryMonthData.htm")
    @FormUrlEncoded
    Observable<Data_Currentbean> getData_Current(@FieldMap Map<String, Object> map);
    //电压
    @POST("queryMonthData.htm")
    @FormUrlEncoded
    Observable<Data_CurrentPressbean> getData_CurrentPress(@FieldMap Map<String, Object> map);
    //电压不平衡
    @POST("queryUnbalance.htm")
    @FormUrlEncoded
    Observable<Data_Pressbean> getData_Press(@FieldMap Map<String, Object> map);
    //电流不平衡
    @POST("queryUnbalance.htm")
    @FormUrlEncoded
    Observable<Data_Nobean> getData_No(@FieldMap Map<String, Object> map);
    //总电量
    @POST("getMeterListByLedgerOrMeterId.htm")
    @FormUrlEncoded
    Observable<AllElectricitybean> getAllElectricity(@FieldMap Map<String, Object> map);
    //运维信息登录
    @POST("queryMaintStaff.do")
    Observable<OpsLoginbean> getOpsLogin(@Query("accountId") String id);
//    //获取订单计划详情
//    @POST(Content.PAGE_DETAILS)
//    Observable<ConfirmBean> getDetatlsData(@Query("id") int id);
//    //获取用户地址
//    @POST(Content.USER_ADDRESS)
//    Observable<ReceiptInformationBean> getAdderssData();
//    //登录
//    @POST(Content.LANDING)
//    Observable<LandingBean> getLandData(@Query("username") String username,
//                                        @Query("password") String password);



}
