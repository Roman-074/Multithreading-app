package hedgehog.tech.multithreadingapp.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import hedgehog.tech.multithreadingapp.databinding.ActivityRxBinding;
import hedgehog.tech.multithreadingapp.main.AnimationUtils;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxJava extends AppCompatActivity {

    private static ActivityRxBinding binding;

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

        AnimationUtils.INSTANCE.setupAnimation(binding.lottieAnimation);
    }


    private static Single<String> downloadLogo() {
        return Single.create(s -> {
            for (int i = 0; i < 7; i++) {
                TimeUnit.MILLISECONDS.sleep(300);
                binding.textStatus.setText("Загрузка файла... " + i);
            }
            s.onSuccess("Success");
        });
    }


}
