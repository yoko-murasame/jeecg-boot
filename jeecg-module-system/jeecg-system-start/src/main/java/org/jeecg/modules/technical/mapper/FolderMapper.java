package org.jeecg.modules.technical.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.technical.entity.Folder;

import java.util.List;

public interface FolderMapper extends BaseMapper<Folder> {

    @Select(value = {
            "WITH RECURSIVE resEnd AS (\n" +
                    "    SELECT * FROM public.technical_folder ${endWpstr}\n" +
                    "    UNION ALL\n" +
                    "    SELECT b.* FROM resEnd a INNER JOIN public.technical_folder b ON a.parent_id = b.id\n" +
                    "),resBegin AS (\n" +
                    "    SELECT * FROM public.technical_folder ${beginWpstr}\n" +
                    "    UNION ALL\n" +
                    "    SELECT b.* FROM resBegin a INNER JOIN resEnd b ON a.id = b.parent_id\n" +
                    ")SELECT a.* from resEnd a INNER JOIN resBegin b ON a.id = b.id ORDER BY a.level"
    })
    List<Folder> queryTreeListByFolderNames(@Param("endWp") Wrapper<Folder> endWp,
                                            @Param("beginWp") Wrapper<Folder> beginWp,
                                            @Param("endWpstr") String endWpstr,
                                            @Param("beginWpstr") String beginWpstr);
}
