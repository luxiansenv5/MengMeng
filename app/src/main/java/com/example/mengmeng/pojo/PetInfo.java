package com.example.mengmeng.pojo;

/**
 * Created by 陆猛 on 2016/9/24.
 */
public class PetInfo {
    public Integer petId;
    public String petName;
    public String petType;
    public Integer petAge;
    public String petPhoto;

    public PetInfo(){}

    public PetInfo(Integer petId, String petName, String petType,
                   Integer petAge, String petPhoto) {
        super();
        this.petId = petId;
        this.petName = petName;
        this.petType = petType;
        this.petAge = petAge;
        this.petPhoto=petPhoto;
    }

}
