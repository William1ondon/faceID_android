package com.example.scau_faceid.util.sqlite;

import java.util.List;

public interface AsyncResponse {
    void onDataReceivedSuccess(List<SQliteStudent> listData);
    void onDataReceivedFailed();
}
