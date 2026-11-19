package com.example.torobui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TorobCategory(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val badgeColor: Color
)

data class TorobProduct(
    val id: Int,
    val title: String,
    val minPrice: String,
    val shopCount: Int,
    val category: String,
    val icon: ImageVector,
    val accentColor: Color,
    val isPriceDropped: Boolean = false
)

object MockTorobData {
    val categories = listOf(
        TorobCategory(1, "موبایل و تبلت", Icons.Rounded.Smartphone, Color(0xFFE3F2FD)),
        TorobCategory(2, "لپ‌تاپ و کامپیوتر", Icons.Rounded.LaptopMac, Color(0xFFF3E5F5)),
        TorobCategory(3, "لوازم خانگی", Icons.Rounded.Kitchen, Color(0xFFE8F5E9)),
        TorobCategory(4, "مد و پوشاک", Icons.Rounded.Checkroom, Color(0xFFFFF3E0)),
        TorobCategory(5, "زیبایی و سلامت", Icons.Rounded.Spa, Color(0xFFFCE4EC)),
        TorobCategory(6, "صوتی و تصویری", Icons.Rounded.Headphones, Color(0xFFE0F7FA)),
        TorobCategory(7, "خودرو و موتور", Icons.Rounded.DirectionsCar, Color(0xFFEDE7F6)),
        TorobCategory(8, "سایر دسته‌ها", Icons.Rounded.GridView, Color(0xFFF5F5F5))
    )

    val trendingSearches = listOf(
        "آیفون 16 پرومکس",
        "گلکسی A55",
        "ایرپاد پرو 2",
        "لپ تاپ ایسوس",
        "ساعت هوشمند",
        "جاروبرقی رباتیک"
    )

    val products = listOf(
        TorobProduct(
            id = 1,
            title = "گوشی موبایل سامسونگ مدل Galaxy A55 5G ظرفیت 256 گیگابایت رم 8",
            minPrice = "۲۱,۴۵۰,۰۰۰",
            shopCount = 148,
            category = "موبایل و تبلت",
            icon = Icons.Rounded.Smartphone,
            accentColor = Color(0xFF1E88E5),
            isPriceDropped = true
        ),
        TorobProduct(
            id = 2,
            title = "هدفون بی‌سیم اپل مدل AirPods Pro 2nd Generation (USB-C)",
            minPrice = "۱۱,۸۹۰,۰۰۰",
            shopCount = 94,
            category = "صوتی و تصویری",
            icon = Icons.Rounded.Headphones,
            accentColor = Color(0xFF43A047),
            isPriceDropped = false
        ),
        TorobProduct(
            id = 3,
            title = "لپ تاپ 15.6 اینچی ایسوس مدل Vivobook 15 X1504ZA - i5 16GB 512SSD",
            minPrice = "۲۸,۷۰۰,۰۰۰",
            shopCount = 62,
            category = "لپ‌تاپ و کامپیوتر",
            icon = Icons.Rounded.LaptopMac,
            accentColor = Color(0xFF8E24AA),
            isPriceDropped = true
        ),
        TorobProduct(
            id = 4,
            title = "گوشی موبایل شیائومی مدل Redmi Note 13 Pro 5G ظرفیت 512 گیگابایت",
            minPrice = "۱۸,۲۰۰,۰۰۰",
            shopCount = 115,
            category = "موبایل و تبلت",
            icon = Icons.Rounded.Smartphone,
            accentColor = Color(0xFFFB8C00),
            isPriceDropped = false
        ),
        TorobProduct(
            id = 5,
            title = "اسپرسوساز دلونگی مدل Dedica EC685.M با فشار بخار 15 بار",
            minPrice = "۱۰,۴۵۰,۰۰۰",
            shopCount = 78,
            category = "لوازم خانگی",
            icon = Icons.Rounded.CoffeeMaker,
            accentColor = Color(0xFFD81B60),
            isPriceDropped = true
        ),
        TorobProduct(
            id = 6,
            title = "ساعت هوشمند سامسونگ مدل Galaxy Watch 6 Classic 47mm",
            minPrice = "۱۲,۳۰۰,۰۰۰",
            shopCount = 53,
            category = "موبایل و تبلت",
            icon = Icons.Rounded.Watch,
            accentColor = Color(0xFF00897B),
            isPriceDropped = false
        )
    )
}
