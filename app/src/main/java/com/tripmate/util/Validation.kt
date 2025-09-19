package com.tripmate.util
object Validation {
  fun email(e: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()
  fun password(p: String) = p.length >= 6
}
