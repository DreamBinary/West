package com.example.westfour01.dao;

import com.example.westfour01.entity.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CXQ
 * @date 2022/2/1
 */

@Repository
public interface CollectionRepository extends CrudRepository<Collection, Long> {

    @Query(value = "select novel_title from collection where username = ?1", nativeQuery = true)
    List<String> findAllTitleByUsername(String username);
    //查看

    Integer deleteAllByUsername(String username);
    //清空

    boolean existsCollectionByUsernameAndNovelTitle(String username, String novelTitle);

    void deleteCollectionByUsernameAndNovelTitle(String username, String novelTitle);

}
