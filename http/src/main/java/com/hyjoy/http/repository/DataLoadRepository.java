package com.hyjoy.http.repository;

import android.arch.lifecycle.LiveData;

import com.hyjoy.http.Status;

import java.util.List;

/**
 * 数据提供者
 * ViewModel 中定义
 * <pre>
 * val userRepository: DataLoadRepository<OwnUser>
 *          by lazy {
 *               SingleDataLoadRepository<OwnUser> {
 *                   LaidianApi.getService(User::class.java).login(phone.get(), password.get())
 *               }
 *           }
 * </pre>
 * ViewModel 加载数据
 * <pre>
 * fun login() {
 *        userRepository.loadData()
 * }
 * </pre>
 * activity中 监听网络状态结果
 * <pre>
 * loginViewModel.getUserRepository().getStatus().observe(this, t -> {
 *     if (t != null) {
 *          if (t.isSuccess()) {
 *               SPUtils.getInstance().put("token", true);
 *               LoginActivity.this.finish();
 *               return;
 *          }
 *          if (t.isError()) {
 *               loginViewModel.getToastStringMessage().postValue(t.getMessage());
 *          }
 *     }
 * });
 * </pre>
 * 多个网络请求统一处理loding
 * <pre>
 * NetLoadingLiveData netLoadingLiveData = new NetLoadingLiveData();
 * netLoadingLiveData.observe(this, t -> {
 *     if (t != null) {
 *          if (t.isLoading()) {
 *              加载中
 *          }
 *          if (t.isSuccess()) {
 *              全部成功
 *          }
 *          if (t.isError()) {
 *              至少一个全部成功
 *          }
 *     }
 * });
 * </pre>
 *
 * Created by hyjoy on 2018/4/19.
 */
public interface DataLoadRepository<T> {
    /**
     * 获取数据 LiveData
     *
     * @return 数据 LiveData
     */
    LiveData<T> getData();

    /**
     * 获取数据 LiveData
     *
     * @return 数据 LiveData
     */
    LiveData<Boolean> noData();

    /**
     * 获取网络状态
     *
     * @return LiveData
     */
    LiveData<Status> getStatus();

    /**
     * 获取数据
     *
     * @param reLoad 是否重新加载
     */
    void loadData(boolean reLoad);

    /**
     * 获取数据 不重新加载
     */
    default void loadData() {
        loadData(false);
    }

    /**
     * 分页数据提供者
     *
     * @param <T>
     * @author x
     */
    interface PagesLoadRepository<T> extends DataLoadRepository<List<T>> {
        /**
         * 是否有下一页
         *
         * @return LiveData
         */
        LiveData<Boolean> getHasNext();

        /**
         * 获取下一页数据
         */
        void loadNextPage(boolean reLoad);

        default void loadNextPage() {
            loadNextPage(false);
        }

        /**
         * 获取Next网络状态
         *
         * @return LiveData
         */
        LiveData<Status> getPageStatus();
    }
}
