package cn.beijing.pku.mobilesafe.Http;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

import static cn.beijing.pku.mobilesafe.Utils.ConstantUtil.CATEGORY_URL;
import static cn.beijing.pku.mobilesafe.Utils.ConstantUtil.HOST;
import static cn.beijing.pku.mobilesafe.Utils.ConstantUtil.PATH;

/**
 * Created by Administrator on 2017.6.12.
 */
@HttpRequest(
        host = HOST,
        path = PATH,
        builder = DefaultParamsBuilder.class/*可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/
)
public class ServiceParam extends RequestParams {
    public ServiceParam(){
    }
}
