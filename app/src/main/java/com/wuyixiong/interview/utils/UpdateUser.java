package com.wuyixiong.interview.utils;

import android.util.Log;

import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.InfoEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by WUYIXIONG on 2017-5-20.
 */

public class UpdateUser {

    /**
     * 更新昵称
     * @param nickName
     */
    public void updateNick(final String nickName){
        Log.i("tag", "-----------------"+nickName);
        User user = new User();
        user.setNickName(nickName);
        User bmobUser = BmobUser.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new InfoEvent(nickName,1,"nickName"));
                }else {
                    EventBus.getDefault().post(new InfoEvent(-1));
                }
            }
        });
    }

    /**
     * 更新账号
     * @param account
     */
    public void updateAccount(final String account){
        Log.i("tag", "-----------------"+account);
        User user = new User();
        user.setUsername(account);
        user.setChanged(true);
        User bmobUser = BmobUser.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new InfoEvent(account,1,"account"));
                }else {
                    EventBus.getDefault().post(new InfoEvent(-1));
                }
            }
        });
    }

    /**
     * 修改性别
     * @param sex
     */
    public void updateSex(final String sex){
        User user = new User();
        user.setSex(sex);
        User bmobUser = BmobUser.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new InfoEvent(sex,1,"sex"));
                }else {
                    EventBus.getDefault().post(new InfoEvent(-1));
                }
            }
        });
    }

    /**
     * 修改职业
     * @param job
     */
    public void updateJob(final String job){
        User user = new User();
        user.setJob(job);
        User bmobUser = BmobUser.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new InfoEvent(job,1,"job"));
                }else {
                    EventBus.getDefault().post(new InfoEvent(-1));
                }
            }
        });
    }

    /**
     * 修改电话
     * @param phone
     */
    public void updatePhone(final String phone){
        User user = new User();
        user.setMobilePhoneNumber(phone);
        User bmobUser = BmobUser.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new InfoEvent(phone,1,"phone"));
                }else {
                    EventBus.getDefault().post(new InfoEvent(-1));
                }
            }
        });
    }

    /**
     * 修改邮箱
     * @param mail
     */
    public void updateMail(final String mail){
        User user = new User();
        user.setEmail(mail);
        User bmobUser = BmobUser.getCurrentUser(User.class);
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new InfoEvent(mail,1,"mail"));
                }else {
                    EventBus.getDefault().post(new InfoEvent(-1));
                }
            }
        });
    }

    /**
     * 修改头像
     * @param file
     */
    public void updateIcon(File file){


        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    String fileUrl =  bmobFile.getFileUrl();
                    User user = new User();
                    user.setHeadUrl(fileUrl);
                    User bmobUser = BmobUser.getCurrentUser(User.class);
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                EventBus.getDefault().post(new InfoEvent(0));
                            }else {
                                EventBus.getDefault().post(new InfoEvent(-1));
                            }
                        }
                    });
                }else {

                }
            }
        });


    }
}
