package fun.china1.picturehandle.service.person;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author dengbo
 */
public interface HistoryShow {
    /**
     *获取最新的数据
     * @param pageNumber 当前页数
     * @param pageSize 需要的页数
     * @param openId 用户的唯一标识
     * @return
     */
    JSONArray getData(Integer pageNumber,Integer pageSize,String openId);

    /**
     * 删除照片
     * @param openId
     * @param ImgId
     * @return
     */
    JSONObject deleteImg(String openId, String ImgId);

}
