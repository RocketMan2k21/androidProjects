package com.example.mymovie.db.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProfileDAO {

    @Insert
    public long addProfile(Profile profile);

    @Update
    public void updateProfile(Profile profile);

    @Delete
    public void deleteProfile(Profile profile);

    @Query("select * from profiles")
    public List<Profile> getAllProfiles();

    @Query("select * from profiles where profile_id == :profileId")
    public Profile getProfile(final long profileId);
}
