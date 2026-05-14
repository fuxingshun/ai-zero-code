package com.fxs.aizerocode.service;

import com.fxs.aizerocode.model.dto.app.AppQueryRequest;
import com.fxs.aizerocode.model.entity.ChatHistory;
import com.fxs.aizerocode.model.entity.User;
import com.fxs.aizerocode.model.vo.app.AppVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.fxs.aizerocode.model.entity.App;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 应用 服务层。
 *
 * @author 24352
 * @since 2026-05-09
 */
public interface AppService extends IService<App> {

    AppVO getAppVO(App app);

    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    List<AppVO> getAppVOList(List<App> appList);

    Flux<String> chatToGenCode(Long appId, String message, User loginUser);

    String deployApp(Long appId, User loginUser);

    void generateAppScreenshotAsync(Long appId, String appUrl);
}
