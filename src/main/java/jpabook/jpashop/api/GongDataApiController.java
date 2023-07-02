package jpabook.jpashop.api;

import jpabook.jpashop.domain.Response;
import jpabook.jpashop.domain.ResponseOutInterface;
import jpabook.jpashop.repository.OrderApiSearch;
import jpabook.jpashop.repository.OrderGongSearch;
import jpabook.jpashop.service.ApiService;
import jpabook.jpashop.service.GongDataApiService;
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
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

    private final GongDataApiService gongDataApiService;


    @GetMapping("/api/gong")
    public String gong(@ModelAttribute("orderGongSearch") OrderGongSearch orderGongSearch , Model model) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + serviceKey); /*Service Key*/
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

    @GetMapping("/api/gongsil2")
    public void gongsil(@ModelAttribute("orderGongSearch") OrderGongSearch orderGongSearch , Model model) throws IOException, ParseException, JDOMException, JSONException, SAXException, ParserConfigurationException {
        List<String> lawdTmpList = new ArrayList<>();
        lawdTmpList.add("11680");
        lawdTmpList.add("11740");
        lawdTmpList.add("11305");
        lawdTmpList.add("11500");
        lawdTmpList.add("11620");
        lawdTmpList.add("11215");
        lawdTmpList.add("11530");
        lawdTmpList.add("11545");
        lawdTmpList.add("11350");
        lawdTmpList.add("11320");
        lawdTmpList.add("11230");
        lawdTmpList.add("11590");
        lawdTmpList.add("11440");
        lawdTmpList.add("11410");
        lawdTmpList.add("11650");
        lawdTmpList.add("11200");
        lawdTmpList.add("11290");
        lawdTmpList.add("11710");
        lawdTmpList.add("11470");
        lawdTmpList.add("11560");
        lawdTmpList.add("11170");
        lawdTmpList.add("11380");
        lawdTmpList.add("11110");
        lawdTmpList.add("11140");
        lawdTmpList.add("11260");
        lawdTmpList.add("26440");
        lawdTmpList.add("26410");
        lawdTmpList.add("26710");
        lawdTmpList.add("26290");
        lawdTmpList.add("26170");
        lawdTmpList.add("26260");
        lawdTmpList.add("26230");
        lawdTmpList.add("26320");
        lawdTmpList.add("26530");
        lawdTmpList.add("26380");
        lawdTmpList.add("26140");
        lawdTmpList.add("26500");
        lawdTmpList.add("26470");
        lawdTmpList.add("26200");
        lawdTmpList.add("26110");
        lawdTmpList.add("26350");
        lawdTmpList.add("27200");
        lawdTmpList.add("27290");
        lawdTmpList.add("27710");
        lawdTmpList.add("27140");
        lawdTmpList.add("27230");
        lawdTmpList.add("27170");
        lawdTmpList.add("27260");
        lawdTmpList.add("27110");
        lawdTmpList.add("28710");
        lawdTmpList.add("28245");
        lawdTmpList.add("28200");
        lawdTmpList.add("28140");
        lawdTmpList.add("28177");
        lawdTmpList.add("28237");
        lawdTmpList.add("28260");
        lawdTmpList.add("28185");
        lawdTmpList.add("28720");
        lawdTmpList.add("28110");
        lawdTmpList.add("29200");
        lawdTmpList.add("29155");
        lawdTmpList.add("29110");
        lawdTmpList.add("29170");
        lawdTmpList.add("29140");
        lawdTmpList.add("30230");
        lawdTmpList.add("30110");
        lawdTmpList.add("30170");
        lawdTmpList.add("30200");
        lawdTmpList.add("30140");
        lawdTmpList.add("31140");
        lawdTmpList.add("31170");
        lawdTmpList.add("31200");
        lawdTmpList.add("31710");
        lawdTmpList.add("31110");
        lawdTmpList.add("36110");
        lawdTmpList.add("41820");
        lawdTmpList.add("41281");
        lawdTmpList.add("41285");
        lawdTmpList.add("41287");
        lawdTmpList.add("41290");
        lawdTmpList.add("41210");
        lawdTmpList.add("41610");
        lawdTmpList.add("41310");
        lawdTmpList.add("41410");
        lawdTmpList.add("41570");
        lawdTmpList.add("41360");
        lawdTmpList.add("41250");
        lawdTmpList.add("41190");
        lawdTmpList.add("41135");
        lawdTmpList.add("41131");
        lawdTmpList.add("41133");
        lawdTmpList.add("41113");
        lawdTmpList.add("41117");
        lawdTmpList.add("41111");
        lawdTmpList.add("41115");
        lawdTmpList.add("41390");
        lawdTmpList.add("41273");
        lawdTmpList.add("41271");
        lawdTmpList.add("41550");
        lawdTmpList.add("41173");
        lawdTmpList.add("41171");
        lawdTmpList.add("41630");
        lawdTmpList.add("41830");
        lawdTmpList.add("41670");
        lawdTmpList.add("41800");
        lawdTmpList.add("41370");
        lawdTmpList.add("41463");
        lawdTmpList.add("41465");
        lawdTmpList.add("41461");
        lawdTmpList.add("41430");
        lawdTmpList.add("41150");
        lawdTmpList.add("41500");
        lawdTmpList.add("41480");
        lawdTmpList.add("41220");
        lawdTmpList.add("41650");
        lawdTmpList.add("41450");
        lawdTmpList.add("41590");
        lawdTmpList.add("42150");
        lawdTmpList.add("42820");
        lawdTmpList.add("42170");
        lawdTmpList.add("42230");
        lawdTmpList.add("42210");
        lawdTmpList.add("42800");
        lawdTmpList.add("42830");
        lawdTmpList.add("42750");
        lawdTmpList.add("42130");
        lawdTmpList.add("42810");
        lawdTmpList.add("42770");
        lawdTmpList.add("42780");
        lawdTmpList.add("42110");
        lawdTmpList.add("42190");
        lawdTmpList.add("42760");
        lawdTmpList.add("42720");
        lawdTmpList.add("42790");
        lawdTmpList.add("42730");
        lawdTmpList.add("43760");
        lawdTmpList.add("43800");
        lawdTmpList.add("43720");
        lawdTmpList.add("43740");
        lawdTmpList.add("43730");
        lawdTmpList.add("43770");
        lawdTmpList.add("43150");
        lawdTmpList.add("43745");
        lawdTmpList.add("43750");
        lawdTmpList.add("43111");
        lawdTmpList.add("43112");
        lawdTmpList.add("43114");
        lawdTmpList.add("43113");
        lawdTmpList.add("43130");
        lawdTmpList.add("44250");
        lawdTmpList.add("44150");
        lawdTmpList.add("44710");
        lawdTmpList.add("44230");
        lawdTmpList.add("44270");
        lawdTmpList.add("44180");
        lawdTmpList.add("44760");
        lawdTmpList.add("44210");
        lawdTmpList.add("44770");
        lawdTmpList.add("44200");
        lawdTmpList.add("44810");
        lawdTmpList.add("44131");
        lawdTmpList.add("44133");
        lawdTmpList.add("44790");
        lawdTmpList.add("44825");
        lawdTmpList.add("44800");
        lawdTmpList.add("45790");
        lawdTmpList.add("45130");
        lawdTmpList.add("45210");
        lawdTmpList.add("45190");
        lawdTmpList.add("45730");
        lawdTmpList.add("45800");
        lawdTmpList.add("45770");
        lawdTmpList.add("45710");
        lawdTmpList.add("45140");
        lawdTmpList.add("45750");
        lawdTmpList.add("45740");
        lawdTmpList.add("45113");
        lawdTmpList.add("45111");
        lawdTmpList.add("45180");
        lawdTmpList.add("45720");
        lawdTmpList.add("46810");
        lawdTmpList.add("46770");
        lawdTmpList.add("46720");
        lawdTmpList.add("46230");
        lawdTmpList.add("46730");
        lawdTmpList.add("46170");
        lawdTmpList.add("46710");
        lawdTmpList.add("46110");
        lawdTmpList.add("46840");
        lawdTmpList.add("46780");
        lawdTmpList.add("46150");
        lawdTmpList.add("46910");
        lawdTmpList.add("46130");
        lawdTmpList.add("46870");
        lawdTmpList.add("46830");
        lawdTmpList.add("46890");
        lawdTmpList.add("46880");
        lawdTmpList.add("46800");
        lawdTmpList.add("46900");
        lawdTmpList.add("46860");
        lawdTmpList.add("46820");
        lawdTmpList.add("46790");
        lawdTmpList.add("47290");
        lawdTmpList.add("47130");
        lawdTmpList.add("47830");
        lawdTmpList.add("47190");
        lawdTmpList.add("47720");
        lawdTmpList.add("47150");
        lawdTmpList.add("47280");
        lawdTmpList.add("47920");
        lawdTmpList.add("47250");
        lawdTmpList.add("47840");
        lawdTmpList.add("47170");
        lawdTmpList.add("47770");
        lawdTmpList.add("47760");
        lawdTmpList.add("47210");
        lawdTmpList.add("47230");
        lawdTmpList.add("47900");
        lawdTmpList.add("47940");
        lawdTmpList.add("47930");
        lawdTmpList.add("47730");
        lawdTmpList.add("47820");
        lawdTmpList.add("47750");
        lawdTmpList.add("47850");
        lawdTmpList.add("47111");
        lawdTmpList.add("47113");
        lawdTmpList.add("48310");
        lawdTmpList.add("48880");
        lawdTmpList.add("48820");
        lawdTmpList.add("48250");
        lawdTmpList.add("48840");
        lawdTmpList.add("48270");
        lawdTmpList.add("48240");
        lawdTmpList.add("48860");
        lawdTmpList.add("48330");
        lawdTmpList.add("48720");
        lawdTmpList.add("48170");
        lawdTmpList.add("48740");
        lawdTmpList.add("48125");
        lawdTmpList.add("48127");
        lawdTmpList.add("48123");
        lawdTmpList.add("48121");
        lawdTmpList.add("48129");
        lawdTmpList.add("48220");
        lawdTmpList.add("48850");
        lawdTmpList.add("48730");
        lawdTmpList.add("48870");
        lawdTmpList.add("48890");
        lawdTmpList.add("50130");
        lawdTmpList.add("50110");
        String LAWD_CD ="";
//        for (int i = 0; i < lawdTmpList.size(); i++) {
        for (int i = 0; i < 3; i++) {
            LAWD_CD = lawdTmpList.get(i);
            StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(LAWD_CD, "UTF-8")); /*지역코드*/
            urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode("202305", "UTF-8")); /*계약월*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Response apiResponse = (Response) unmarshaller.unmarshal(new StringReader(xml));
                result.put("response", apiResponse);
                Response.Body.Items items = apiResponse.getBody().getItems();


                log.info("item을 확인하겠습니다.");

                for (Response.Body.Items.aptItem item : items.getItem()) {
//                log.info("아파트 " + item.get아파트());
                    apiService.join(item);
                }
                log.info("실행 완료!");

//            model.addAttribute("data",items.getItem());

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
//        return "order/orderList3";
    }


    @GetMapping("/api/gongsil")
    public String gongsil2(@ModelAttribute("orderApiSearch") OrderApiSearch orderApiSearch , Model model) throws IOException, ParseException, JDOMException, JSONException, SAXException, ParserConfigurationException {
        String stationName = orderApiSearch.getStationName();
        if (null == stationName){
            List<ResponseOutInterface> allFind = gongDataApiService.findAll2();
            model.addAttribute("data",allFind);
        } else {
            List<ResponseOutInterface> allFind = gongDataApiService.findWhere(stationName);
            model.addAttribute("data",allFind);
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
