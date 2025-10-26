package com.f14my.glyphalw.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.f14my.glyphalw.R
import com.f14my.glyphalw.ui.theme.NDot57
import com.f14my.glyphalw.ui.theme.NType82
import com.f14my.glyphalw.ui.theme.Space

@Composable
fun AboutScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge.copy(fontFamily = NDot57)
        )
        Text(
            text = stringResource(id = R.string.made_by),
            style = MaterialTheme.typography.bodyLarge.copy(fontFamily = NType82, fontWeight = FontWeight.W500),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            textAlign = TextAlign.Center
        )
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/username7052"))
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.send_24px),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(id = R.string.telegram),
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Space, fontWeight = FontWeight.Bold)
                )
            }
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/f14myy/MyGlyph"))
                context.startActivity(intent)
            }, modifier = Modifier.padding(start = 16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.code_24px),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(id = R.string.github),
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Space, fontWeight = FontWeight.Bold)
                    )
            }
        }
        Text(
            text = stringResource(id = R.string.app_version),
            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = NType82),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}