package com.f14my.glyphalw.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.glyphalw.R
import com.f14my.glyphalw.features.AnimationCard
import com.f14my.glyphalw.features.EpilepsyCard
import com.f14my.glyphalw.ui.theme.NDot57

@Composable
fun GlyphToolsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.glyph_tools),
            style = MaterialTheme.typography.headlineLarge.copy(fontFamily = NDot57, fontWeight = FontWeight.W500)
        )
        Text(
            text = stringResource(id = R.string.glyph_tools_desc),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))

        EpilepsyCard()
        
        Spacer(modifier = Modifier.height(16.dp))

        AnimationCard()
    }
}