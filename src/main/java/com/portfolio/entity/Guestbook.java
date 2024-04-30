package com.portfolio.entity;

import com.portfolio.dto.GuestbookFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "guestbook")
public class Guestbook extends BaseEntity{
    @Id
    @Column(name = "guestbook_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateGuestbook(Guestbook guestbook){
        this.content = guestbook.getContent();
        this.title = guestbook.getTitle();
    }
}
