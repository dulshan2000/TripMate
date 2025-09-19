package com.tripmate.core.ui.theme

import androidx.compose.ui.graphics.Color

val BluePrimary = Color(0xFF2A85FF)  // 60
val CoralAccent = Color(0xFFFF6A3D)  // 10
val NeutralBg = Color(0xFFF7F8FA)    // 30
val NeutralOn = Color(0xFF1F2937)    // text
val NeutralLine = Color(0xFFE5E7EB)

data class RatioColors(
  val primary60: Color,
  val neutral30: Color,
  val accent10: Color
)

val TripMateRatio = RatioColors(BluePrimary, NeutralBg, CoralAccent)
