package com.example.mediaplayer.ultis;

import com.example.mediaplayer.PlaybackManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlaybackSubThread implements Runnable {

    private AtomicBoolean isRunning=new AtomicBoolean(false);
    private AtomicBoolean inStopped=new AtomicBoolean(false);
    private final int m_vInterval;
    private final Thread m_vWorker;
    private final PlaybackManager m_vPlaybackManager;
    public PlaybackSubThread(int interval, PlaybackManager playbackManager){
        this.m_vPlaybackManager =playbackManager;
        this.m_vWorker= new Thread(this);
        this.m_vInterval=interval;

    }



    @Override
    public void run(){
        this.isRunning.set(true);
        this.inStopped.set(false);

        while (isRunning()){
            //dO SOMTHING UI UPDATE actually Motifivation updates
            try{
                Thread.sleep(this.m_vInterval);
            }
            catch (InterruptedException ignore){
                interrupt();
            }
        }
    }
    private void interrupt(){
        this.isRunning.set(false);
        this.m_vWorker.interrupt();
    }
    public Thread getWorker(){ return this.m_vWorker;}

    public boolean isRunning(){return this.isRunning.get();}
    public boolean isStopped(){return this.inStopped.get();}

    public void onStart(){this.m_vWorker.start();}
    public void onStop(){this.interrupt();}



}
