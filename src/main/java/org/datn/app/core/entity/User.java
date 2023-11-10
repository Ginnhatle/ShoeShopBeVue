package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.datn.app.constant.UserConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    private String phoneNumber;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    private String avatar;

    private String role;

    private String code;

    @Column(name = "status")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @ManyToOne(targetEntity = Rank.class)
    @JoinColumn(name = "rank_id", referencedColumnName = "rank_id")
    private Rank rank;

    @OneToMany(mappedBy = "user", targetEntity = Order.class)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "user", targetEntity = Cart.class)
    @JsonIgnore
    private List<Cart> carts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserConstant.USER_STATUS_ACTIVE ? true : false;
    }

    @PrePersist
    public void prePersist() {
        this.created = new Date();
        this.modified = new Date();
        this.status = UserConstant.USER_STATUS_ACTIVE;
        this.role = UserConstant.USER_ROLE_USER;
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    @SuppressWarnings("unused")
    public static User getContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }
}
