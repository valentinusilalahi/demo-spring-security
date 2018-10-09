package com.silalahi.valentinus.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silalahi.valentinus.app.entity.Memo;
import com.silalahi.valentinus.app.service.MemoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "memo")
@Slf4j
public class MemoController {
	private final MemoService memoService;

	public MemoController(MemoService memoService) {
		this.memoService = memoService;
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<Memo> id(@PathVariable(value = "id") Long id) {
		Optional<Memo> memo = memoService.findById(id);
		return memo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Memo>> list(Pageable page) {
		Page<Memo> memos = memoService.findAll(page);
		return ResponseEntity.ok(memos.getContent());
	}

	@PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String strong(@RequestBody Memo memo) {
		memoService.store(memo);
		return "success";
	}

	@PutMapping(path = "{id}", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String update(@PathVariable(value = "id") Long id, @RequestBody Memo memo) {
		memoService.updateById(id, memo);
		return "success";
	}

	@DeleteMapping(path = "{id}", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String remove(@PathVariable(value = "id") Long id) {
		memoService.removeById(id);
		return "success";
	}
}
