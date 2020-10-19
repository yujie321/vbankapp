package com.vieboo.vbankapp.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {

    @Id
    private String id;
    //图片地址
    private String imageUrl;
    //姓名
    private String name;
    //性别,0-男1-女
    private Integer sex;
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

    @Generated(hash = 997284292)
    public UserInfo(String id, String imageUrl, String name, Integer sex,
            String number, Integer departmentId, Integer positionId, Integer auth,
            String nation, String company, Date birthday, String phone,
            String address, String politicsStatus, String idCard) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.sex = sex;
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
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
