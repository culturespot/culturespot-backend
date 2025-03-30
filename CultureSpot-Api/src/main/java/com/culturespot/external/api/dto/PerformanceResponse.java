package com.culturespot.external.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JacksonXmlRootElement(localName = "response")
public class PerformanceResponse {

  @JacksonXmlProperty(localName = "header")
  private Header header;

  @JacksonXmlProperty(localName = "body")
  private Body body;

  @Getter
  public static class Header {

    @JacksonXmlProperty(localName = "resultCode")
    private String resultCode;

    @JacksonXmlProperty(localName = "resultMsg")
    private String resultMsg;
  }

  @ToString
  @Getter
  public static class Body {

    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;

    @JacksonXmlProperty(localName = "PageNo")
    private int pageNo;

    @JacksonXmlProperty(localName = "numOfrows")
    private int numOfRows;

    @JacksonXmlProperty(localName = "items")
    private Items items;
  }

  @ToString
  @Getter
  public static class Items {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    private List<Item> items;
  }

  @ToString
  @Getter
  public static class Item {

    @JacksonXmlProperty(localName = "serviceName")
    private String serviceName;

    @JacksonXmlProperty(localName = "seq")
    private String seq;

    @JacksonXmlProperty(localName = "title")
    private String title;

    @JacksonXmlProperty(localName = "startDate")
    private String startDate;

    @JacksonXmlProperty(localName = "endDate")
    private String endDate;

    @JacksonXmlProperty(localName = "place")
    private String place;

    @JacksonXmlProperty(localName = "realmName")
    private String realmName;

    @JacksonXmlProperty(localName = "area")
    private String area;

    @JacksonXmlProperty(localName = "thumbnail")
    private String thumbnail;

    @JacksonXmlProperty(localName = "gpsX")
    private String gpsX;

    @JacksonXmlProperty(localName = "gpsY")
    private String gpsY;
  }
}