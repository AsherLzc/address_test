package com.asher.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public  class  LocationUtils {    
    
    //public static String cityName = "����";  //������    
    public static String cityName = " ";  //������    
        
    private static Geocoder geocoder;   //�˶�����ͨ��γ������ȡ��Ӧ�ĳ��е���Ϣ    
        
    /**  
     * ͨ���������ȡ������  ����CN�ֱ���city��name������ĸ��д  
     * @param context  
     */    
    public static void getCNBylocation(Context context){    
            
        geocoder = new Geocoder(context);    
        //���ڻ�ȡLocation�����Լ�����    
        LocationManager locationManager;     
        String serviceName = Context.LOCATION_SERVICE;    
        //ʵ��һ��LocationManager����    
        locationManager = (LocationManager)context.getSystemService(serviceName);    
        //provider������    
        String provider = LocationManager.NETWORK_PROVIDER;    
    
        Criteria criteria = new Criteria();    
        criteria.setAccuracy(Criteria.ACCURACY_FINE);   //�߾���    
        criteria.setAltitudeRequired(false);    //��Ҫ�󺣰�    
        criteria.setBearingRequired(false); //��Ҫ��λ    
        criteria.setCostAllowed(false); //�������л���    
        criteria.setPowerRequirement(Criteria.POWER_LOW);   //�͹���    
            
        //ͨ�����һ�εĵ���λ�������Location����    
        Location location = locationManager.getLastKnownLocation(provider);    
            
        String queryed_name = updateWithNewLocation(location);    
        if((queryed_name != null) && (0 != queryed_name.length())){    
                
            cityName = queryed_name;    
        }    
            
        /*  
         * �ڶ��������ʾ���µ����ڣ���λΪ���룻���������ĺ����ʾ��С����������λ����  
         * �趨ÿ30�����һ���Զ���λ  
         */    
        locationManager.requestLocationUpdates(provider, 30000, 50,    
                locationListener);    
        //�Ƴ����������ֻ��һ��widget��ʱ������������õ�    
        locationManager.removeUpdates(locationListener);    
    }    
        
    /**  
     * ��λ�ı�ʱ���������е���  
     */    
    private final static LocationListener locationListener = new LocationListener() {    
        String tempCityName;    
        public void onLocationChanged(Location location) {    
                
            tempCityName = updateWithNewLocation(location);    
            if((tempCityName != null) && (tempCityName.length() != 0)){    
                    
                cityName = tempCityName;    
            }    
        }    
    
        public void onProviderDisabled(String provider) {    
            tempCityName = updateWithNewLocation(null);    
            if ((tempCityName != null) && (tempCityName.length() != 0)) {    
    
                cityName = tempCityName;    
            }    
        }    
    
        public void onProviderEnabled(String provider) {    
        }    
    
        public void onStatusChanged(String provider, int status, Bundle extras) {    
        }    
    };    
    
    /**  
     * ����location  
     * @param location  
     * @return cityName  
     */    
    private static String updateWithNewLocation(Location location) {    
        String mcityName = "";    
        double lat = 0;    
        double lng = 0;    
        List<Address> addList = null;    
        if (location != null) {    
            lat = location.getLatitude();    
            lng = location.getLongitude();    
        } else {    
    
            System.out.println("�޷���ȡ������Ϣ");    
        }    
             
        try {    
                
            addList = geocoder.getFromLocation(lat, lng, 1);    //������γ��    
                
        } catch (IOException e) {    
            // TODO Auto-generated catch block    
            e.printStackTrace();    
        }    
        if (addList != null && addList.size() > 0) {    
            for (int i = 0; i < addList.size(); i++) {    
                Address add = addList.get(i);    
                mcityName += add.getLocality();    
            }    
        }    
        if(mcityName.length()!=0){    
                
            return mcityName.substring(0, (mcityName.length()-1));    
        } else {    
            return mcityName;    
        }    
    }    
    
    /**  
     * ͨ��γ�Ȼ�ȡ��ַ��Ϣ����һ�ַ���  
     * @param latitude  
     * @param longitude  
     * @return ������  
     */    
    public static String GetAddr(String latitude, String longitude) {      
        String addr = "";
            
        /*  
         * Ҳ������http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s�����������������Ӣ�ĵ�ַ  
         * ��Կ�������дһ��key=abc  
         * output=csv,Ҳ������xml��json������ʹ��csv���ص�������෽�����      
         */    
        String url = String.format(      
            "http://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s",      
            latitude, longitude);      
        URL myURL = null;      
        URLConnection httpsConn = null;      
        try {      
                
            myURL = new URL(url);      
        } catch (MalformedURLException e) {      
          e.printStackTrace();      
          return null;      
        }      
            
        try {      
            
            httpsConn = (URLConnection) myURL.openConnection();      
                
            if (httpsConn != null) {      
                InputStreamReader insr = new InputStreamReader(      
                        httpsConn.getInputStream(), "UTF-8");      
                BufferedReader br = new BufferedReader(insr);      
                String data = null;      
                if ((data = br.readLine()) != null) {      
                    String[] retList = data.split(",");      
                    if (retList.length > 2 && ("200".equals(retList[0]))) {      
                        addr = retList[2];      
                    } else {      
                        addr = "";      
                    }      
                }      
                insr.close();      
            }      
       } catch (IOException e) {      
            
            e.printStackTrace();      
           return null;      
        }      
           return addr;      
    }    
        
}

