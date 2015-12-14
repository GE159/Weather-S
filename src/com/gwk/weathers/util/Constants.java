package com.gwk.weathers.util;



import com.gwk.weathers.app.R;
import com.gwk.weathers.db.kLog;

import android.content.Context;

public class Constants {
	public static int picSize;
	public Constants(Context c){
		picSize = (int) c.getResources().getDimension(R.dimen.picSize);
		kLog.i("picSize", picSize+"");
	}
  
}
