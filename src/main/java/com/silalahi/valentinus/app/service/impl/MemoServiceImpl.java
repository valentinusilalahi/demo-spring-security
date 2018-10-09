package com.silalahi.valentinus.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silalahi.valentinus.app.entity.Memo;
import com.silalahi.valentinus.app.repository.MemoRepository;
import com.silalahi.valentinus.app.service.MemoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemoServiceImpl implements MemoService {

	private final MemoRepository memoRepository;

	public MemoServiceImpl(MemoRepository repository) {
		this.memoRepository = repository;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Memo> findById(Long id) {
		return memoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Memo> findAll(Pageable page) {
		return memoRepository.findAll(page);
	}

	@Transactional(timeout = 10)
	@Override
	public void store(Memo memo) {
		memoRepository.save(memo);
	}

	@Transactional(timeout = 10)
	@Override
	public void removeById(Long id) {
		memoRepository.deleteById(id);
	}

	@Transactional(timeout = 10)
	@Override
	public void updateById(Long id, Memo memo) {
		memoRepository.findById(id).ifPresent(targetMemo -> targetMemo.merge(memo));
	}

}
