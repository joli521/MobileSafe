package cn.beijing.pku.mobilesafe.Http;

import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.beijing.pku.mobilesafe.Domain.NewsMenu;
import cn.beijing.pku.mobilesafe.Utils.ConstantUtil;
import cn.beijing.pku.mobilesafe.Utils.LogUtil;

/**
 * Created by wyouflf on 15/11/5.
 */
public class JsonResponseParser implements ResponseParser {// 如果实现 InputStreamResponseParser, 可实现自定义流数据转换.

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // custom check ?
        // get headers ?
    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO: json to java bean
        if (resultClass == List.class) {
            // 这里只是个示例, 不做json转换.
            List<ServiceResponse> list = new ArrayList<ServiceResponse>();
            ServiceResponse serviceResponse = new ServiceResponse();

            serviceResponse.setTest(result);
            list.add(serviceResponse);
            return list;
            // fastJson 解析:
            // return JSON.parseArray(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
        } else {
            // 这里只是个示例, 不做json转换.
            ServiceResponse serviceResponse = new ServiceResponse();
            serviceResponse.setTest(result);
            return serviceResponse;
//             fastjson 解析:
//             return JSON.parseObject(result, resultClass);
        }

    }
}
