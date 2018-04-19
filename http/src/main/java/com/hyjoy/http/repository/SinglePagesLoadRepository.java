package com.hyjoy.http.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hyjoy.http.Status;
import com.hyjoy.http.data.ListData;
import com.hyjoy.http.data.Response;
import com.hyjoy.http.exception.PageDataException;
import com.hyjoy.http.livedata.NetStatusLiveData;
import com.hyjoy.http.loader.SinglePageLoader;
import com.hyjoy.http.util.ErrorUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * 分页数据封装
 * Created by hyjoy on 2018/4/19.
 */
public class SinglePagesLoadRepository<T> implements DataLoadRepository.PagesLoadRepository<T> {

    private final static int FIST_PAGE = 0;
    private final static int DEFAULT_PAGE_RP = 10;

    private interface Success<R> {
        void onSubscribe(Disposable d);

        int getPage();

        /**
         * 成功时的回调
         *
         * @param r 数据
         */
        void success(@Nullable R r);
    }

    private int rp;
    private SinglePageLoader<T> pageLoader;
    private int page;

    public SinglePagesLoadRepository(int rp, SinglePageLoader<T> pageLoader) {
        this.rp = rp;
        this.pageLoader = pageLoader;
    }

    public SinglePagesLoadRepository(SinglePageLoader<T> pageLoader) {
        this(DEFAULT_PAGE_RP, pageLoader);
    }

    private Disposable fistDisposable;
    private Disposable nextDisposable;


    private List<T> data;
    private MutableLiveData<List<T>> listLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> hasNext = new MutableLiveData<>();
    private NetStatusLiveData rLiveData = new NetStatusLiveData() {
        @Override
        protected void dispose() {
            if (fistDisposable != null && !fistDisposable.isDisposed()) {
                fistDisposable.dispose();
            }
        }
    };
    private NetStatusLiveData rPageStatusLiveData = new NetStatusLiveData() {
        @Override
        protected void dispose() {
            if (nextDisposable != null && !nextDisposable.isDisposed()) {
                nextDisposable.dispose();
            }
        }
    };
    private MutableLiveData<Boolean> nLiveData = new MutableLiveData<>();


    @Override
    public LiveData<Boolean> getHasNext() {
        if (hasNext.getValue() == null) {
            hasNext.postValue(false);
        }
        return hasNext;
    }

    @Override
    public LiveData<List<T>> getData() {
        return listLiveData;
    }

    @Override
    public LiveData<Boolean> noData() {
        return nLiveData;
    }

    @Override
    public LiveData<Status> getStatus() {
        return rLiveData;
    }

    @Override
    public LiveData<Status> getPageStatus() {
        return rPageStatusLiveData;
    }

    @Override
    public void loadData(boolean reLoad) {
        if (!reLoad && rLiveData.getValue() != null && rLiveData.getValue().isLoading()) {
            return;
        }
        if (fistDisposable != null && !fistDisposable.isDisposed()) {
            fistDisposable.dispose();
        }
        load(rLiveData, new Success<List<T>>() {
            @Override
            public void onSubscribe(Disposable d) {
                fistDisposable = d;
            }

            @Override
            public int getPage() {
                return FIST_PAGE;
            }

            @Override
            public void success(@Nullable List<T> ts) {
                if (ts != null) {
                    data = ts;
                    listLiveData.postValue(ts);
                }
            }
        });
    }

    @Override
    public void loadNextPage(boolean reLoad) {
        if (!reLoad && rLiveData.getValue() != null && rLiveData.getValue().isLoading()) {
            return;
        }
        if (nextDisposable != null && !nextDisposable.isDisposed()) {
            nextDisposable.dispose();
        }
        load(rPageStatusLiveData, new Success<List<T>>() {
            @Override
            public void onSubscribe(Disposable d) {
                nextDisposable = d;
            }

            @Override
            public int getPage() {
                return page + 1;
            }

            @Override
            public void success(@Nullable List<T> ts) {
                if (ts != null) {
                    ts.addAll(0, data);
                    data = ts;
                    listLiveData.postValue(ts);
                }
            }
        });
    }


    private void load(NetStatusLiveData liveData, @NonNull Success<List<T>> success) {
        liveData.postLoading();
        if (liveData != rPageStatusLiveData) {
            rPageStatusLiveData.postLoading();
        }
        pageLoader.getLoader(success.getPage(), rp).subscribe(new SingleObserver<Response<? extends ListData<T>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                success.onSubscribe(d);
            }

            @Override
            public void onSuccess(Response<? extends ListData<T>> listDataResponse) {
                if (listDataResponse.success()) {
                    if (listDataResponse.data() == null) {
                        onError(new PageDataException());
                    } else {
                        ListData<T> listData = listDataResponse.data();
                        success.success(listData.data());
                        nLiveData.postValue(listData.count() == 0);
                        page = listData.pageNo();
                        hasNext.postValue(listData.count() > data.size());
                        liveData.postSuccess();
                        if (liveData != rPageStatusLiveData) {
                            rPageStatusLiveData.postSuccess();
                        }
                    }
                } else {
                    liveData.postError(listDataResponse.message());
                    if (liveData != rPageStatusLiveData) {
                        rPageStatusLiveData.postError(listDataResponse.message());
                    }
                }
            }


            @Override
            public void onError(Throwable e) {
                liveData.postError(ErrorUtils.getErrorMessage(e));
                if (liveData != rPageStatusLiveData) {
                    rPageStatusLiveData.postError(ErrorUtils.getErrorMessage(e));
                }
            }
        });
    }
}
