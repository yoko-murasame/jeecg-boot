package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
@TableName("sys_upload")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUpload extends JeecgEntity {

    private static final long serialVersionUID = 1L;

    @Excel(name = "文件名称")
    private String fileName;

    @Excel(name = "文件地址")
    private String url;

    @Excel(name = "md5")
    private String md5;

}
