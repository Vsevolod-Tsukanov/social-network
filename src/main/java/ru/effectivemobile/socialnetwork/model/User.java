package ru.effectivemobile.socialnetwork.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effectivemobile.socialnetwork.security.model.ERole;

import java.util.List;

@Table(schema = "social_network_schema", name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(schema = "social_network_schema", name = "user_friends", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "friend_id")})
    private List<User> friends;

    @ManyToMany
    @JoinTable(schema = "social_network_schema", name = "user_followers", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "follower_id")})
    private List<User> followers;

    @ManyToMany
    @JoinTable(schema = "social_network_schema", name = "user_friendsrequests",
            joinColumns = {
                    @JoinColumn(name = "user_from_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "user_to_id")})
    private List<User> friendsRequests;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ERole role;

}
