package com.example.formation4.superquizz.ui.ThreadTask;


import android.os.AsyncTask;
import android.os.SystemClock;

public class DelayTask extends AsyncTask<Void, Integer, String> {

    private onDelayTaskListener listener;

    int count = 0;


    public DelayTask(onDelayTaskListener listener){
        super();
        this.listener = listener;
    }

    @Override
    protected void onPreExecute(){
        //pb.setVisibility(ProgressBar.VISIBLE);
        listener.willStart();
    }

    @Override
    protected String doInBackground(Void... voids) {
        //super.doInBackground(voids);
        while(count < 5){
            SystemClock.sleep(1000);
            count++;
            publishProgress(count * 20);
        }
        //listener.onFinish();
        return "Complete";
    }

    @Override
    protected void onProgressUpdate(Integer... values){
       // pb.setProgress(values[0]);
        listener.onProgress(values[0]);
    }



    public interface onDelayTaskListener{
        void willStart();
        void onProgress(int progress);
        void onFinish();

    }


}
