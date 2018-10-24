package com.cjmmy.vxordersystem.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 因为前端要求传回去的time单位为秒，而我们的数据则存储的是毫秒，所以我们需要将数据库中的date数据进行转换，转换成long值
 * 这个类就是做这个的，并且我们还要在与数据库的实体类上进行注解配置
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime()/1000);
    }
}
