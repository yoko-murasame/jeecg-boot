package org.jeecg.modules.technical.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.FolderUserPermission;

import java.util.List;

public interface FolderMapper extends BaseMapper<Folder> {

    List<Folder> queryTreeListByFolderNames(@Param("endWp") Wrapper<Folder> endWp,
                                            @Param("beginWp") Wrapper<Folder> beginWp,
                                            @Param("endWpstr") String endWpstr,
                                            @Param("beginWpstr") String beginWpstr);

    List<Folder> selectAuthList(@Param("fdWp") Wrapper<Folder> fdWp,
                                @Param("permWp") Wrapper<FolderUserPermission> permWp,
                                @Param("fdWpStr") String fdWpStr,
                                @Param("permWpStr") String permWpStr);
}
