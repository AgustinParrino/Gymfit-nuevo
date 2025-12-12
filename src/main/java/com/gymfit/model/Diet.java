    package com.gymfit.model;

    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public class Diet {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        @Column(length=1000)
        private String description;
        @Column(length=4000)
        private String content;
    }
