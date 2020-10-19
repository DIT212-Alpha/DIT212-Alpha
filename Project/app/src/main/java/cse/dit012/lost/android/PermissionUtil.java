package cse.dit012.lost.android;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import cse.dit012.lost.android.ui.map.LostMapFragment;

/**
 * Utilities for dealing with Android permissions.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link LostMapFragment}
 */
public final class PermissionUtil {
    private PermissionUtil() {
    }

    /**
     * Checks if the given permission has been granted to the application by the user.
     *
     * @param context    Android application {@link Context}
     * @param permission the permission id for the permission to check
     * @return true if the permission has been granted, false otherwise
     */
    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}