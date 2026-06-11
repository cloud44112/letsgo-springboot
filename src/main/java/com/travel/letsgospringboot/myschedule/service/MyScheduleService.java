package com.travel.letsgospringboot.myschedule.service;

import com.travel.letsgospringboot.exception.AccessDeniedException;
import com.travel.letsgospringboot.myschedule.repository.MyScheduleRepository;
import com.travel.letsgospringboot.myschedule.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyScheduleService {
    private final MyScheduleRepository myScheduleRepository;

    public List<MyScheduleVO> getMyScheduleListAllByDate(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListAllByDate(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListAllByTitle(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListAllByTitle(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSharedByDate(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSharedByDate(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSharedByTitle(String userId) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSharedByTitle(userId));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchByDate(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchByDate(userId, keyword));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchByTitle(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchByTitle(userId, keyword));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchSharedByDate(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchSharedByDate(userId, keyword));
        return result;
    }

    public List<MyScheduleVO> getMyScheduleListSearchSharedByTitle(String userId, String keyword) {
        List<MyScheduleVO> result = processMyScheduleList(myScheduleRepository.getMyScheduleListSearchSharedByTitle(userId, keyword));
        return result;
    }

    private List<MyScheduleVO> processMyScheduleList(List<MyScheduleVO> data) {
        Map<String, MyScheduleVO> uniqueMap = new LinkedHashMap<>();
        for (MyScheduleVO vo : data) {
            if (!uniqueMap.containsKey(vo.getMyScheduleId())) {
                vo.setStartAt(formatStartAt(vo.getStartAt()));
                vo.setAddr1(formatAddr1(vo.getAddr1()));
                uniqueMap.put(vo.getMyScheduleId(), vo);
            } else {
                MyScheduleVO existingVO = uniqueMap.get(vo.getMyScheduleId());
                if (existingVO.getFirstImage() == null) {
                    existingVO.setFirstImage(vo.getFirstImage());
                }
                String combinedPlaces = existingVO.getPlaceTitle() + " / " + vo.getPlaceTitle();
                existingVO.setPlaceTitle(combinedPlaces);
            }
        }
        List<MyScheduleVO> result = new ArrayList<>(uniqueMap.values());
        return result;
    }

    private String formatStartAt(String startAt) {
        if (startAt == null) {
            return null;
        }
        return startAt.length() >= 10 ? startAt.substring(0, 10) : startAt;
    }

    private String formatAddr1(String addr1) {
        if (addr1 == null) {
            return null;
        }
        String[] tokens = addr1.trim().split("\\s+");
        if (tokens.length <= 2) {
            return addr1.trim();
        }
        return tokens[0] + " " + tokens[1];
    }

    public boolean setMyScheduleTitle(String title, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.setMyScheduleTitle(title, scheduleId, userId);
        log.info("일정 제목 수정 - scheduleId={}, userId={}", scheduleId, userId);
        return result;
    }

    public boolean setTodoDetail(String scheduleId, String todoDetail, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.setTodoDetail(scheduleId, todoDetail);
        log.info("일정 할일 수정 - scheduleId={}, userId={}", scheduleId, userId);
        return result;
    }

    public ScheduleDetailVO getScheduleDetail(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getScheduleDetail(scheduleId);
    }

    public String getTodoDetail(String scheduleId) {
        return myScheduleRepository.getTodoDetail(scheduleId);
    }

    public boolean setBudgetDetail(String scheduleId, String budgetDetail, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.setBudgetDetail(scheduleId, budgetDetail);
        log.info("일정 예산 수정 - scheduleId={}, userId={}", scheduleId, userId);
        return result;
    }

    public String getBudgetDetail(String scheduleId) {
        return myScheduleRepository.getBudgetDetail(scheduleId);
    }

    public String getScheduleTitle(String scheduleId) {
        return myScheduleRepository.getScheduleTitle(scheduleId);
    }

    public String getStartAt(String scheduleId) {
        return myScheduleRepository.getStartAt(scheduleId);
    }

    public boolean setStartAt(String scheduleId, String startAt, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.setStartAt(scheduleId, startAt, userId);
        log.info("일정 시작일 수정 - scheduleId={}, userId={}", scheduleId, userId);
        return result;
    }

    public boolean addMySchedule(String myScheduleId, String title, String userId) {
        boolean result = myScheduleRepository.addMySchedule(myScheduleId, title, userId);
        log.info("내 일정 추가 - userId={}, scheduleId={}, title={}", userId, myScheduleId, title);
        return result;
    }

    private boolean isScheduleOwnedByUser(String scheduleId, String userId) {
        boolean owned = myScheduleRepository.isScheduleOwnedByUser(scheduleId, userId) > 0;
        if (!owned) {
            log.warn("일정 접근 권한 위반 차단 - userId={}, scheduleId={}", userId, scheduleId);
        }
        return owned;
    }

    public List<ScheduleSummaryVO> listMyScheduleIdAndTitle(String userId) {
        return myScheduleRepository.listMyScheduleIdAndTitle(userId);
    }

    public List<RouteScheduleVO> getScheduleRoute(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getScheduleRoute(scheduleId);
    }

    public List<MapScheduleVO> getMapSchedule(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getMapSchedule(scheduleId);
    }
    @Transactional
    public boolean addVisitItem(int visitOrder, String placeId, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.addVisitItem(visitOrder, placeId, scheduleId);
        log.info("방문 항목 추가 - scheduleId={}, placeId={}, userId={}", scheduleId, placeId, userId);
        return result;
    }
    @Transactional
    public boolean deleteVisitItemById(String visitItemId, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.deleteVisitItemById(visitItemId);
        log.info("방문 항목 삭제 - scheduleId={}, visitItemId={}, userId={}", scheduleId, visitItemId, userId);
        return result;
    }
    @Transactional
    public boolean addCompanion(String myScheduleId, String sharedUserId) {
        boolean result = myScheduleRepository.addCompanion(myScheduleId, sharedUserId);
        log.info("동행자 추가 - scheduleId={}, sharedUserId={}", myScheduleId, sharedUserId);
        return result;
    }
    @Transactional
    public boolean setCompanionPermission(String scheduleId, String sharedUserId, String permission, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean result = myScheduleRepository.setCompanionPermission(scheduleId, sharedUserId, permission);
        log.info("동행자 권한 수정 - scheduleId={}, sharedUserId={}, permission={}", scheduleId, sharedUserId, permission);
        return result;
    }
    @Transactional
    public List<ColleagueVO> getCompanionList(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        return myScheduleRepository.getCompanionList(scheduleId);
    }

    @Transactional
    public boolean deleteMySchedule(String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        myScheduleRepository.deleteVisitItemsByScheduleId(scheduleId);
        boolean result = myScheduleRepository.deleteScheduleById(scheduleId) > 0;
        log.info("일정 삭제 - scheduleId={}, userId={}", scheduleId, userId);
        return result;
    }

    @Transactional
    public boolean deleteMyScheduleList(String userId, String[] scheduleIds) {
        boolean allDeleted = scheduleIds.length > 0;
        for (String id : scheduleIds) {
            myScheduleRepository.deleteVisitItemsByScheduleId(id);
            if (myScheduleRepository.deleteScheduleByIdAndUserId(id, userId) <= 0) {
                allDeleted = false;
            }
        }
        log.info("일정 목록 삭제 - userId={}, 건수={}", userId, scheduleIds.length);
        return allDeleted;
    }

    @Transactional
    public boolean setMySchedule(String[] visitItemId, int[] visitOrder, String[] distanceToNext, String scheduleId,
                                 String scheduleTitle, String startAt, String budgetDetail, String todoDetail,
                                 String userId, int isShared) {
        boolean updated = myScheduleRepository.updateSchedule(scheduleId, scheduleTitle, startAt, budgetDetail,
                todoDetail, isShared, userId) > 0;
        for (int i = 0; i < visitItemId.length; i++) {
            myScheduleRepository.updateVisitItem(visitItemId[i], visitOrder[i], distanceToNext[i]);
        }
        log.info("일정 일괄 수정 - scheduleId={}, userId={}, 방문항목수={}", scheduleId, userId, visitItemId.length);
        return updated;
    }

    @Transactional
    public boolean updateVisitOrders(List<VisitOrderVO> orders, String scheduleId, String userId) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        boolean allUpdated = !orders.isEmpty();
        for (VisitOrderVO order : orders) {
            if (myScheduleRepository.updateVisitItem(
                    order.getVisitItemId(), order.getVisitOrder(), order.getDistance()) <= 0) {
                allUpdated = false;
            }
        }
        log.info("방문 순서 수정 - scheduleId={}, userId={}, 건수={}", scheduleId, userId, orders.size());
        return allUpdated;
    }

    public String allocateNextMyScheduleId() {
        String candidate;
        do {
            candidate = myScheduleRepository.getNextMyScheduleIdCandidate();
        } while (myScheduleRepository.checkMyScheduleIdExists(candidate) > 0);
        return candidate;
    }

    @Transactional
    public String shareToPost(String scheduleId, String userId, int isAnonymous) {
        if(!isScheduleOwnedByUser(scheduleId, userId))
            throw new AccessDeniedException("권한이 없습니다");
        myScheduleRepository.shareToPostInsert(scheduleId, userId, isAnonymous);
        myScheduleRepository.shareVisitItemsToPost(scheduleId);
        String postId = myScheduleRepository.getLastPostId();
        log.info("일정 게시판 공유 - scheduleId={}, userId={}, postId={}", scheduleId, userId, postId);
        return postId;
    }
}
