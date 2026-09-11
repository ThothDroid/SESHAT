package com.blueapps.seshat;

public interface SeshatListener {

    void onExportStarted();

    void onExportProgress(int progress, int total);

    void onExportCompleted();

}
