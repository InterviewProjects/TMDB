package com.example.sawaiparihar.mytmdb.base;

import android.support.v4.app.Fragment;

import javax.inject.Inject;


public abstract class WrapperFragment<P extends BasePresenter> extends Fragment {
    @Inject
    public P mPresenter;


    @Override
    public void onStart() {
        super.onStart();

        mPresenter.onAttach();
    }

    @Override
    public void onStop() {
        super.onStop();

        mPresenter.onDetach();
    }
}
