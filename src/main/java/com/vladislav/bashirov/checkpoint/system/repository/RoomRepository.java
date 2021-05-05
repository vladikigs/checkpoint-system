package com.vladislav.bashirov.checkpoint.system.repository;

import com.vladislav.bashirov.checkpoint.system.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
