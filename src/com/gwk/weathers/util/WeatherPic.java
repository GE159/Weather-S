package com.gwk.weathers.util;



import com.gwk.weathers.app.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WeatherPic {
	/**
	 * 天气代码转图片
	 * @param c 
	 * @param index 天气码
	 * @param type	today\-0 
	 * @return bitmap
	 */
	public static Bitmap getPic(Context c, int index, int type){
		Bitmap bmp = null;
		switch(index){
		case 0:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_0);
			break;
		case 1:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_1);
			break;
		case 2:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_2);
			break;
		case 3:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_3);
			break;
		case 4:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_4);
			break;
		case 5:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_5);
			break;
		case 6:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_6);
			break;
		case 7:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_7);
			break;
		case 8:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_8);
			break;
		case 9:
			bmp = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.number_9);
			break;
		case 100://晴
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_0);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_0);
			}
			break;
			
		case 101://多云
		case 104://	阴	Overcast	104.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_9);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_4);
			}
			break;
		case 102://	少云	Few Clouds	102.png
		case 103://	晴间多云	Partly Cloudy	103.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_7);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_7);
			}
			break;
		
		case  200://	有风	Windy	200.png
		case  201://	平静	Calm	201.png
		case  202://	微风	Light Breeze	202.png
		case  203://	和风	Moderate/Gentle Breeze	203.png
		case  204://	清风	Fresh Breeze	204.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_26);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_26);
			}
			break;
		
		case  205://	强风/劲风	Strong Breeze	205.png
		case  206://	疾风	High Wind, Near Gale	206.png
		case  207://	大风	Gale	207.png
		case  208://	烈风	Strong Gale	208.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_32);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_32);
			}
			break;
			
		case  209://	风暴	Storm	209.png
		case  210://	狂爆风	Violent Storm	210.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_35);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_35);
			}
			break;
		case  211://	飓风	Hurricane	211.png
		case  212://	龙卷风	Tornado	212.png
		case  213://	热带风暴	Tropical Storm	213.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_36);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_36);
			}
			break;
		
		case  300://	阵雨	Shower Rain	300.png
		case  301://	强阵雨	Heavy Shower Rain	301.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_10);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_10);
			}
			break;
		
		case  302://	雷阵雨	Thundershower	302.png
		case  303://	强雷阵雨	Heavy Thunderstorm	303.png
		case  304://	雷阵雨伴有冰雹	Hail	304.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_12);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_12);
			}
			break;
		
		case  309://	毛毛雨/细雨	Drizzle Rain	309.png
		case  305://	小雨	Light Rain	305.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_13);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_13);
			}
			break;
		
		case  306://	中雨	Moderate Rain	306.png
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_14);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_14);
			}
			break;
			
		
		case  307://	大雨	Heavy Rain	307.png
		case  308://	极端降雨	Extreme Rain	308.png
		case  310://	暴雨	Storm	310.png
		
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_15);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_15);
		}
		break;
		
		case  311://	大暴雨	Heavy Storm	311.png
		case  312://	特大暴雨	Severe Storm	312.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_19);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_19);
		}
		break;
		
		case  313://	冻雨	Freezing Rain	313.png
		case  400://	小雪	Light Snow	400.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_22);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_22);
		}
		break;
		
		
		case  401://	中雪	Moderate Snow	401.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_23);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_23);
		}
		break;
		
		case  402://	大雪	Heavy Snow	402.png
		case  403://	暴雪	Snowstorm	403.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_25);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_25);
		}
		break;
		case  404://	雨夹雪	Sleet	404.png
		case  405://	雨雪天气	Rain And Snow	405.png
		case  406://	阵雨夹雪	Shower Snow	406.png
		case  407://	阵雪	Snow Flurry	407.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_20);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_20);
		}
		break;
		case  500://	薄雾	Mist	500.png
		case  501://	雾	Foggy	501.png
		case  502://	霾	Haze	502.png
		case  503://	扬沙	Sand	503.png
		case  504://	浮尘	Dust	504.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_30);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_30);
		}
		break;
		
		case  506://	火山灰	Volcanic Ash	506.png
		case  507://	沙尘暴	Duststorm	507.png
		case  508://	强沙尘暴	Sandstorm	508.png
		if(type == 0){
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_today_31);
		}else{
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_31);
		}
		break;
		case  999://
		default:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_recent3days_99);
			break;
		}
		return bmp;
	}
	
}
