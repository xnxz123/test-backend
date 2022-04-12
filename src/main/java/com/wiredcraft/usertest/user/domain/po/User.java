package com.wiredcraft.usertest.user.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Author lei 
 * @Description 用户实体
 * @Date 10:35 2022/4/9
 * @Version 1.0
**/
@Table(value = "user")
@Data
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user name
     */
    private String name;

    /**
     * user password
     */
    private String pwd;

    /**
     * user date of birth
     */
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
    private Date dob;

    /**
     * user address
     */
    private String address;

    /**
     * user description
     */
    private String description;

    /**
     * user longitude 经度
     */
    private Double longitude;


    /**
     * user latitude 纬度
     */
    private Double latitude;


    /**
     * user created date
     */
//    @TableField(fill = FieldFill.INSERT)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private Date createdAt;

    /**
     * update_at
     */

//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private Date updateAt;

    /**
     * version
     */
    private Integer version;

    /**
     * status
     */
    private Integer status = 1;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}