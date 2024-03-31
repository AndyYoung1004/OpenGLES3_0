package com.example.opengles3;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    private final int PURECOLOR_ACTIVITY = 0;
    private final int TRIANGLE_ACTIVITY = 1;
    private final int COLOR_TRIANGLE_ACTIVITY = 2;
    private final int SQUARE_ACTIVITY = 3;
    private final int ROUND_ACTIVITY = 4;
    private final int CUBE_ACTIVITY = 5;
    private final int TEXTURE_ACTIVITY = 6;
    private final int SIXCUBE_ACTIVITY = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> displayList = new ArrayList<>();
        displayList.add("纯色");
        displayList.add("三角形");
        displayList.add("彩色三角形");
        displayList.add("四边形");
        displayList.add("圆形");
        displayList.add("立方体");
        displayList.add("纹理");
        displayList.add("六边形纹理");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, displayList);
        setListAdapter(adapter);
        checkPermission();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position == PURECOLOR_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, PureColorActivity.class);
            startActivity(intent);
        } else if (position == TRIANGLE_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TriangleActivity.class);
            startActivity(intent);
        } else if (position == COLOR_TRIANGLE_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ColorTriangle.class);
            startActivity(intent);
        } else if (position == SQUARE_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SquareActivity.class);
            startActivity(intent);
        } else if (position == ROUND_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, RoundActivity.class);
            startActivity(intent);
        } else if (position == CUBE_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CubeActivity.class);
            startActivity(intent);
        } else if (position == TEXTURE_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TextureActivity.class);
            startActivity(intent);
        } else if (position == SIXCUBE_ACTIVITY) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SixCubeActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PermissionChecker.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 0);
            return false;
        }
        return true;
    }
}