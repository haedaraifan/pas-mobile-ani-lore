package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        fragmentManager = getSupportFragmentManager();
        PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, paperOnboardingFragment);
        fragmentTransaction.commit();

        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        String[] onboardingTitle = getResources().getStringArray(R.array.onboarding_title);
        String[] onboardingDescription = getResources().getStringArray(R.array.onboarding_description);

        PaperOnboardingPage source1 = new PaperOnboardingPage(onboardingTitle[0], onboardingDescription[0], Color.parseColor("#191C44"), R.drawable.onboarding_1, R.drawable.ic_test);
        PaperOnboardingPage source2 = new PaperOnboardingPage(onboardingTitle[1], onboardingDescription[1], Color.parseColor("#191C44"), R.drawable.onboarding_2, R.drawable.ic_test);
        PaperOnboardingPage source3 = new PaperOnboardingPage(onboardingTitle[2], onboardingDescription[2], Color.parseColor("#191C44"), R.drawable.onboarding_3, R.drawable.ic_test);
        PaperOnboardingPage source4 = new PaperOnboardingPage(onboardingTitle[3], onboardingDescription[3], Color.parseColor("#191C44"), R.drawable.onboarding_4, R.drawable.ic_test);

        elements.add(source1);
        elements.add(source2);
        elements.add(source3);
        elements.add(source4);

        return elements;
    }
}