package com.example.smarthome.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Power
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smarthome.ui.theme.*


@Composable
fun SwitchCard(
    title: String = "3-Switch Unit"
) {

    var switch1 by remember { mutableStateOf(false) }
    var switch2 by remember { mutableStateOf(false) }
    var switch3 by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCard
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "🔌 $title",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            SwitchRow(
                name = "Switch 1",
                checked = switch1,
                onChanged = { switch1 = it }
            )

            SwitchRow(
                name = "Switch 2",
                checked = switch2,
                onChanged = { switch2 = it }
            )

            SwitchRow(
                name = "Switch 3",
                checked = switch3,
                onChanged = { switch3 = it }
            )
        }
    }
}



@Composable
fun SwitchRow(
    name: String,
    checked: Boolean,
    onChanged: (Boolean) -> Unit
) {

    val iconTint by animateColorAsState(
        targetValue = if (checked) PrimaryTealLight else TextMuted,
        animationSpec = tween(300),
        label = "switchIconTint"
    )

    val iconBg by animateColorAsState(
        targetValue = if (checked) PrimaryTeal.copy(alpha = 0.2f) else DarkCardElevated,
        animationSpec = tween(300),
        label = "switchIconBg"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Power,
                contentDescription = name,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryTeal,
                checkedBorderColor = PrimaryTeal,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = DarkCardElevated,
                uncheckedBorderColor = TextMuted
            )
        )
    }
}