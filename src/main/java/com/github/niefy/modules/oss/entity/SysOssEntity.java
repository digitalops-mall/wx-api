package com.github.niefy.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 文件上传
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_oss")
public class SysOssEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    //URL地址
    private String url;
    //创建时间
    private Date createDate;

}
