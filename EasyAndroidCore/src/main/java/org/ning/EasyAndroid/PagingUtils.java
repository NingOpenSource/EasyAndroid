package org.ning.EasyAndroid;

import android.content.Context;
import android.support.annotation.UiThread;

import org.ning.EasyJava.utils.StringUtils;
import org.ning.EasyObserver.core.EasyAcceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>分页工具</P>
 * Created by yanni on 2017/1/19.
 */
public class PagingUtils {
    /**
     * 是否还有更多
     *
     * @param total
     * @param pageSize
     * @param pageIndex
     * @param booleanAcceptor
     */
    public static void hasMore(final String total, final String pageSize, final String pageIndex, final EasyAcceptor.Acceptor2<Boolean, Boolean> booleanAcceptor) {
        new EasyThread.SubThread() {

            @Override
            public void onRunning(EasyThread.SubThread subThread) {
                int _total = 0;
                if (!StringUtils.isEmpty(total)) {
                    _total = Integer.valueOf(total);
                } else {
                    booleanAcceptor.accept(false, false);
                    return;
                }
                int _pageIndex = 1;
                if (!StringUtils.isEmpty(pageIndex)) {
                    _pageIndex = Integer.valueOf(pageIndex);
                } else {
                    booleanAcceptor.accept(false, false);
                    return;
                }
                int _pageSize = 10;
                if (!StringUtils.isEmpty(pageSize)) {
                    _pageSize = Integer.valueOf(pageSize);
                } else {
                    booleanAcceptor.accept(false, false);
                    return;
                }
                booleanAcceptor.accept(_pageSize * _pageIndex < _total, false);
            }
        }.start();
    }

    /**
     * 上一页
     *
     * @param pageIndex
     * @param pageIndexSetter
     */
    public static void lastPage(final String pageIndex, final EasyAcceptor.Acceptor2<String, Boolean> pageIndexSetter) {
        new EasyThread.SubThread() {
            @Override
            public void onRunning(EasyThread.SubThread subThread) {
                if (StringUtils.isEmpty(pageIndex)) {
                    pageIndexSetter.accept(pageIndex, false);
                    return;
                }
                int _pageIndex = Integer.valueOf(pageIndex);
                _pageIndex = _pageIndex <= 1 ? 1 : _pageIndex - 1;
                pageIndexSetter.accept("" + _pageIndex, false);
            }
        }.start();
    }

    /**
     * 下一页
     *
     * @param pageIndex
     * @param pageIndexSetter
     */
    public static void nextPage(final String pageIndex, final EasyAcceptor.Acceptor2<String, Boolean> pageIndexSetter) {
        new EasyThread.SubThread() {

            @Override
            public void onRunning(EasyThread.SubThread subThread) {
                if (StringUtils.isEmpty(pageIndex)) {
                    pageIndexSetter.accept(pageIndex, false);
                    return;
                }
                int _pageIndex = Integer.valueOf(pageIndex);
                _pageIndex = _pageIndex + 1;
                pageIndexSetter.accept("" + _pageIndex, false);
            }
        }.start();
    }

    /**
     * 分頁適配器
     *
     * @param <T>
     */
    public static abstract class PagingAdapter<T> {
        private Context context;

        protected abstract int getPageSize();

        private ArrayList<T> data = new ArrayList<>();
        private ArrayList<T> tempData = new ArrayList<>();

        /**
         * 返回每次页码的更改
         *
         * @param pageIndex 最小值为1
         */
        protected abstract void onChangePageIndex(int pageIndex);

        /**
         * 执行数据请求，并将单次获取的数据返回到temDataAcceptor中
         *
         * @param tempDataAcceptor 每个页面数据获取之后的回调
         */
        protected abstract void onRequest(EasyAcceptor.Acceptor2<List<T>, Boolean> tempDataAcceptor);

        /**
         * 返回最终组合后的数据（执行在UI线程上）
         *
         * @param data 所请求的所有页码合并后的数据集合
         */
        @UiThread
        protected abstract void afterDataChanged(List<T> data);

        private int pageIndex = 1;
        private EasyAcceptor.Acceptor2<List<T>, Boolean> onResponseDataAcceptor = new EasyAcceptor.Acceptor2<List<T>, Boolean>() {
            @Override
            public void accept(List<T> ts, Boolean aBoolean) {
                processTempData(ts);
            }
        };

        public ArrayList<T> getData() {
            return data;
        }

        /**
         * 處理每一頁返回的數據
         *
         * @param ts 每一頁返回的數據
         */
        private void processTempData(final List<T> ts) {
            new EasyThread.SubThread() {

                @Override
                public void onRunning(EasyThread.SubThread subThread) {
                    if (tempData.size() == 0) {
                        pageIndex = lastPage();
                    } else if (tempData.size() < getPageSize()) {//到了最后一頁
                        /**
                         * 回档数据
                         */
                        if (data.size() == tempData.size()) {
                            /**
                             * 数据无变化
                             */
                            data.clear();
                        } else {
                            /**
                             * 数据产生了变化，删除最后一页的旧数据,更新到最新的数据
                             */
                            data = new ArrayList<>(data.subList(0, data.size() - 1 - tempData.size()));
                        }
                        /**
                         * 页码索引进行回档
                         */
                        pageIndex = lastPage();

                    }
                    /**
                     * 增加最后一页的新数据
                     */
                    data.addAll(new ArrayList<T>(ts));
                    tempData = new ArrayList<>(ts);
                    new EasyThread.UIThread(context) {
                        @Override
                        public void onRunning(EasyThread.UIThread uiThread, Context context) {
                            afterDataChanged(data);
                        }
                    }.start();
                }
            }.start();
        }

        public PagingAdapter(Context context) {
            this.context = context;
        }

        /**
         * 刷新數據
         */
        public final void onRefresh() {
            tempData.clear();
            data.clear();
            setPageIndex(1);
            onRequest(onResponseDataAcceptor);
        }

        private void setPageIndex(int v) {
            pageIndex = v;
            onChangePageIndex(pageIndex);
        }

        /**
         * 加載數據
         */
        public final void onLoadMore() {
            if (data.size() < getPageSize()) {
                /**
                 * 当前数据不足一页时，加载数据相当于刷新数据
                 */
                onRefresh();
                return;
            }
            setPageIndex(nextPage());
            onRequest(onResponseDataAcceptor);
        }

        private int nextPage() {
            if (tempData.size() == 0) {
                return pageIndex;
            }
            return pageIndex + 1;
        }

        private int lastPage() {
            if (pageIndex <= 1)
                return 1;
            return pageIndex - 1;
        }
    }
}
