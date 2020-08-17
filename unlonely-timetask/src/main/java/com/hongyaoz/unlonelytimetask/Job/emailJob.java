package com.hongyaoz.unlonelytimetask.Job;

import com.hongyaoz.unlonelycommon.Until.DateTimeUtil;
import com.hongyaoz.unlonelytimetask.dao.TaskMapper;
import com.hongyaoz.unlonelytimetask.pojo.Task;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class emailJob implements Job {

    @Autowired
    TaskMapper taskMapper;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
   //   taskMapper.selectTask(new Date())
        Date date=new Date();
        int minutes = date.getMinutes();
        date.setMinutes(minutes+5);
        Date datenow = DateTimeUtil.strToDate(DateTimeUtil.dateToStr(new Date()));
        Date dateafter = DateTimeUtil.strToDate(DateTimeUtil.dateToStr(date));
         List<Task> list =  taskMapper.selectTask(datenow,dateafter);
         for (Task task:list){
             //发邮件
             if (task.getStatus()==0){

             }
         }
    }
}
