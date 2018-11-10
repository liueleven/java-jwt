package cn.liueleven.jwt.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class User {

    private Integer id;

    private String email;

    private String phone;

    private String name;

    private String passwd;

    private String confirmPassword;

    /**
     * 1.user 2.agency
     */
    private Integer type;

    private String avatar;
    private MultipartFile avatarFile;

    private String newPassword;

    private Date createTime;
    private Date updateTime;

    private Integer enable;

    private String key;

    private Integer agencyId;

    private String aboutme;

    private String token;


}
