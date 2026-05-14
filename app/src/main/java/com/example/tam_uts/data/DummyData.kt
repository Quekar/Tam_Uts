package com.example.tam_uts.data

data class Recipe(
    val id: Int,
    val name: String,
    val origin: String,
    val regionCategory: String = "Indonesia"
)

data class User(
    val name: String,
    val bio: String,
    val recipesUploaded: Int
)

enum class Page {
    LOGIN, REGISTER,
    HOME, REGIONS, SEARCH, DETAIL, ADD, BOOKMARKS, PROFILE,
    EDIT_PROFILE, NOTIFICATIONS, SHIPPING_ADDRESS, SETTINGS
}

object DummyData {
    val dummyUser = User(
        name = "Andi Pratama",
        bio = "Pecinta kuliner nusantara & dunia.",
        recipesUploaded = 41
    )

    val dummyRecipes = listOf(
        Recipe(1, "Mie Aceh", "Aceh"),
        Recipe(2, "Bika Ambon", "Sumatera Utara"),
        Recipe(3, "Rendang Padang", "Sumatera Barat"),
        Recipe(4, "Gulai Belacan", "Riau"),
        Recipe(5, "Otak-otak", "Kepulauan Riau"),
        Recipe(6, "Gulai Ikan Patin", "Jambi"),
        Recipe(7, "Pempek", "Sumatera Selatan"),
        Recipe(8, "Lempah Kuning", "Kepulauan Bangka Belitung"),
        Recipe(9, "Pendap", "Bengkulu"),
        Recipe(10, "Seruit", "Lampung"),
        Recipe(11, "Kerak Telor", "DKI Jakarta"),
        Recipe(12, "Surabi", "Jawa Barat"),
        Recipe(13, "Sate Bandeng", "Banten"),
        Recipe(14, "Lumpia", "Jawa Tengah"),
        Recipe(15, "Gudeg", "DI Yogyakarta"),
        Recipe(16, "Rawon", "Jawa Timur"),
        Recipe(17, "Ayam Betutu", "Bali"),
        Recipe(18, "Ayam Taliwang", "Nusa Tenggara Barat"),
        Recipe(19, "Se'i Sapi", "Nusa Tenggara Timur"),
        Recipe(20, "Bubur Pedas", "Kalimantan Barat"),
        Recipe(21, "Juhu Singkah", "Kalimantan Tengah"),
        Recipe(22, "Soto Banjar", "Kalimantan Selatan"),
        Recipe(23, "Nasi Kuning Samarinda", "Kalimantan Timur"),
        Recipe(24, "Kepiting Soka", "Kalimantan Utara"),
        Recipe(25, "Tinutuan", "Sulawesi Utara"),
        Recipe(26, "Binte Biluhuta", "Gorontalo"),
        Recipe(27, "Kaledo", "Sulawesi Tengah"),
        Recipe(28, "Bau Peapi", "Sulawesi Barat"),
        Recipe(29, "Coto Makassar", "Sulawesi Selatan"),
        Recipe(30, "Sinonggi", "Sulawesi Tenggara"),
        Recipe(31, "Papeda", "Maluku"),
        Recipe(32, "Gohu Ikan", "Maluku Utara"),
        Recipe(33, "Sate Ulat Sagu", "Papua"),
        Recipe(34, "Ikan Bakar Manokwari", "Papua Barat"),
        Recipe(35, "Sagu Sep", "Papua Selatan"),
        Recipe(36, "Udang Selingkuh", "Papua Tengah"),
        Recipe(37, "Ayam Bakar Batu", "Papua Pegunungan"),
        Recipe(38, "Keladi Tumbuk", "Papua Barat Daya"),
        Recipe(39, "Pizza Margherita", "Italia", "Internasional"),
        Recipe(40, "Sushi Moriawase", "Jepang", "Internasional"),
        Recipe(41, "Tom Yum Goong", "Thailand", "Internasional")
    )
}