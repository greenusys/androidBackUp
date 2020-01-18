package com.icosom.social;

import android.graphics.PointF;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import com.nightonke.boommenu.BoomButtons.BoomButton;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
public class Test_boommenu extends AppCompatActivity {

    BoomMenuButton bmb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initializeBmb1();
       // initializeBmb2();
       //initializeBmb3();
       // initializeBmb4();
    }

    private void initializeBmb1() {
         bmb = (BoomMenuButton) findViewById(R.id.bmb1);

        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++)
            bmb.addBuilder(BuilderManager.getHamButtonBuilder());

        float w_0_5 = bmb.getHamWidth() / 2;
        float h_0_5 = bmb.getHamHeight() / 2;

        float hm_0_5 = bmb.getPieceHorizontalMargin() / 2;
        float vm_0_5 = bmb.getPieceVerticalMargin() / 2;

        bmb.getCustomPiecePlacePositions().add(new PointF(-w_0_5 - hm_0_5, -h_0_5 - vm_0_5));
        bmb.getCustomPiecePlacePositions().add(new PointF(+w_0_5 + hm_0_5, -h_0_5 - vm_0_5));
        bmb.getCustomPiecePlacePositions().add(new PointF(-w_0_5 - hm_0_5, +h_0_5 + vm_0_5));
       // bmb.getCustomPiecePlacePositions().add(new PointF(+w_0_5 + hm_0_5, +h_0_5 + vm_0_5));

        setListener();

    }

    private void setListener() {
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                System.out.println("kaif_index"+index);
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
            }

            @Override
            public void onBackgroundClick() {
              //  textViewForAnimation.setText("Click background!!!");
            }

            @Override
            public void onBoomWillHide() {
              //  textViewForAnimation.setText("Will RE-BOOM!!!");
            }

            @Override
            public void onBoomDidHide() {
                //textViewForAnimation.setText("Did RE-BOOM!!!");
            }

            @Override
            public void onBoomWillShow() {
               // textViewForAnimation.setText("Will BOOM!!!");
            }

            @Override
            public void onBoomDidShow() {
                //textViewForAnimation.setText("Did BOOM!!!");
            }
        });
    }

    /*private void initializeBmb2() {
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb2);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());

        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(-80), Util.dp2px(-80)));
        bmb.getCustomButtonPlacePositions().add(new PointF(0, 0));
        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(+80), Util.dp2px(+80)));
    }

    private void initializeBmb3() {
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb3);
        for (int i = 0; i < 12; i++)
            bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor());

        float w = Util.dp2px(80);
        float h = Util.dp2px(96);
        float h_0_5 = h / 2;
        float h_1_5 = h * 1.5f;

        float hm = bmb.getButtonHorizontalMargin();
        float vm = bmb.getButtonVerticalMargin();
        float vm_0_5 = vm / 2;
        float vm_1_5 = vm * 1.5f;

        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, -h_1_5 - vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, -h_1_5 - vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, -h_1_5 - vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, -h_0_5 - vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, -h_0_5 - vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, -h_0_5 - vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, +h_0_5 + vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, +h_0_5 + vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, +h_0_5 + vm_0_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(-w - hm, +h_1_5 + vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(      0, +h_1_5 + vm_1_5));
        bmb.getCustomButtonPlacePositions().add(new PointF(+w + hm, +h_1_5 + vm_1_5));
    }

    private void initializeBmb4() {
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb4);
        for (int i = 0; i < 3; i++)
            bmb.addBuilder(BuilderManager.getTextInsideCircleButtonBuilder());

        bmb.getCustomPiecePlacePositions().add(new PointF(Util.dp2px(+6), Util.dp2px(-6)));
        bmb.getCustomPiecePlacePositions().add(new PointF(0, 0));
        bmb.getCustomPiecePlacePositions().add(new PointF(Util.dp2px(-6), Util.dp2px(+6)));

        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(-80), Util.dp2px(-80)));
        bmb.getCustomButtonPlacePositions().add(new PointF(0, 0));
        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(+80), Util.dp2px(+80)));
    }*/
}
