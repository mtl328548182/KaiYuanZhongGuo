package com.jiyun.kaiyuanzhongguo.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by dell on 2017/4/25.
 */
@Entity
public class SearchDetail {
    @Property(nameInDb = "id")
    @Id(autoincrement = true)
    private Long id;
    private String number;
    @Generated(hash = 524894380)
    public SearchDetail(Long id, String number) {
        this.id = id;
        this.number = number;
    }
    @Generated(hash = 2444021)
    public SearchDetail() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
