package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    
    @Autowired
    private EduVideoService videoService;
    
    @Autowired
    private VodClient vodClient;
    
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        
        // 从阿里云vod删除视频
        // 微服务的方式实现 edu调用vod的删除方法
        // 远程调用
        // 通过小节Id获取视频Id，再删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        
        // 有视频才删
        if (!StringUtils.isBlank(videoSourceId)) {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除视频失败，熔断机制触发!");
            }
        }
        // 删除小节
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节 TODO
}

