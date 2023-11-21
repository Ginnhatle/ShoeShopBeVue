package org.datn.app.filter;

import org.datn.app.constant.UserConstant;
import org.datn.app.core.entity.User;
import org.springframework.stereotype.Service;


@Service
public class CustomSecurityService {

    public boolean checkUserStatus() {
        User user = User.getContext();
        if (user == null) {
            return false;
        }
        boolean isLocked = user.getStatus() == UserConstant.USER_STATUS_INACTIVE ? false : true;
        return  isLocked;
    }
}
