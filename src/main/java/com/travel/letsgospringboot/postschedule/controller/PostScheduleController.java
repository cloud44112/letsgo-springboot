package com.travel.letsgospringboot.postschedule.controller;

import com.travel.letsgospringboot.postschedule.service.PostScheduleService;
import com.travel.letsgospringboot.postschedule.vo.MapScheduleTO;
import com.travel.letsgospringboot.postschedule.vo.PostScheduleTO;
import com.travel.letsgospringboot.postschedule.vo.RouteScheduleTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/postschedule")
@RequiredArgsConstructor
public class PostScheduleController {

    private final PostScheduleService postScheduleService;

    @GetMapping("/list")
    @ResponseBody
    public List<PostScheduleTO> getPostScheduleList(@RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "keyword", required = false) String keyword) {
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "latest";
        }
        System.out.println(sortOrder + keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getPostScheduleListLike();
                case "view":
                    return postScheduleService.getPostScheduleListView();
                case "title":
                    return postScheduleService.getPostScheduleListTitle();
                default:
                    return postScheduleService.getPostScheduleListLatest();
            }
        } else {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getPostScheduleListSearchLike(keyword);
                case "view":
                    return postScheduleService.getPostScheduleListSearchView(keyword);
                case "title":
                    return postScheduleService.getPostScheduleListSearchTitle(keyword);
                default:
                    return postScheduleService.getPostScheduleListSearchLatest(keyword);
            }
        }
    }

    @GetMapping("/mylist")
    @ResponseBody
    public List<PostScheduleTO> getUserPostScheduleList(@RequestParam (value = "userId", required = false) String userId, @RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "keyword", required = false) String keyword) {
        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "latest";
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getUserPostScheduleListLike(userId);
                case "view":
                    return postScheduleService.getUserPostScheduleListView(userId);
                case "title":
                    return postScheduleService.getUserPostScheduleListTitle(userId);
                default:
                    return postScheduleService.getUserPostScheduleListLatest(userId);
            }
        } else {
            switch (sortOrder) {
                case "like":
                    return postScheduleService.getUserPostScheduleListSearchLike(userId, keyword);
                case "view":
                    return postScheduleService.getUserPostScheduleListSearchView(userId, keyword);
                case "title":
                    return postScheduleService.getUserPostScheduleListSearchTitle(userId, keyword);
                default:
                    return postScheduleService.getUserPostScheduleListSearchLatest(userId, keyword);
            }
        }
    }

    @GetMapping("/{postId}/budget")
    @ResponseBody
    public String getBudgetDetail(@PathVariable("postId") String postId) {
        return postScheduleService.getBudgetDetail(postId);
    }

    @GetMapping("/{postId}/todo")
    @ResponseBody
    public String getTodoDetail(@PathVariable("postId") String postId) {
        return postScheduleService.getTodoDetail(postId);
    }

    @GetMapping("/{postId}/route")
    @ResponseBody
    public List<RouteScheduleTO> getScheduleRoute(@PathVariable("postId") String postId) {
        return postScheduleService.getScheduleRoute(postId);
    }

    @GetMapping("/{postId}/route/map")
    @ResponseBody
    public List<MapScheduleTO> getMapSchedule(@PathVariable("postId") String postId) {
        return postScheduleService.getMapSchedule(postId);
    }

    @GetMapping("/{postId}/title")
    @ResponseBody
    public String getScheduleTitle(@PathVariable("postId") String postId) {
        return postScheduleService.getScheduleTitle(postId);
    }

    @GetMapping("/{postId}/likeCount")
    @ResponseBody
    public int getLikeCount(@PathVariable("postId") String postId) {
        return postScheduleService.getLikeCount(postId);
    }

    @GetMapping("/{postId}/viewCount")
    @ResponseBody
    public int getViewCount(@PathVariable("postId") String postId) {
        return postScheduleService.getViewCount(postId);
    }

    @GetMapping("/{postId}/userId")
    @ResponseBody
    public String getUserId(@PathVariable("postId") String postId) {
        return postScheduleService.getUserId(postId);
    }

    @PutMapping("/{postId}/plusLike")
    @ResponseBody
    public void plusLike(@PathVariable("postId") String postId) {
        postScheduleService.plusLike(postId);
    }

    @PutMapping("/{postId}/plusView")
    @ResponseBody
    public void plusView(@PathVariable("postId") String postId) {
        postScheduleService.plusView(postId);
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePostSchedule(@PathVariable("postId") String postId){
        postScheduleService.deletePostSchedule(postId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}/addToMySchedule")
    public ResponseEntity<String> addToMySchedule(@PathVariable("postId") String postId, @RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        postScheduleService.addToMySchedule(postId, userId);
        return ResponseEntity.ok("성공ㅇㅇ");
    }



}
