import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
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