package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleDetailTO;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/postschedule")
@RequiredArgsConstructor
public class PostScheduleController {

    private final PostScheduleService postScheduleService;

    @GetMapping("/list")
    public String postScheduleList(Model model) {
        List<PostScheduleTO> list = postScheduleService.getPostScheduleListLatest();
        model.addAttribute("list", list);
        return "postScheduleList";
    }

    @GetMapping("/detail/{postId}")
    public String postScheduleDetail(@PathVariable("postId") String postId, Model model) {
        postScheduleService.plusView(postId);
        PostScheduleDetailTO detail = postScheduleService.getPostScheduleDetail(postId);
        model.addAttribute("detail", detail);
        return  "postScheduleDetail";
    }


//    @GetMapping("/detail/{postId}")
//    @ResponseBody
//    public PostScheduleDetailTO postScheduleDetail(@PathVariable("postId") String postId, PostScheduleDetailTO detail) {
//        postScheduleService.plusView(postId);
//        return postScheduleService.getPostScheduleDetail(postId);
//    }


}
