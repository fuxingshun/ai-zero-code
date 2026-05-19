package com.fxs.aizerocode.innerservice;

import com.fxs.aizerocode.exception.BusinessException;
import com.fxs.aizerocode.exception.ErrorCode;
import com.fxs.aizerocode.model.entity.User;
import com.fxs.aizerocode.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.fxs.aizerocode.constant.UserConstant.USER_LOGIN_STATE;

public interface InnerUserService {
    // 静态方法，避免跨服务调用
    static User getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    List<User> listByIds(Collection<? extends Serializable> ids);

    User getById(Serializable id);

    UserVO getUserVO(User user);
}
