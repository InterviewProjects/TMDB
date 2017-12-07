package com.example.sawaiparihar.mytmdb.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class CommonModule {
    @Provides
    public CompositeDisposable provideDisposable() {
        return new CompositeDisposable();
    }
}
