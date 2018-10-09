package com.silalahi.valentinus.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silalahi.valentinus.app.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

}
