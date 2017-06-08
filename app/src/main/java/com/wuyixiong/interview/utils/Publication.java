package com.wuyixiong.interview.utils;

import android.util.Log;

import com.wuyixiong.interview.entity.Comment;
import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.CommentEvent;
import com.wuyixiong.interview.event.PublishEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by WUYIXIONG on 2017-5-23.
 */

public class Publication {
    private static Publication publish = null;

    private Publication() {

    }

    public static Publication getInstance() {
        if (publish == null) {
            publish = new Publication();
        }
        return publish;
    }


    /**
     * 发表信息的上传图片
     *
     * @param filePath
     */
    public void publishpic(ArrayList<String> filePath) {
        final int size = filePath.size();
        if (size == 0) {
            EventBus.getDefault().post(new PublishEvent(0, null));
        } else {
            String[] paths = (String[]) filePath.toArray(new String[size]);
            BmobFile.uploadBatch(paths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    if (list1.size() == size) {
                        EventBus.getDefault().post(new PublishEvent(0, (ArrayList<String>) list1));
                    }else {
                        Log.i("tag", "----------------"+"图片上传失败");
                        EventBus.getDefault().post(new PublishEvent("上传失败",-1));
                    }
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {

                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }

    }

    /**
     * 发表信息
     *
     * @param type
     * @param content
     * @param company
     * @param filePath
     */
    public void publishMsg(String type, String content, String company, ArrayList<String> filePath) {
        Message message = new Message();
        message.setCompany(company);
        message.setContents(content);
        message.setType(type);
        User user = BmobUser.getCurrentUser(User.class);
        message.setAuthor(user);
        if (filePath != null){
            switch (filePath.size()) {
                case 0:
                    message.setPicType(0);
                    break;
                case 1:
                    message.setPicType(1);
                    message.setPic1(filePath.get(0));
                    break;
                case 2:
                    message.setPicType(2);
                    message.setPic1(filePath.get(0));
                    message.setPic2(filePath.get(1));
                    break;
                case 3:
                    message.setPicType(3);
                    message.setPic1(filePath.get(0));
                    message.setPic2(filePath.get(1));
                    message.setPic3(filePath.get(2));
                    break;
                case 4:
                    message.setPicType(4);
                    message.setPic1(filePath.get(0));
                    message.setPic2(filePath.get(1));
                    message.setPic3(filePath.get(2));
                    message.setPic4(filePath.get(3));
                    break;
            }
        }else {
            message.setPicType(0);
        }

        message.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new PublishEvent(1,null));
                }else {
                    Log.i("tag", "------------"+e.getMessage());
                    EventBus.getDefault().post(new PublishEvent("上传失败",-1));
                }
            }
        });
    }


    /**
     * 发表评论
     * @param msgId
     * @param content
     */
    public void publishComment(final String msgId, String content){
        User user = BmobUser.getCurrentUser(User.class);
        Message message = new Message();
        message.setObjectId(msgId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setZan(0);
        comment.setAuthor(user);
        comment.setMessage(message);

        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Query.getInstance().queryComment(msgId,1);
                }else {
                    EventBus.getDefault().post(new CommentEvent(false));
                }
            }
        });
    }

    /**
     * 点赞时修改后台数据
     * @param num 修改之后的个数
     */
    public void zan(String id,int num){
        Comment comment = new Comment();
        comment.setZan(num);
        comment.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }
}
