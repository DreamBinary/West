package com.example.westfour01.controller;

import com.example.westfour01.entity.Collection;
import com.example.westfour01.response.CollectionResponseCode;
import com.example.westfour01.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CXQ
 * @date 2022/2/1
 */

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    CollectionService service = new CollectionService();


    @PostMapping("/insert")
    public CollectionResponseCode insert(@RequestBody Collection collection) {
        service.insert(collection);
        return CollectionResponseCode.INSERT_SUCCESS;
    }

    @PostMapping("/exist")
    public boolean exist(@RequestBody Collection collection) {
        return service.exist(collection);
    }

    @PostMapping("/findAll")
    public List<String> findAll(@RequestParam String username) {
        return service.findAll(username);
    }

    @DeleteMapping("/delete")
    public CollectionResponseCode deleteOne(@RequestBody Collection collection) {
        service.delete(collection);
        return CollectionResponseCode.DELETE_ONE_SUCCESS;
    }

    @DeleteMapping("/clear")
    public CollectionResponseCode deleteAll(@RequestParam String username) {
        return service.clear(username) > 0 ? CollectionResponseCode.DELETE_ALL_SUCCESS
                : CollectionResponseCode.DELETE_ALL_FAILED;
    }
}
