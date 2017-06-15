package com.dashubio.model.ucenter;


import com.dashubio.model.DiseaseHistoryItem;
import com.dashubio.model.HealthFilesItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 设备用户
 */
public class SecondUser implements Serializable{

    public final static int NO_SEX = 0;//无
    public final static int MALE = 1;//男
    public final static int FEMALE = 2;//女

    public final static int UNKOWN_HOUSEHOLD_REGISTER = -1;//不知道户籍
    public final static int HOUSEHOLD_REGISTER = 0;//户籍
    public final static int NO_HOUSEHOLD_REGISTER = 1;//非户籍

    public String id;
    private String e_id;


    @SerializedName("phone")
    private String phone;//用户手机号


    @SerializedName("phone_contacts")
    private String mphone;//常用联系人电话

    private String card_img;
    private String photo;//

    @SerializedName("name")
    public String fullname;//姓名
    private int sex;
    private String birth;//出生
    private String nation;//民族
    @SerializedName("card_id")
    public String cardId;//身份证
    private int resident;//户籍(0:户籍,1非户籍)
    @SerializedName("work")
    private String workUnit;//工作单位
    private int blood;//血型

    @SerializedName("blood_hr")
    private int rhNegative;//RH阴性

    private int edu;//文化程度

    @SerializedName("occ")
    private int profession;//职业

    @SerializedName("marr")
    private int maritalStatus;//婚姻状况

    private String addtime;

    @SerializedName("pay_type")
    private int payType;//医疗费用支付方式

    @SerializedName("allergor")
    private int drugAllergyHistory;//药物过敏史

    @SerializedName("expose")
    private int exposureHistory;//暴露史


    @SerializedName("p_dis")//疾病
    private List<HealthFilesItem>  diseaseList;

    @SerializedName("p_opera")
    private List<DiseaseHistoryItem> operationHistoryList;//手术

    @SerializedName("p_trau")
    private List<DiseaseHistoryItem> traumaHistoryList;//外伤

    @SerializedName("p_trans")
    private List<DiseaseHistoryItem> bloodTransfusionHistoryList;//输血

    @SerializedName("f_p")
    private List<HealthFilesItem>  fatherList;
//    private int familyHistoryFather;//-家族史  父亲

    @SerializedName("f_m")
    private List<HealthFilesItem>  motherList;
//    private int familyHistoryMother;//家族史  母亲

    @SerializedName("f_chi")
    private List<HealthFilesItem>  childrenList;
//    private int familyHistoryChildren;//家族史  子女

    @SerializedName("inheri")
    private String geneticHistory;//遗传病史

    @SerializedName("deformity")
    private int disabilityStatus;//残疾情况

    @SerializedName("exha")
    private int kitchenExhaustFacilities;//厨房排风设施

    @SerializedName("fuel")
    private int fuelType;//燃料类型

    private int water;//饮水

    @SerializedName("wc")
    private int waterCloset;//厕所

    @SerializedName("poultry")
    private int birdStorageField;//禽蓄栏


    @SerializedName("province")
    private String province;//省

    @SerializedName("city")
    private String mcity;//城市

    @SerializedName("district")
    private String district;//地区

    @SerializedName("address")
    private String address;//地址

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMcity() {
        return mcity;
    }

    public void setMcity(String mcity) {
        this.mcity = mcity;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<HealthFilesItem> getFatherList() {
        return fatherList;
    }

    public void setFatherList(List<HealthFilesItem> fatherList) {
        this.fatherList = fatherList;
    }

    public List<HealthFilesItem> getMotherList() {
        return motherList;
    }

    public void setMotherList(List<HealthFilesItem> motherList) {
        this.motherList = motherList;
    }

    public List<HealthFilesItem> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<HealthFilesItem> childrenList) {
        this.childrenList = childrenList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getCard_img() {
        return card_img;
    }

    public void setCard_img(String card_img) {
        this.card_img = card_img;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getResident() {
        return resident;
    }

    public void setResident(int resident) {
        this.resident = resident;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getRhNegative() {
        return rhNegative;
    }

    public void setRhNegative(int rhNegative) {
        this.rhNegative = rhNegative;
    }

    public int getEdu() {
        return edu;
    }

    public void setEdu(int edu) {
        this.edu = edu;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getDrugAllergyHistory() {
        return drugAllergyHistory;
    }

    public void setDrugAllergyHistory(int drugAllergyHistory) {
        this.drugAllergyHistory = drugAllergyHistory;
    }

    public int getExposureHistory() {
        return exposureHistory;
    }

    public void setExposureHistory(int exposureHistory) {
        this.exposureHistory = exposureHistory;
    }

    public List<HealthFilesItem> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(List<HealthFilesItem> diseaseList) {
        this.diseaseList = diseaseList;
    }

    public List<DiseaseHistoryItem> getOperationHistoryList() {
        return operationHistoryList;
    }

    public void setOperationHistoryList(List<DiseaseHistoryItem> operationHistoryList) {
        this.operationHistoryList = operationHistoryList;
    }

    public List<DiseaseHistoryItem> getTraumaHistoryList() {
        return traumaHistoryList;
    }

    public void setTraumaHistoryList(List<DiseaseHistoryItem> traumaHistoryList) {
        this.traumaHistoryList = traumaHistoryList;
    }

    public List<DiseaseHistoryItem> getBloodTransfusionHistoryList() {
        return bloodTransfusionHistoryList;
    }

    public void setBloodTransfusionHistoryList(List<DiseaseHistoryItem> bloodTransfusionHistoryList) {
        this.bloodTransfusionHistoryList = bloodTransfusionHistoryList;
    }

    /*public int getFamilyHistoryFather() {
        return familyHistoryFather;
    }

    public void setFamilyHistoryFather(int familyHistoryFather) {
        this.familyHistoryFather = familyHistoryFather;
    }

    public int getFamilyHistoryMother() {
        return familyHistoryMother;
    }

    public void setFamilyHistoryMother(int familyHistoryMother) {
        this.familyHistoryMother = familyHistoryMother;
    }

    public int getFamilyHistoryChildren() {
        return familyHistoryChildren;
    }

    public void setFamilyHistoryChildren(int familyHistoryChildren) {
        this.familyHistoryChildren = familyHistoryChildren;
    }*/

    public String getGeneticHistory() {
        return geneticHistory;
    }

    public void setGeneticHistory(String geneticHistory) {
        this.geneticHistory = geneticHistory;
    }

    public int getDisabilityStatus() {
        return disabilityStatus;
    }

    public void setDisabilityStatus(int disabilityStatus) {
        this.disabilityStatus = disabilityStatus;
    }

    public int getKitchenExhaustFacilities() {
        return kitchenExhaustFacilities;
    }

    public void setKitchenExhaustFacilities(int kitchenExhaustFacilities) {
        this.kitchenExhaustFacilities = kitchenExhaustFacilities;
    }

    public int getFuelType() {
        return fuelType;
    }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getWaterCloset() {
        return waterCloset;
    }

    public void setWaterCloset(int waterCloset) {
        this.waterCloset = waterCloset;
    }

    public int getBirdStorageField() {
        return birdStorageField;
    }

    public void setBirdStorageField(int birdStorageField) {
        this.birdStorageField = birdStorageField;
    }
}
