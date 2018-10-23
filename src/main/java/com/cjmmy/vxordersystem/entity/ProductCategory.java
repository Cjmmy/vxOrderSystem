package com.cjmmy.vxordersystem.entity;



import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 创建的实体会映射到数据库
 * jpa会自动将数据库表明和类名的驼峰表示对应
 */
//@Table(name = "product_category") //这样不能自动对应的情况下我们要使用注解
@Entity
//@DynamicUpdate
@Data //lombok依赖和插件帮助我们生成getter、setter和toString方法简化代码
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    private String categoryName;
    private int categoryType;

//    为了解释一个现象，我们根据数据库创建这个成员变量，如果我们在对数据进行更新时，会先查询到在更新，因为update_time成为成员变量，那么我们通过
//    查询便能获取到数据库中此时数据update_time的值，我们更改数据时因为不会更改这个变量，所以又将原先的update_time值存入覆盖了本应变化的
//    update_time值，所以我们如果创建了这个的成员变量在更新数据操作是，可能这个update_time就不会自动改变，为了解决这个问题，我们需要注解@DynamicUpdate
//    private Date updateTime;

    public ProductCategory(String categoryName, int categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
//    无参的构造方法，最好在我们自定义了其他构造方法以后写上，因为jvm在自定义了其他构造方法的情况不会为我们自动生成无参构造方法
//    对于jpa(对hibernate封装)，我们自定义查询方法时，如果要返回实体对象，那么我们必须给该实体类设置无参构造方法，因为hibernate需要该无参构造方法
//    通过反射机制class.forName().newInstance()创建对象，而有参构造还不能智能到到底传入什么参数，所以只能调用无参构造创建对象
    public ProductCategory() {
    }

}
