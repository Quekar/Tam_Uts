package com.example.tam_uts.data

data class Recipe(
    val id: Int,
    val name: String,
    val origin: String,
    val regionCategory: String = "Indonesia",
    val imageUrl: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

data class User(
    val name: String,
    val bio: String,
    val email: String,
    val phone: String,
    val recipesUploaded: Int
)

enum class Page {
    LOGIN, REGISTER,
    HOME, REGIONS, SEARCH, DETAIL, ADD, BOOKMARKS, PROFILE,
    EDIT_PROFILE, NOTIFICATIONS, SETTINGS
}

object DummyData {
    var dummyUser = User(
        name = "Andi Pratama",
        bio = "Pecinta kuliner nusantara & dunia.",
        email = "andi.pratama@email.com",
        phone = "+62 812 3456 7890",
        recipesUploaded = 41
    )

    val dummyRecipes = listOf(
        Recipe(
            id = 1,
            name = "Mie Aceh",
            origin = "Aceh",
            imageUrl = "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&q=80&w=1000",
            description = "Mie pedas khas Aceh dengan irisan daging sapi atau seafood.",
            ingredients = listOf("Mie kuning 500g", "Daging sapi 200g", "Bawang merah", "Bawang putih", "Cabai merah", "Kunyit", "Jahe", "Kapulaga"),
            instructions = listOf("Haluskan bumbu.", "Tumis bumbu hingga harum.", "Masukkan daging, masak hingga berubah warna.", "Tambahkan mie dan air secukupnya.", "Masak hingga bumbu meresap.")
        ),
        Recipe(
            id = 2,
            name = "Bika Ambon",
            origin = "Sumatera Utara",
            imageUrl = "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?auto=format&fit=crop&q=80&w=1000",
            description = "Kue kenyal dengan lubang-lubang kecil khas Medan.",
            ingredients = listOf("Tepung sagu 300g", "Santan 600ml", "Gula pasir 250g", "Telur 10 butir", "Ragi instan"),
            instructions = listOf("Campur ragi dengan sedikit air hangat.", "Kocok telur dan gula hingga larut.", "Masukkan tepung sagu and santan bergantian.", "Diamkan adonan selama 2 jam.", "Panggang hingga matang.")
        ),
        Recipe(
            id = 3,
            name = "Rendang Padang",
            origin = "Sumatera Barat",
            imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan daging bercita rasa pedas yang menggunakan campuran berbagai bumbu dan rempah-rempah.",
            ingredients = listOf("Daging sapi 1kg", "Santan kental 1.5L", "Serai", "Daun jeruk", "Asam kandis", "Bawang merah & putih", "Cabai merah"),
            instructions = listOf("Masak santan dengan bumbu halus hingga berminyak.", "Masukkan daging sapi.", "Aduk terus hingga santan menyusut dan bumbu meresap ke dalam daging.", "Masak dengan api kecil hingga warna berubah menjadi cokelat gelap.")
        ),
        Recipe(
            id = 4,
            name = "Gulai Belacan",
            origin = "Riau",
            imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan dengan bahan dasar udang atau ikan dengan bumbu belacan (terasi).",
            ingredients = listOf("Udang besar 500g", "Santan 500ml", "Terasi (belacan) 1 sdt", "Cabai merah", "Bawang merah & putih", "Asam jawa"),
            instructions = listOf("Haluskan bumbu and terasi.", "Tumis bumbu hingga harum.", "Masukkan santan dan air asam jawa.", "Masukkan udang, masak hingga matang dan kuah mengental.")
        ),
        Recipe(
            id = 5,
            name = "Otak-otak",
            origin = "Kepulauan Riau",
            imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan yang terbuat dari daging tenggiri cincang yang dibungkus dengan daun pisang dan dipanggang.",
            ingredients = listOf("Ikan tenggiri 500g", "Santan 200ml", "Tepung sagu 150g", "Putih telur 2 butir", "Daun bawang", "Bawang merah & putih"),
            instructions = listOf("Campur ikan, santan, telur, and bumbu halus.", "Tambahkan tepung sagu dan daun bawang, aduk rata.", "Bungkus dengan daun pisang.", "Bakar di atas bara api hingga matang.")
        ),
        Recipe(
            id = 6,
            name = "Gulai Ikan Patin",
            origin = "Jambi",
            imageUrl = "https://images.unsplash.com/photo-1559847844-5315695dadae?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan khas Jambi yang menggunakan ikan patin sebagai bahan utama dengan kuah kuning yang gurih.",
            ingredients = listOf("Ikan patin 1 ekor", "Santan 750ml", "Tempoyak (opsional)", "Kunyit", "Jahe", "Lengkuas", "Cabai merah"),
            instructions = listOf("Bersihkan ikan patin.", "Haluskan bumbu and tumis.", "Masukkan santan dan didihkan.", "Masukkan ikan patin, masak hingga matang.")
        ),
        Recipe(
            id = 7,
            name = "Pempek",
            origin = "Sumatera Selatan",
            imageUrl = "https://images.unsplash.com/photo-1574484284002-952d92456975?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan khas Palembang yang terbuat dari daging ikan yang digiling lembut yang dicampur tepung kanji.",
            ingredients = listOf("Ikan tenggiri 500g", "Tepung sagu 400g", "Air 250ml", "Garam", "Gula", "Cuka", "Ebi", "Cabai rawit"),
            instructions = listOf("Campur ikan, air, and garam.", "Tambahkan tepung sagu sedikit demi sedikit hingga bisa dibentuk.", "Rebus dalam air mendidih hingga mengapung.", "Goreng sebelum disajikan dengan cuko.")
        ),
        Recipe(
            id = 8,
            name = "Lempah Kuning",
            origin = "Kepulauan Bangka Belitung",
            imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan laut khas Bangka dengan kuah berwarna kuning dari kunyit dan rasa asam segar.",
            ingredients = listOf("Ikan laut (tenggiri/kakap) 500g", "Nanas 1/2 buah", "Kunyit", "Lengkuas", "Terasi", "Cabai merah", "Asam jawa"),
            instructions = listOf("Haluskan bumbu.", "Rebus air, masukkan bumbu halus and asam jawa.", "Masukkan ikan and nanas.", "Masak hingga ikan matang.")
        ),
        Recipe(
            id = 9,
            name = "Pendap",
            origin = "Bengkulu",
            imageUrl = "https://images.unsplash.com/photo-1519984388953-d2406bc725e1?auto=format&fit=crop&q=80&w=1000",
            description = "Ikan yang dibumbui dengan aneka rempah dan kelapa parut, lalu dibungkus daun talas dan dikukus lama.",
            ingredients = listOf("Ikan 500g", "Kelapa parut 1 butir", "Daun talas", "Daun pisang", "Kunyit", "Jahe", "Cabai merah"),
            instructions = listOf("Haluskan bumbu, campur dengan kelapa parut.", "Lumuri ikan dengan campuran kelapa.", "Bungkus dengan daun talas, lalu bungkus lagi dengan daun pisang.", "Kukus selama minimal 8 jam.")
        ),
        Recipe(
            id = 10,
            name = "Seruit",
            origin = "Lampung",
            imageUrl = "https://images.unsplash.com/photo-1529042410759-befb1204b468?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan ikan goreng atau bakar yang diaduk dengan sambal terasi, tempoyak, atau mangga.",
            ingredients = listOf("Ikan sungai 500g", "Sambal terasi", "Tempoyak", "Mangga muda iris", "Lalapan"),
            instructions = listOf("Bakar atau goreng ikan hingga matang.", "Campur sambal terasi, tempoyak, and mangga iris.", "Penyet ikan di atas sambal.", "Sajikan dengan nasi hangat dan lalapan.")
        ),
        Recipe(
            id = 11,
            name = "Kerak Telor",
            origin = "DKI Jakarta",
            imageUrl = "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan khas Betawi yang terbuat dari beras ketan, telur, and serundeng.",
            ingredients = listOf("Beras ketan 100g", "Telur bebek 1 butir", "Ebi", "Serundeng", "Bawang goreng"),
            instructions = listOf("Panaskan wajan, masukkan ketan dan air sedikit.", "Masukkan telur, ebi, and bumbu.", "Ratakan dan balik wajan di atas api hingga matang/kering.")
        ),
        Recipe(
            id = 12,
            name = "Surabi",
            origin = "Jawa Barat",
            imageUrl = "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?auto=format&fit=crop&q=80&w=1000",
            description = "Kue tradisional Sunda yang terbuat dari tepung beras dan santan, dimasak di atas cetakan tanah liat.",
            ingredients = listOf("Tepung beras 250g", "Santan 500ml", "Kelapa parut", "Garam", "Gula merah (untuk kuah kinca)"),
            instructions = listOf("Campur tepung beras, santan, and kelapa parut.", "Tuang adonan ke cetakan tanah liat yang sudah panas.", "Tutup dan masak hingga matang.", "Sajikan dengan kuah kinca.")
        ),
        Recipe(
            id = 13,
            name = "Sate Bandeng",
            origin = "Banten",
            imageUrl = "https://images.unsplash.com/photo-1555126634-323283e090fa?auto=format&fit=crop&q=80&w=1000",
            description = "Ikan bandeng yang dagingnya dikeluarkan, dicampur bumbu, dimasukkan kembali, lalu dibakar.",
            ingredients = listOf("Ikan bandeng 1 ekor", "Santan kental", "Telur 1 butir", "Bawang merah & putih", "Ketumbar", "Jintan"),
            instructions = listOf("Pukul-pukul ikan bandeng, keluarkan daging and durinya.", "Haluskan daging ikan dan campur dengan bumbu, telur, and santan.", "Masukkan kembali ke dalam kulit ikan.", "Jepit dengan bambu and bakar hingga matang.")
        ),
        Recipe(
            id = 14,
            name = "Lumpia",
            origin = "Jawa Tengah",
            imageUrl = "https://images.unsplash.com/photo-1562802378-063ec186a863?auto=format&fit=crop&q=80&w=1000",
            description = "Jajanan khas Semarang berupa lembaran tipis tepung gandum yang diisi rebung, telur, dan daging.",
            ingredients = listOf("Kulit lumpia", "Rebung iris 250g", "Daging ayam cincang 100g", "Udang 100g", "Telur 2 butir", "Bawang putih"),
            instructions = listOf("Tumis bawang putih, masukkan ayam and udang.", "Masukkan rebung and bumbu, masak hingga kering.", "Letakkan isi di atas kulit lumpia, lipat and gulung.", "Goreng hingga kecokelatan.")
        ),
        Recipe(
            id = 15,
            name = "Gudeg",
            origin = "DI Yogyakarta",
            imageUrl = "https://images.unsplash.com/photo-1455619452474-d2be8b1e70cd?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan khas Yogyakarta yang terbuat dari nangka muda yang dimasak dengan santan.",
            ingredients = listOf("Nangka muda 1kg", "Santan 1.5L", "Gula merah 200g", "Daun jati (untuk warna)", "Bawang merah & putih", "Ketumbar"),
            instructions = listOf("Masukkan nangka muda, santan, bumbu halus, and gula merah ke panci.", "Masak dengan api kecil selama beberapa jam hingga santan meresap and nangka empuk.", "Sajikan dengan krecek and ayam opor.")
        ),
        Recipe(
            id = 16,
            name = "Rawon",
            origin = "Jawa Timur",
            imageUrl = "https://images.unsplash.com/photo-1547592180-85f173990554?auto=format&fit=crop&q=80&w=1000",
            description = "Sup daging berkuah hitam yang menggunakan bumbu utama kluwek.",
            ingredients = listOf("Daging sapi (sandung lamur) 500g", "Kluwek 3-5 buah", "Bawang merah & putih", "Kunyit", "Jahe", "Serai", "Daun jeruk"),
            instructions = listOf("Rebus daging hingga empuk.", "Tumis bumbu halus (termasuk kluwek) hingga harum.", "Masukkan bumbu ke dalam rebusan daging.", "Masak hingga meresap and sajikan with tauge pendek.")
        ),
        Recipe(
            id = 17,
            name = "Ayam Betutu",
            origin = "Bali",
            imageUrl = "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?auto=format&fit=crop&q=80&w=1000",
            description = "Ayam yang diisi dengan bumbu base genep dan dibungkus daun pisang lalu dikukus atau dibakar.",
            ingredients = listOf("Ayam utuh 1 ekor", "Bumbu Base Genep (Lengkuas, Kunyit, Jahe, Kencur, Bawang, Cabai)", "Daun pisang"),
            instructions = listOf("Lumuri ayam dengan bumbu base genep secara merata.", "Bungkus dengan daun pisang.", "Kukus selama 2-3 jam atau hingga daging empuk and bumbu meresap.")
        ),
        Recipe(
            id = 18,
            name = "Ayam Taliwang",
            origin = "Nusa Tenggara Barat",
            imageUrl = "https://images.unsplash.com/photo-1532550907401-a500c9a57435?auto=format&fit=crop&q=80&w=1000",
            description = "Ayam bakar khas Lombok dengan bumbu pedas yang menggugah selera.",
            ingredients = listOf("Ayam kampung 1 ekor", "Cabai merah & rawit", "Bawang merah & putih", "Terasi bakar", "Kencur", "Gula merah"),
            instructions = listOf("Bakar ayam setengah matang.", "Haluskan bumbu and tumis.", "Lumuri ayam dengan bumbu lalu bakar kembali hingga matang sempurna.")
        ),
        Recipe(
            id = 19,
            name = "Se'i Sapi",
            origin = "Nusa Tenggara Timur",
            imageUrl = "https://images.unsplash.com/photo-1588315029754-2dd089d39a1a?auto=format&fit=crop&q=80&w=1000",
            description = "Daging sapi yang dimasak dengan cara diasap menggunakan kayu kosambi.",
            ingredients = listOf("Daging sapi has dalam 1kg", "Garam", "Lada", "Kayu kosambi (untuk pengasapan)"),
            instructions = listOf("Iris daging memanjang, lumuri garam and lada.", "Diamkan sejenak.", "Asapi daging di atas bara api kayu kosambi hingga matang and beraroma asap.")
        ),
        Recipe(
            id = 20,
            name = "Bubur Pedas",
            origin = "Kalimantan Barat",
            imageUrl = "https://images.unsplash.com/photo-1476224203421-9ac39bcb3b27?auto=format&fit=crop&q=80&w=1000",
            description = "Bubur tradisional Kalimantan Barat yang berisi aneka sayuran dan bumbu rempah.",
            ingredients = listOf("Beras 250g", "Kelapa parut sangrai", "Kangkung", "Pakis", "Wortel", "Jagung", "Kacang tanah goreng"),
            instructions = listOf("Sangrai beras and tumbuk halus.", "Rebus air, masukkan beras tumbuk.", "Masukkan sayuran and bumbu halus.", "Masak hingga menjadi bubur kental.")
        ),
        Recipe(
            id = 21,
            name = "Juhu Singkah",
            origin = "Kalimantan Tengah",
            imageUrl = "https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan khas suku Dayak yang menggunakan rotan muda sebagai bahan utamanya.",
            ingredients = listOf("Rotan muda (umbut) 500g", "Ikan baung/tenggiri", "Santan", "Kunyit", "Lengkuas", "Serai"),
            instructions = listOf("Bersihkan umbut rotan and potong-potong.", "Haluskan bumbu and tumis.", "Masukkan santan and umbut rotan.", "Masukkan ikan, masak hingga matang.")
        ),
        Recipe(
            id = 22,
            name = "Soto Banjar",
            origin = "Kalimantan Selatan",
            imageUrl = "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?auto=format&fit=crop&q=80&w=1000",
            description = "Soto khas suku Banjar dengan kuah bening atau sedikit keruh karena tambahan susu/telur.",
            ingredients = listOf("Ayam kampung 1 ekor", "Bawang merah & putih", "Kayu manis", "Cengkeh", "Pala", "Ketupat", "Perkedel kentang"),
            instructions = listOf("Rebus ayam dengan rempah (kayu manis, cengkeh, pala) hingga empuk.", "Suwir daging ayam.", "Tumis bumbu halus and masukkan ke kuah rebusan.", "Sajikan dengan ketupat, suwiran ayam, and perkedel.")
        ),
        Recipe(
            id = 23,
            name = "Nasi Kuning Samarinda",
            origin = "Kalimantan Timur",
            imageUrl = "https://images.unsplash.com/photo-1516684732162-798a0062be99?auto=format&fit=crop&q=80&w=1000",
            description = "Nasi kuning khas Samarinda yang biasanya disajikan dengan ikan haruan masak habang.",
            ingredients = listOf("Beras 500g", "Santan 750ml", "Kunyit", "Serai", "Daun salam", "Ikan haruan (gabus)", "Bumbu masak habang"),
            instructions = listOf("Masak beras dengan santan, kunyit, and rempah hingga menjadi nasi kuning.", "Masak ikan haruan dengan bumbu masak habang (cabai kering).", "Sajikan nasi kuning dengan ikan and pelengkap lainnya.")
        ),
        Recipe(
            id = 24,
            name = "Kepiting Soka",
            origin = "Kalimantan Utara",
            imageUrl = "https://images.unsplash.com/photo-1559715745-e1b33a271c8f?auto=format&fit=crop&q=80&w=1000",
            description = "Kepiting cangkang lunak yang bisa dimakan seluruh bagian tubuhnya, biasanya digoreng tepung.",
            ingredients = listOf("Kepiting soka 4 ekor", "Tepung bumbu serbaguna", "Telur 1 butir", "Minyak goreng"),
            instructions = listOf("Bersihkan kepiting soka.", "Celupkan ke telur kocok, lalu gulingkan ke tepung bumbu.", "Goreng dalam minyak panas hingga renyah.", "Sajikan dengan saus asam manis.")
        ),
        Recipe(
            id = 25,
            name = "Tinutuan",
            origin = "Sulawesi Utara",
            imageUrl = "https://images.unsplash.com/photo-1571091718767-18b5b1457add?auto=format&fit=crop&q=80&w=1000",
            description = "Bubur Manado yang kaya akan sayuran seperti labu kuning, bayam, kangkung, dan jagung.",
            ingredients = listOf("Beras 200g", "Labu kuning 200g", "Jagung manis", "Bayam", "Kangkung", "Daun kemangi"),
            instructions = listOf("Masak beras and labu kuning hingga menjadi bubur.", "Masukkan jagung and sayuran lainnya.", "Tambahkan garam and kemangi.", "Sajikan dengan ikan asin and sambal roa.")
        ),
        Recipe(
            id = 26,
            name = "Binte Biluhuta",
            origin = "Gorontalo",
            imageUrl = "https://images.unsplash.com/photo-1588765566399-8d80aeec6cf5?auto=format&fit=crop&q=80&w=1000",
            description = "Sup jagung manis khas Gorontalo dengan campuran udang atau ikan cakalang suwir.",
            ingredients = listOf("Jagung manis pipil 500g", "Udang/Ikan cakalang", "Kelapa parut", "Bawang merah & putih", "Cabai rawit", "Jeruk nipis"),
            instructions = listOf("Rebus jagung hingga matang.", "Masukkan udang/ikan and bumbu.", "Tambahkan kelapa parut and perasan jeruk nipis.", "Masak hingga mendidih and sajikan.")
        ),
        Recipe(
            id = 27,
            name = "Kaledo",
            origin = "Sulawesi Tengah",
            imageUrl = "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?auto=format&fit=crop&q=80&w=1000",
            description = "Sup kaki sapi khas Palu dengan kuah yang asam segar dari penggunaan asam jawa muda.",
            ingredients = listOf("Kaki sapi (tulang sumsum) 1kg", "Asam jawa muda", "Cabai rawit", "Garam"),
            instructions = listOf("Rebus kaki sapi hingga dagingnya empuk.", "Masukkan bumbu ulek (cabai rawit and garam) serta asam jawa muda.", "Masak hingga kuah meresap.", "Sajikan panas-panas.")
        ),
        Recipe(
            id = 28,
            name = "Bau Peapi",
            origin = "Sulawesi Barat",
            imageUrl = "https://images.unsplash.com/photo-1498654896293-37aaa4f9f1e4?auto=format&fit=crop&q=80&w=1000",
            description = "Masakan ikan khas Mandar dengan kuah kuning yang beraroma segar dari minyak kelapa mandar.",
            ingredients = listOf("Ikan (tuna/tongkol) 500g", "Bawang mandar", "Kunyit", "Cabai rawit", "Minyak kelapa mandar", "Air asam"),
            instructions = listOf("Susun ikan dalam wajan.", "Masukkan bumbu halus, air asam, and minyak kelapa mandar.", "Masak di atas api kecil hingga kuah menyusut.")
        ),
        Recipe(
            id = 29,
            name = "Coto Makassar",
            origin = "Sulawesi Selatan",
            imageUrl = "https://images.unsplash.com/photo-1611143669185-af224c5e3252?auto=format&fit=crop&q=80&w=1000",
            description = "Sup daging dan jeroan sapi khas Makassar with kuah kental dari kacang tanah goreng.",
            ingredients = listOf("Daging & jeroan sapi 1kg", "Kacang tanah goreng 250g (haluskan)", "Air cucian beras", "Bawang merah & putih", "Ketumbar", "Serai"),
            instructions = listOf("Rebus daging with air cucian beras and rempah hingga empuk.", "Masukkan kacang tanah halus ke dalam kuah.", "Tumis bumbu halus and campurkan ke kuah.", "Sajikan with burasa atau ketupat.")
        ),
        Recipe(
            id = 30,
            name = "Sinonggi",
            origin = "Sulawesi Tenggara",
            imageUrl = "https://images.unsplash.com/photo-1490645935967-10de6ba17061?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan pokok suku Tolaki yang terbuat dari saripati sagu, dimakan with sayur and ikan.",
            ingredients = listOf("Sagu 500g", "Air mendidih", "Ikan masak kuah kuning", "Sayur bening"),
            instructions = listOf("Seduh sagu with air mendidih sedikit demi sedikit sambil diaduk cepat hingga mengental and bening.", "Ambil sedikit demi sedikit menggunakan sumpit.", "Sajikan with kuah ikan kuning and sayur.")
        ),
        Recipe(
            id = 31,
            name = "Papeda",
            origin = "Maluku",
            imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99eb4b478?auto=format&fit=crop&q=80&w=1000",
            description = "Bubur sagu khas Papua and Maluku yang teksturnya lengket, biasanya disajikan with ikan kuah kuning.",
            ingredients = listOf("Sagu 500g", "Air mendidih", "Jeruk nipis"),
            instructions = listOf("Campur sagu with sedikit air dingin and perasan jeruk nipis.", "Siram with air mendidih sambil diaduk hingga sagu berubah warna menjadi bening and mengental.")
        ),
        Recipe(
            id = 32,
            name = "Gohu Ikan",
            origin = "Maluku Utara",
            imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?auto=format&fit=crop&q=80&w=1000",
            description = "Sashimi khas Ternate yang terbuat dari daging ikan tuna atau cakalang segar.",
            ingredients = listOf("Ikan tuna segar 300g", "Kacang tanah goreng", "Bawang merah", "Cabai rawit", "Lemon cui", "Minyak kelapa panas"),
            instructions = listOf("Potong dadu daging ikan tuna.", "Campur with irisan bawang, cabai, and kacang tanah.", "Beri perasan lemon cui.", "Siram with minyak kelapa panas, aduk rata.")
        ),
        Recipe(
            id = 33,
            name = "Sate Ulat Sagu",
            origin = "Papua",
            imageUrl = "https://images.unsplash.com/photo-1428515613728-6b4607e44363?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan ekstrem khas Papua yang kaya akan protein, ulat sagu yang dibakar.",
            ingredients = listOf("Ulat sagu segar", "Garam", "Jeruk nipis"),
            instructions = listOf("Bersihkan ulat sagu.", "Tusuk with lidi sate.", "Bakar di atas bara api hingga matang.", "Sajikan with garam and perasan jeruk nipis.")
        ),
        Recipe(
            id = 34,
            name = "Ikan Bakar Manokwari",
            origin = "Papua Barat",
            imageUrl = "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?auto=format&fit=crop&q=80&w=1000",
            description = "Ikan bakar with bumbu sambal ulek mentah yang pedas and khas.",
            ingredients = listOf("Ikan tongkol/tongkol 1 ekor", "Bawang merah & putih", "Cabai rawit", "Garam", "Jeruk nipis"),
            instructions = listOf("Bakar ikan hingga matang.", "Haluskan bumbu secara kasar (mentah).", "Siramkan bumbu ke atas ikan bakar.", "Sajikan segera.")
        ),
        Recipe(
            id = 35,
            name = "Sagu Sep",
            origin = "Papua Selatan",
            imageUrl = "https://images.unsplash.com/photo-1484980972926-edee96e0960d?auto=format&fit=crop&q=80&w=1000",
            description = "Makanan khas suku Marind yang terbuat dari sagu, kelapa, and daging babi atau ikan.",
            ingredients = listOf("Sagu", "Kelapa parut", "Daging (ikan/sapi)", "Garam"),
            instructions = listOf("Campur sagu, kelapa parut, and daging.", "Bungkus with daun pisang.", "Bakar di atas batu panas hingga matang.")
        ),
        Recipe(
            id = 36,
            name = "Udang Selingkuh",
            origin = "Papua Tengah",
            imageUrl = "https://images.unsplash.com/photo-1565299507177-b0ac66763828?auto=format&fit=crop&q=80&w=1000",
            description = "Jenis udang air tawar dari Wamena yang memiliki capit seperti kepiting.",
            ingredients = listOf("Udang selingkuh 500g", "Bawang merah & putih", "Saus tiram", "Cabai merah"),
            instructions = listOf("Bersihkan udang.", "Tumis bumbu hingga harum.", "Masukkan udang, tambahkan saus tiram.", "Masak hingga udang berubah warna and matang.")
        ),
        Recipe(
            id = 37,
            name = "Ayam Bakar Batu",
            origin = "Papua Pegunungan",
            imageUrl = "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&q=80&w=1000",
            description = "Metode memasak ayam menggunakan batu panas yang dikubur di dalam tanah.",
            ingredients = listOf("Ayam utuh 1 ekor", "Ubi jalar", "Sayuran hutan", "Batu kali", "Daun pisang"),
            instructions = listOf("Panaskan batu kali hingga merah.", "Buat lubang di tanah, lapisi with daun pisang.", "Susun batu panas, ubi, ayam, and sayuran selang-seling.", "Tutup rapat and tunggu hingga matang (beberapa jam).")
        ),
        Recipe(
            id = 38,
            name = "Keladi Tumbuk",
            origin = "Papua Barat Daya",
            imageUrl = "https://images.unsplash.com/photo-1590301157890-4810ed352733?auto=format&fit=crop&q=80&w=1000",
            description = "Olahan keladi yang direbus lalu ditumbuk halus, biasanya disajikan with ikan bakar.",
            ingredients = listOf("Keladi (talas) 1kg", "Garam", "Kelapa parut (opsional)"),
            instructions = listOf("Kupas and rebus keladi hingga empuk.", "Tumbuk selagi panas hingga halus.", "Bentuk sesuai selera and sajikan.")
        ),
        Recipe(
            id = 39,
            name = "Pizza Margherita",
            origin = "Italia",
            regionCategory = "Internasional",
            imageUrl = "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?auto=format&fit=crop&q=80&w=1000",
            description = "Pizza klasik Italia with topping tomat, mozzarella, and basil segar.",
            ingredients = listOf("Adonan pizza", "Saus tomat", "Keju mozzarella", "Daun basil segar", "Minyak zaitun"),
            instructions = listOf("Pipihkan adonan pizza.", "Oleskan saus tomat.", "Taburkan mozzarella.", "Panggang dalam oven suhu tinggi.", "Hiasi with basil.")
        ),
        Recipe(
            id = 40,
            name = "Sushi Moriawase",
            origin = "Jepang",
            regionCategory = "Internasional",
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&q=80&w=1000",
            description = "Aneka ragam sushi segar yang disajikan dalam satu piring.",
            ingredients = listOf("Nasi sushi", "Ikan tuna/salmon segar", "Nori", "Wasabi", "Kecap asin"),
            instructions = listOf("Siapkan nasi sushi.", "Iris ikan segar tipis-tipis.", "Bentuk nasi and letakkan ikan di atasnya atau gulung with nori.", "Sajikan with wasabi.")
        ),
        Recipe(
            id = 41,
            name = "Tom Yum Goong",
            origin = "Thailand",
            regionCategory = "Internasional",
            imageUrl = "https://images.unsplash.com/photo-1548943487-a2e4e43b4853?auto=format&fit=crop&q=80&w=1000",
            description = "Sup udang pedas asam khas Thailand with rempah-rempah segar.",
            ingredients = listOf("Udang 300g", "Jamur", "Serai", "Lengkuas", "Daun jeruk", "Cabai", "Air asam jawa"),
            instructions = listOf("Didihkan air with rempah.", "Masukkan udang and jamur.", "Tambahkan bumbu tom yum and air asam.", "Masak hingga udang matang.")
        )
    )
}