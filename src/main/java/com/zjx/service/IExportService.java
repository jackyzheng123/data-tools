package com.zjx.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/1/6 13:46
 * @Version V1.0
 **/
public interface IExportService {

    void download(HttpServletResponse response);

    void downloadFailedUsingJson(HttpServletResponse response);
}
