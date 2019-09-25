package repository;


import model.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface BlogRepository extends JpaRepository<BlogEntity,Integer> {

    // 修改博文操作
    @Modifying
    @Transactional
    @Query("update BlogEntity set title =:pTitle,userByUserId.id = :pUserID" +
            ",content=:pContent,pubDate=:pPubDate where id = :pID ")
    void updateBlogs(@Param("pTitle") String title, @Param("pUserID") int userID, @Param("pContent") String content
        , @Param("pPubDate") Date pubDate,@Param("pID") int id);
}
