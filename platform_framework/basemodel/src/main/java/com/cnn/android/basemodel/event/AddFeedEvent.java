package com.cnn.android.basemodel.event;

import android.content.Intent;

/**
 * Created by nmj on 2017/5/2.
 */

public class AddFeedEvent {
    private Intent data;
    private boolean isSelectedImgae=false;
    public void selectImgae(Intent data){

        this.data=data;
    }

    public void setSelectedImgae(boolean selectedImgae) {
        isSelectedImgae = selectedImgae;
    }

    public boolean isSelectedImgae() {
        return isSelectedImgae;
    }

    public Intent getData(){
        return data;
    }
}
