package com.example.scau_faceid.util.sqlite;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.scau_faceid.CheckAttendance;

import java.util.ArrayList;
import java.util.List;

public class DBEngine {
    public StudentDao studentDao;

    public DBEngine(Context context) {
        SQliteStudentDatabase studentDatabase = SQliteStudentDatabase.getInstance(context);
        studentDao = studentDatabase.getStudentDao();
    }

    public void insertStudentsNewThread(SQliteStudent... students) {
        new InsertAsyncTask(studentDao).execute(students);
    }

    public void updateStudentsNewThread(SQliteStudent... students) {
        new UpdateAsyncTask(studentDao).execute(students);
    }

    public void deleteStudentsNewThread(SQliteStudent... students) {
        new DeleteAsyncTask(studentDao).execute(students);
    }

    public void deleteAllStudents() {
        new DeleteAllStudentsAsyncTask(studentDao).execute();
    }

    public void queryAllStudents() {
        new QueryAsyncTask(studentDao).execute();
    }

    //<params,progress,result>
    //Params：开始异步任务执行时传入的参数类型；
    //
    //Progress：异步任务执行过程中，返回下载进度值的类型；
    //
    //Result：异步任务执行完成后，返回的结果类型；
    static class InsertAsyncTask extends AsyncTask<SQliteStudent, Void, Void> {
        private StudentDao studentDao;

        public InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(SQliteStudent... sQliteStudents) {
            studentDao.insertStudents(sQliteStudents);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<SQliteStudent, Void, Void> {

        private StudentDao studentDao;

        public UpdateAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(SQliteStudent... students) {
            studentDao.updateStudents(students);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<SQliteStudent, Void, Void> {
        private StudentDao studentDao;

        public DeleteAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(SQliteStudent... students) {
            studentDao.deleteStudents(students);
            return null;
        }
    }

    static class DeleteAllStudentsAsyncTask extends AsyncTask<SQliteStudent, Void, Void> {
        private StudentDao studentDao;

        public DeleteAllStudentsAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(SQliteStudent... students) {
            studentDao.deleteAllStudents();
            return null;
        }
    }

    public static class QueryAsyncTask extends AsyncTask<SQliteStudent, Void, List<SQliteStudent>> {
        private StudentDao studentDao;

        public AsyncResponse asyncResponse;

        public void setOnAsyncResponse(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        public QueryAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected List<SQliteStudent> doInBackground(SQliteStudent... students) {
            return studentDao.getAllStudents();
        }

        @Override
        protected void onPostExecute(List<SQliteStudent> list) {
            super.onPostExecute(list);
            if (list != null) {
                List<SQliteStudent> listData = new ArrayList<SQliteStudent>();
                listData = list;
                asyncResponse.onDataReceivedSuccess(listData);
            } else {
                asyncResponse.onDataReceivedFailed();
            }
        }
    }
}
