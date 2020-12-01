package com.vieboo.vbankapp.data;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@Entity
public class UserInfo {

    @Id(autoincrement = true)
    private Long id;
    //人员ID
    private String personId;
    //图片地址
    private String imageUrl;
    //姓名
    private String name;
    //性别,0-男1-女
    private Integer sex;
    //员工类型 Guest：访客,Employee:员工
    private String type;
    //工号
    private String number;
    //部门id
    private Integer departmentId;
    //职务id
    private Integer positionId;
    //权限,0-领导-有审批权,1-保卫人员,2-值班人员,3-外部人员,4-管库员,5-长期人员,6-临时人员
    private Integer auth;
    //民族
    private String nation;
    //单位
    private String company;
    //出生日期,格式yyyy-MM-dd
    private Date birthday;
    //联系电话
    private String phone;
    //身份证住址
    private String address;
    //政治面貌
    private String politicsStatus;
    //身份证号
    private String idCard;
    //特征值数据
    private byte[] padFeature;
    @Generated(hash = 704128678)
    public UserInfo(Long id, String personId, String imageUrl, String name,
            Integer sex, String type, String number, Integer departmentId,
            Integer positionId, Integer auth, String nation, String company,
            Date birthday, String phone, String address, String politicsStatus,
            String idCard, byte[] padFeature) {
        this.id = id;
        this.personId = personId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.sex = sex;
        this.type = type;
        this.number = number;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.auth = auth;
        this.nation = nation;
        this.company = company;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.politicsStatus = politicsStatus;
        this.idCard = idCard;
        this.padFeature = padFeature;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPersonId() {
        return this.personId;
    }
    public void setPersonId(String personId) {
        this.personId = personId;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getSex() {
        return this.sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Integer getDepartmentId() {
        return this.departmentId;
    }
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    public Integer getPositionId() {
        return this.positionId;
    }
    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
    public Integer getAuth() {
        return this.auth;
    }
    public void setAuth(Integer auth) {
        this.auth = auth;
    }
    public String getNation() {
        return this.nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public Date getBirthday() {
        return this.birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPoliticsStatus() {
        return this.politicsStatus;
    }
    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }
    public String getIdCard() {
        return this.idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public byte[] getPadFeature() {
        return this.padFeature;
    }
    public void setPadFeature(byte[] padFeature) {
        this.padFeature = padFeature;
    }
    
}
