import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


fun Any.getLogTag(): String = this.javaClass.simpleName


fun AppCompatActivity.popAllFragmentsIfNeeded() {
    try {
        val fragmentCount = supportFragmentManager.backStackEntryCount
        if (fragmentCount > 0) {
            for (i in 0 until fragmentCount) {
                supportFragmentManager.popBackStackImmediate()
            }
        }
    } catch (cause: Exception) {
        showLog(
            Log.ERROR,
            getLogTag(),
            "Exception on FragmentManager.popBackStackImmediate()",
            cause
        )
    }
}

fun AppCompatActivity.replaceFragment(targetFragment: Fragment, @IdRes containerResId: Int) {

    popAllFragmentsIfNeeded()

    try {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_NONE)
            .replace(containerResId, targetFragment)
            .commit()
    }
    catch (cause: Exception) {
        showLog(Log.ERROR, getLogTag(), "Exception on FragmentTransaction.commit()", cause)
    }
}

/** ask if the permission WRITE_EXTERNAL_STORAGE has been granted.  */
fun AppCompatActivity.hasWriteExternalStoragePermissionBeenGranted(): Boolean =
    (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

/** ask if the permission ACCESS_COARSE_LOCATION has been granted.  */
fun AppCompatActivity.hasCoarseLocationPermissionBeenGranted(): Boolean =
    (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)

/** ask if the permission ACCESS_FINE_LOCATION has been granted.  */
fun AppCompatActivity.hasFineLocationPermissionBeenGranted(): Boolean =
    (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

fun AppCompatActivity.hasEnoughLocationPermission(): Boolean {
    val fine = hasFineLocationPermissionBeenGranted()
    val coarse = hasCoarseLocationPermissionBeenGranted()
    return fine && coarse
}

fun showLog(mode: Int, tag: String?, message: String?) {
    if ((null == tag) || (null == message) ) {
        return
    }

    when (mode) {
        Log.DEBUG -> Log.d(tag, message)
        Log.INFO -> Log.i(tag, message)
        Log.WARN -> Log.w(tag, message)
        Log.ERROR -> Log.e(tag, message)
    }
}

fun showLog(mode: Int, tag: String?, message: String?, throwable: Throwable?) {
    if (null == tag || null == message || null == throwable) {
        return
    }

    when (mode) {
        Log.DEBUG -> Log.d(tag, message, throwable)
        Log.INFO -> Log.i(tag, message, throwable)
        Log.WARN -> Log.w(tag, message, throwable)
        Log.ERROR -> Log.e(tag, message, throwable)
    }
}