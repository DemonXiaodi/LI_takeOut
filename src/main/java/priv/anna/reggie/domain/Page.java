package priv.anna.reggie.domain;

import lombok.Data;

import java.util.List;

/**
 * - 老师使用mp分页插件
 * - 我这里使用mybatis，为了分页，使用了**PageHelper**分页工具
 * - 且，我使用的page类就是 `com.github.pagehelper.Page;`
 * - 老师使用的page是mp的page类
 * - **注意注意注意！！！前端需要 records 作为当前页要展示的列表数据，还要一个total**
 *     - 老师使用的分页工具默认是 records ，我这里要记得转换
 *     - 老师将他使用page作为R的泛型传回前端
 *     - 我这里要自写一个page，里面包含**records  和  total ,再返回给前端**
 */
@Data
public class Page<T> {
    private List<T> records;
    private long  total;


}
