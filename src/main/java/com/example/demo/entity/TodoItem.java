package com.example.demo.entity;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todoitems")
@Getter
@Setter
public class TodoItem {
	@Id//プライマリキーとしてidを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自動的に番号を生成
    private Long id;//主キー
	private int category;//仕事か、私生活か、その他か
    private String title;//タイトル
    private String description;//詳細
    private int priority;//優先度
    private Date createDate;//作成日
    private Date dueDate;//期日
    private Boolean done;//達成状況
    
    
    //作成日を自動生成する
    @PrePersist
    public void prePersist() {
        this.createDate = new Date(System.currentTimeMillis());
    }
}