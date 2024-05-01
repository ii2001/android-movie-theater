package woowacourse.movie.common.ui

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace

inline fun <reified T : Fragment> FragmentActivity.replaceTo(
    @IdRes fragmentContainerId: Int,
    tag: String? = null,
    args: Bundle? = null,
    action: () -> Unit = {}
) {
    supportFragmentManager.commit {
        replace<T>(fragmentContainerId, tag, args)
        action()
        setReorderingAllowed(true)
    }
}