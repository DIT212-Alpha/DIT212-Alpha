package cse.dit012.lost.android;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;



public final class PermissionUtil {
    private PermissionUtil() {
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
