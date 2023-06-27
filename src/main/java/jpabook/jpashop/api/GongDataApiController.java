package jpabook.jpashop.api;

import jpabook.jpashop.domain.Response;
import jpabook.jpashop.repository.OrderGongSearch;
import jpabook.jpashop.service.ApiService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.JDOMException;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GongDataApiController {

    private final ApiService apiService;

    @GetMapping("/api/gong")
    public String gong(@ModelAttribute("orderGongSearch") OrderGongSearch orderGongSearch , Model model) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("경기", "UTF-8")); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*버전별 상세 결과 참고*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        System.out.println(sb.toString());

        // 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
        JSONParser parser = new JSONParser();
        int i = sb.toString().indexOf("\"items\":[{");
        int j = sb.toString().indexOf(",\"pageNo\"");
        // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장.
        JSONObject obj = (JSONObject)parser.parse("{"+sb.toString().substring(i,j)+"}");
        // 3. 필요한 리스트 데이터 부분만 가져와 JSONArray로 저장.
        JSONArray dataArr = (JSONArray) obj.get("items");
        // 4. model에 담아준다.
        if(!StringUtils.isEmpty(orderGongSearch.getStationName())){
            JSONArray dataArr2 = new JSONArray();
            for (int k = 0; k < dataArr.size(); k++) {
                if(((JSONObject) dataArr.get(k)).get("stationName").equals(orderGongSearch.getStationName())) {
                    dataArr2.add(dataArr.get(k));
                }
            }
            model.addAttribute("data",dataArr2);
        }else {
            model.addAttribute("data",dataArr);
        }

        return "order/orderList2";
    }

    @GetMapping("/api/gongsil")
    public String gongsil(@ModelAttribute("orderGongSearch") OrderGongSearch orderGongSearch , Model model) throws IOException, ParseException, JDOMException, JSONException, SAXException, ParserConfigurationException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("202305", "UTF-8")); /*계약월*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        // String 형식의 xml
        String xml = sb.toString();

        // String 형식의 xml을 Java Object인 Response로 변환
        Map<String, Response> result = new HashMap<>();
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Response apiResponse = (Response)unmarshaller.unmarshal(new StringReader(xml));
            result.put("response",apiResponse);
            Response.Body.Items items = apiResponse.getBody().getItems();


            log.info("item을 확인하겠습니다.");
            for(Response.Body.Items.Item item : items.getItem()){
//                log.info("아파트 " + item.get아파트());
                apiService.join(item);
            }
            log.info("실행 완료!");

            model.addAttribute("data",items.getItem());

        }catch (JAXBException e){
            e.printStackTrace();
        }

        return "order/orderList3";
    }


    class bind {

        private String stationName; // 측정소명
        private String mangName; // 측정망 정보
        private String dataTime; // 측정일시
        private String so2Value; // 아황산가스 농도
        private String coValue; // 일산화탄소 농도
        private String o3Value; // 오존 농도
        private String no2Value; // 이산화질소 농도
        private String pm10Value; // 미세먼지(PM10) 농도
        private String pm10Value24; // 미세먼지(PM10) 24시간 예측이동농도
        private String pm25Value; // 초미세먼지(PM2.5) 농도
        private String pm25Value24; // 초미세먼지(PM2.5) 농도24시간 예측이동농도
        private String khaiValue; // 통합대기환경수치
        private String khaiGrade; // 통합대기환경지수
        private String so2Grade; // 아황산가스 지수
        private String coGrade; // 일산화탄소 지수
        private String no2Grade; // 이산화질소 지수
        private String pm10Grade; // 미세먼지(PM10) 24시간 등급자료
        private String pm25Grade; // 초미세먼지(PM2.5) 24시간 등급자료
        private String pm10Grade1h; // 미세먼지(PM10) 1시간 등급자료
        private String pm25Grade1h; // 초미세먼지(PM2.5) 1시간 등급자료
        private String so2Flag; // 아상산가스 플래그
        private String coFlag; // 일산화탄소 플래그
        private String o3Flag; // 오존 플래그
        private String no2Flag; // 이산화질소 플래그
        private String pm10Flag; // 미세먼지(PM10) 플래그
        private String pm25Flag; // 초미세먼지(PM2.5) 플래그
    }
}
