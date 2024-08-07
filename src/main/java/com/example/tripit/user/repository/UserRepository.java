package com.example.tripit.user.repository;

import com.example.tripit.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);

    //nickname를 받아 DB테이블에서 회원을 조회하는 메소드 작성
    UserEntity findByEmail(String email);

    @Query("SELECT u.userId FROM user u WHERE u.email = :email")
    long findUserIdByEmail(@Param("email") String email);

    Optional<UserEntity> findById(Long userId);

    //UserEntity findByNickname(String nickname);


    //MemberEntity findByUsername(String id);
    //JpaRepository의 findById 메서드는 Optional을 반환하도록 설계되어 있음.
    //이는 메소드가 호출된 곳에서 값의 존재 여부를 더욱 명시적으로 다룰 수 있도록 하기 위함이다.

}
