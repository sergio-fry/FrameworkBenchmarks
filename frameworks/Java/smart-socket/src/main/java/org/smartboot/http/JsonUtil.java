package org.smartboot.http;

import com.jsoniter.output.JsonStream;
import com.jsoniter.output.JsonStreamPool;
import com.jsoniter.spi.JsonException;
import com.jsoniter.spi.Slice;
import jakarta.servlet.http.HttpServletResponse;
import tech.smartboot.feat.core.server.HttpResponse;

import java.io.IOException;

/**
 * @author 三刀
 * @version V1.0 , 2020/6/16
 */
public class JsonUtil {
    //    public static void writeJsonBytes(HttpResponse httpResponse, Object obj) {
//        JsonWriter writer = Eson.THREAD_WRITER.get();
//        try {
//            writer.reset();
//            Eson.serialize(obj, writer);
//            httpResponse.setContentLength(writer.size());
//            writer.toStream(httpResponse.getOutputStream());
//        } catch (IOException e) {
//            throw new JsonException(e);
//        }
//    }
    public static void writeJsonBytes(HttpResponse httpResponse, Object obj) {
        JsonStream stream = JsonStreamPool.borrowJsonStream();
        try {
            stream.reset(null);
            stream.writeVal(obj.getClass(), obj);
            Slice slice = stream.buffer();
            httpResponse.setContentLength(slice.tail());
            httpResponse.getOutputStream().write(slice.data(), 0, slice.tail());
        } catch (IOException e) {
            throw new JsonException(e);
        } finally {
            JsonStreamPool.returnJsonStream(stream);
        }
    }

    public static void writeJsonBytes(HttpServletResponse httpResponse, Object obj) {
        JsonStream stream = JsonStreamPool.borrowJsonStream();
        try {
            stream.reset(null);
            stream.writeVal(obj.getClass(), obj);
            Slice slice = stream.buffer();
            httpResponse.setContentLength(slice.tail());
            httpResponse.getOutputStream().write(slice.data(), 0, slice.tail());
        } catch (IOException e) {
            throw new JsonException(e);
        } finally {
            JsonStreamPool.returnJsonStream(stream);
        }
    }

//    public static void writeJsonBytes(HttpServletResponse httpResponse, Object obj) {
//        JsonWriter writer = Eson.THREAD_WRITER.get();
//        try {
//            writer.reset();
//            Eson.serialize(obj, writer);
//            httpResponse.setContentLength(writer.size());
//            writer.toStream(httpResponse.getOutputStream());
//        } catch (IOException e) {
//            throw new JsonException(e);
//        }
//    }
}
