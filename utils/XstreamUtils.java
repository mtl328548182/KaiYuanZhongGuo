package com.jiyun.kaiyuanzhongguo.utils;

import com.thoughtworks.xstream.XStream;

import java.util.Map;
import java.util.Set;

import static android.R.attr.x;

/**
 * Created by dell on 2017/5/8.
 */

public class XstreamUtils {
    private XStream xStream=new XStream();
    private static XstreamUtils xstreamUtils;
    public static XstreamUtils getInstance(){
        xstreamUtils=new XstreamUtils();
        return xstreamUtils;
    }
    public XStream alias(Map<String,Class> map){
        if(map.size()>0) {
                Set<String> key=map.keySet();
            for(String str:key){
                Class aClass = map.get(str);
                xStream.alias(str,aClass);
            }
        }
        return xStream;
    }
    public Object fromXml(String xmldata){
       return xStream.fromXML(xmldata);
    }
}
