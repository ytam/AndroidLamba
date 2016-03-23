package brusk.com.lamba;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
    private Camera camera;
    private ToggleButton button;
    private final Context context = this;
    private ImageView imageView;

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        button = (ToggleButton) findViewById(R.id.togglebutton);
        imageView= (ImageView) findViewById(R.id.imageView);

        final PackageManager pm = context.getPackageManager();
        if (!isCameraSupported(pm)) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Kamera yok");
            alertDialog.setMessage("Cihazınızda kamera bulunmuyor ...");

            alertDialog.show();
        }
        camera = Camera.open();
    }

    public void onToggleClicked(View view) {
        PackageManager pm = context.getPackageManager();
        final Parameters p = camera.getParameters();
        if (isFlashSupported(pm)) {
            boolean on = ((ToggleButton) view).isChecked();
            if (on) {

                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(p);
                camera.startPreview();
                imageView.setBackgroundResource(R.drawable.acik);


            } else {

                p.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(p);
                camera.stopPreview();
                imageView.setBackgroundResource(R.drawable.kapali);

            }
        } else {

            button.setChecked(false);
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Desteklenmiyor");
            alertDialog.setMessage("Üzgünüz, cihazınız bu uygulamayı desteklemiyor...");

            alertDialog.show();
        }
    }

    private boolean isFlashSupported(PackageManager packageManager) {

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return true;
        }
        return false;
    }
    private boolean isCameraSupported(PackageManager packageManager) {

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }



}
