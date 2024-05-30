package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer>{

}
