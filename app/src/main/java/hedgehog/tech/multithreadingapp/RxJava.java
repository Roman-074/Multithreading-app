package hedgehog.tech.multithreadingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import hedgehog.tech.multithreadingapp.databinding.ActivityRxBinding;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxJava extends AppCompatActivity {

    private ActivityRxBinding binding;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStart.setOnClickListener(v -> {

            downloadLogo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        binding.textStatus.setText(s);
                    }, throwable -> {
                        binding.textStatus.setText("Ошибка");
                    });
        });

    }


    private static Single<String> downloadLogo(){
        return Single.create( s -> {

            for (int i=0; i< 7; i++){
                TimeUnit.SECONDS.sleep(1);
                Log.d("my", "Загрузка файла... " + i);
            }

            s.onSuccess("Success");
        });
    }


}
