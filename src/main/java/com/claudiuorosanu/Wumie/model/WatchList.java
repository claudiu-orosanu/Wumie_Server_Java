//package com.claudiuorosanu.Wumie.model;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class WatchList {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // relationships
//    @ManyToOne
//    @JoinColumn(name = "movie_id")
//    private Movie movie;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Admin user;
//
//}
