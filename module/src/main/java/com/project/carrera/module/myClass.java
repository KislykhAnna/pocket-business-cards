package com.project.carrera.module;

import java.util.jar.Manifest;

public class myClass {

    // Проверка разрешения доступа на вызов
    private void CheckPerm(Intent intent)
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        startActivity(intent);
    }
}
