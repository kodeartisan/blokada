package core

import android.content.Context
import com.github.salomonbrys.kodein.instance
import gs.environment.Environment
import gs.environment.inject
import org.blokada.BuildConfig
import org.obsolete.KContext
import org.obsolete.newPersistedProperty
import org.obsolete.newProperty

class AUiState(
        private val kctx: KContext,
        private val xx: Environment
) : UiState() {

    private val ctx: Context by xx.instance()

    override val seenWelcome = newPersistedProperty(kctx, APrefsPersistence(ctx, "seenWelcome"),
            { false }
    )

    override val version = newPersistedProperty(kctx, APrefsPersistence(ctx, "version"),
            { BuildConfig.VERSION_CODE }
    )

    override val notifications = newPersistedProperty(kctx, APrefsPersistence(ctx, "notifications"),
            { true }
    )

    override val editUi = newProperty(kctx, { false })

    override val dashes = newPersistedProperty(kctx, ADashesPersistence(ctx), { ctx.inject().instance() })

    override val infoQueue = newProperty(kctx, { listOf<Info>() })

    override val lastSeenUpdateMillis = newPersistedProperty(kctx, APrefsPersistence(ctx, "lastSeenUpdate"),
            { 0L }
    )

    override val showSystemApps = newPersistedProperty(kctx, APrefsPersistence(ctx, "showSystemApps"),
            { true })

}

