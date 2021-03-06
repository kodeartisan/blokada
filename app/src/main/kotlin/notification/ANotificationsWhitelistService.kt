package notification

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import gs.environment.inject
import nl.komponents.kovenant.ui.promiseOnUi
import org.blokada.R
import core.Filter
import filter.FilterSourceSingle
import core.LocalisedFilter
import core.State

class ANotificationsWhitelistService : IntentService("notificationsWhitelist") {

    private val s by lazy { inject().instance<State>() }

    override fun onHandleIntent(intent: Intent) {
        val host = intent.getStringExtra("host") ?: return

        val filter = Filter(
                id = host,
                source = FilterSourceSingle(host),
                active = true,
                whitelist = true,
                localised = LocalisedFilter(host)
        )

        val existing = s.filters().firstOrNull { it == filter }
        if (existing == null) {
            s.filters %= s.filters() + filter
        } else if (!existing.active) {
            existing.active = true
            s.filters %= s.filters() // TODO: not sure if it's nice
        }

        promiseOnUi {
            Toast.makeText(this, R.string.notification_blocked_whitelist_applied, Toast.LENGTH_SHORT).show()
            hideNotification(this)
        }
    }

}
