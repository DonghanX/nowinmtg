package com.donghanx.common

/**
 * Identifies this app to the APIs and CDNs it talks to.
 *
 * Scryfall requires every request to `api.scryfall.com` to carry a User-Agent header that should
 * reflect the app's name and version, instead of a default value chosen by the HTTP library.
 *
 * See also the
 * [Scryfall blog](https://scryfall.com/blog/user-agent-and-accept-header-now-required-on-the-api-225)
 * for more information.
 */
const val USER_AGENT = "NowInMtg/1.0 (Android)"
