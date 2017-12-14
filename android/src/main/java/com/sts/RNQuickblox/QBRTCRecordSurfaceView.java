package com.sts.RNQuickblox;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceHolder;

import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView;

/**
 * Created by Dat Tran on 3/27/17.
 */

public class QBRTCRecordSurfaceView extends QBRTCSurfaceView {

    SurfaceHolder mHolder;
    MediaRecorder mediaRecorder;

    public QBRTCRecordSurfaceView(Context ctx, MediaRecorder recorder) {
        super(ctx);

        mediaRecorder = recorder;
        mHolder = getHolder();
        mHolder.addCallback(this);
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

        String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/temp.3gpp";

        mediaRecorder.setOutputFile(mFileName);
        mediaRecorder.setPreviewDisplay(mHolder.getSurface());
        try{
            mediaRecorder.prepare();
        } catch (Exception e) {
            String message = e.getMessage();
            mediaRecorder.release();
            mediaRecorder = null;
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);

        if(mediaRecorder!=null)
        {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
