package com.example.torobui.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.torobui.model.MockTorobData
import com.example.torobui.model.TorobCategory
import com.example.torobui.model.TorobProduct
import com.example.torobui.ui.theme.*

@Composable
fun TorobHomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedBottomTab by remember { mutableIntStateOf(0) }
    val favoriteIds = remember { mutableStateListOf<Int>() }

    val filteredProducts = remember(searchQuery, selectedCategory) {
        MockTorobData.products.filter { product ->
            val matchesQuery = searchQuery.isBlank() ||
                    product.title.contains(searchQuery, ignoreCase = true)
            val matchesCat = selectedCategory == null ||
                    selectedCategory == "سایر دسته‌ها" ||
                    product.category == selectedCategory
            matchesQuery && matchesCat
        }
    }

    Scaffold(
        containerColor = TorobBackground,
        topBar = {
            TorobTopBar()
        },
        bottomBar = {
            TorobBottomNavigation(
                selectedIndex = selectedBottomTab,
                favoriteCount = favoriteIds.size,
                onSelectTab = { selectedBottomTab = it }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                TorobHeroSearchSection(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSuggestionClick = { searchQuery = it }
                )
            }

            item {
                TorobCategoryGridSection(
                    categories = MockTorobData.categories,
                    selectedCategory = selectedCategory,
                    onCategoryClick = { catTitle ->
                        selectedCategory = if (selectedCategory == catTitle) null else catTitle
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.LocalFireDepartment,
                            contentDescription = null,
                            tint = TorobRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = selectedCategory ?: "محبوب‌ترین کالاها در ترب",
                            style = MaterialTheme.typography.titleMedium,
                            color = TorobTextPrimary
                        )
                    }
                    if (selectedCategory != null || searchQuery.isNotBlank()) {
                        Text(
                            text = "حذف فیلتر",
                            style = MaterialTheme.typography.bodySmall,
                            color = TorobRed,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                selectedCategory = null
                                searchQuery = ""
                            }
                        )
                    }
                }
            }

            val chunkedProducts = filteredProducts.chunked(2)
            if (chunkedProducts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "محصولی با این مشخصات پیدا نشد!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TorobTextSecondary
                        )
                    }
                }
            } else {
                items(chunkedProducts) { rowProducts ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (product in rowProducts) {
                            TorobProductCard(
                                product = product,
                                isFavorite = favoriteIds.contains(product.id),
                                onToggleFavorite = {
                                    if (favoriteIds.contains(product.id)) {
                                        favoriteIds.remove(product.id)
                                    } else {
                                        favoriteIds.add(product.id)
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowProducts.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TorobTopBar() {
    Surface(
        color = TorobSurface,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TorobLogoMark(modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ترب",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TorobRed
                )
            }

            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, TorobBorder),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = TorobTextPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "ورود / ثبت‌نام",
                    fontSize = 12.sp,
                    color = TorobTextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun TorobLogoMark(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        drawCircle(
            color = Color(0xFF2E7D32),
            radius = w * 0.16f,
            center = Offset(w * 0.42f, h * 0.22f)
        )
        drawCircle(
            color = Color(0xFF43A047),
            radius = w * 0.16f,
            center = Offset(w * 0.58f, h * 0.22f)
        )
        drawCircle(
            color = TorobRed,
            radius = w * 0.34f,
            center = Offset(w * 0.5f, h * 0.58f)
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.35f),
            radius = w * 0.08f,
            center = Offset(w * 0.38f, h * 0.48f)
        )
    }
}

@Composable
private fun TorobHeroSearchSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onSuggestionClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TorobSurface)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "موتور جستجوی هوشمند خرید",
            style = MaterialTheme.typography.titleMedium,
            color = TorobTextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "مقایسه قیمت میلیون‌ها کالا بین هزاران فروشگاه معتبر",
            style = MaterialTheme.typography.bodySmall,
            color = TorobTextSecondary
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "نام کالا را وارد کنید...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TorobTextSecondary
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(TorobRed),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "جستجو",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "پاک کردن",
                            tint = TorobTextSecondary
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TorobRed,
                unfocusedBorderColor = TorobBorder,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFFAFAFA)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(MockTorobData.trendingSearches) { item ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = TorobBackground,
                    border = BorderStroke(1.dp, TorobBorder),
                    modifier = Modifier.clickable { onSuggestionClick(item) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.TrendingUp,
                            contentDescription = null,
                            tint = TorobRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item,
                            style = MaterialTheme.typography.labelSmall,
                            color = TorobTextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TorobCategoryGridSection(
    categories: List<TorobCategory>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit
) {
    Surface(
        color = TorobSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 14.dp, horizontal = 12.dp)) {
            val rows = categories.chunked(4)
            rows.forEachIndexed { index, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { cat ->
                        val isSelected = selectedCategory == cat.title
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onCategoryClick(cat.title) }
                                .padding(vertical = 6.dp, horizontal = 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) TorobRed else cat.badgeColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = cat.icon,
                                    contentDescription = cat.title,
                                    tint = if (isSelected) Color.White else TorobTextPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = cat.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) TorobRed else TorobTextPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                if (index < rows.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun TorobProductCard(
    product: TorobProduct,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TorobSurface),
        border = BorderStroke(1.dp, TorobBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(product.accentColor.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = product.icon,
                    contentDescription = product.title,
                    tint = product.accentColor,
                    modifier = Modifier.size(54.dp)
                )

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "علاقه‌مندی",
                        tint = if (isFavorite) TorobRed else TorobTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                if (product.isPriceDropped) {
                    Surface(
                        color = TorobGreenBadge,
                        shape = RoundedCornerShape(bottomEnd = 8.dp),
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = "کاهش قیمت",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.bodySmall,
                color = TorobTextPrimary,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "از ",
                    style = MaterialTheme.typography.labelSmall,
                    color = TorobTextSecondary
                )
                Text(
                    text = product.minPrice,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TorobTextPrimary
                )
                Text(
                    text = " تومان",
                    style = MaterialTheme.typography.labelSmall,
                    color = TorobTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "در ${product.shopCount} فروشگاه",
                    style = MaterialTheme.typography.labelSmall,
                    color = TorobTextSecondary
                )
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = "اطلاع از تغییر قیمت",
                    tint = TorobTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun TorobBottomNavigation(
    selectedIndex: Int,
    favoriteCount: Int,
    onSelectTab: (Int) -> Unit
) {
    NavigationBar(
        containerColor = TorobSurface,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { onSelectTab(0) },
            icon = {
                Icon(
                    imageVector = if (selectedIndex == 0) Icons.Rounded.Home else Icons.Outlined.Home,
                    contentDescription = "خانه"
                )
            },
            label = { Text("خانه", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TorobRed,
                selectedTextColor = TorobRed,
                indicatorColor = TorobRedLight
            )
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { onSelectTab(1) },
            icon = {
                Icon(
                    imageVector = if (selectedIndex == 1) Icons.Rounded.Category else Icons.Outlined.Category,
                    contentDescription = "دسته‌بندی"
                )
            },
            label = { Text("دسته‌بندی", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TorobRed,
                selectedTextColor = TorobRed,
                indicatorColor = TorobRedLight
            )
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { onSelectTab(2) },
            icon = {
                BadgedBox(
                    badge = {
                        if (favoriteCount > 0) {
                            Badge(containerColor = TorobRed) {
                                Text(favoriteCount.toString(), color = Color.White)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (selectedIndex == 2) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "محبوب‌ها"
                    )
                }
            },
            label = { Text("محبوب‌ها", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TorobRed,
                selectedTextColor = TorobRed,
                indicatorColor = TorobRedLight
            )
        )
        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = { onSelectTab(3) },
            icon = {
                Icon(
                    imageVector = if (selectedIndex == 3) Icons.Rounded.Person else Icons.Outlined.Person,
                    contentDescription = "ترب من"
                )
            },
            label = { Text("ترب من", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TorobRed,
                selectedTextColor = TorobRed,
                indicatorColor = TorobRedLight
            )
        )
    }
}
