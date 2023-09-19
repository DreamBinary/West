package com.example.westfour01.controller;

import com.example.westfour01.entity.History;
import com.example.westfour01.response.CollectionResponseCode;
import com.example.westfour01.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author CXQ
 * @date 2022/2/1
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private final HistoryService service = new HistoryService();

    @PostMapping("/insert")
    public void insert(@RequestBody History history) {
        service.insert(history);
    }

    @PostMapping("/findAll")
    public List<History> findAll(String username) {
        List<History> historyList = service.findAll(username);
        Collections.reverse(historyList);
        return historyList;
    }

    @DeleteMapping("/clear")
    public CollectionResponseCode deleteAll(@RequestParam String username) {
        return service.clear(username) > 0 ? CollectionResponseCode.DELETE_ALL_SUCCESS
                : CollectionResponseCode.DELETE_ALL_FAILED;
    }

}
